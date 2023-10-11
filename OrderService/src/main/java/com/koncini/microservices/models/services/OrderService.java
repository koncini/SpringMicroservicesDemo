package com.koncini.microservices.models.services;

import java.util.UUID;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.koncini.microservices.models.Order;
import com.koncini.microservices.models.OrderLineItems;
import com.koncini.microservices.models.dto.InventoryResponse;
import com.koncini.microservices.models.dto.OrderLineItemsDto;
import com.koncini.microservices.models.dto.OrderRequest;
import com.koncini.microservices.models.repositories.IOrderRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

	private final IOrderRepository orderRepository;
	private final WebClient webClient;

	public void placeOrder(OrderRequest orderRequest) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream().map(this::mapToDto)
				.toList();
		order.setOrderLineItemsList(orderLineItems);

		List<String> skuCodes = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();

		// Call inventory and place order if is in stock
		InventoryResponse[] inventoryResponsesArray = webClient.get()
				.uri("https://localhost:8082/api/inventory",
						uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
				.retrieve().bodyToMono(InventoryResponse[].class).block();

		boolean allProductsInStock = Arrays.stream(inventoryResponsesArray).allMatch(InventoryResponse::isInStock);
		
		if (allProductsInStock) {
			orderRepository.save(order);
		} else {
			throw new IllegalArgumentException("Product is not in stock");
		}
	}

	private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
		OrderLineItems orderLineItems = new OrderLineItems();
		orderLineItems.setPrice(orderLineItemsDto.getPrice());
		orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
		orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
		return orderLineItems;
	}
}
