package com.koncini.microservices.models.services;

import java.util.UUID;
import java.util.List;

import org.springframework.stereotype.Service;

import com.koncini.microservices.models.Order;
import com.koncini.microservices.models.OrderLineItems;
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

	public void placeOrder(OrderRequest orderRequest) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream().map(this::mapToDto)
				.toList();
		order.setOrderLineItemsList(orderLineItems);

		orderRepository.save(order);
	}

	private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
		OrderLineItems orderLineItems = new OrderLineItems();
		orderLineItems.setPrice(orderLineItemsDto.getPrice());
		orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
		orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
		return orderLineItems;
	}
}
