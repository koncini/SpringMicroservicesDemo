package com.koncini.microservice.models.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.koncini.microservice.models.dto.InventoryResponse;
import com.koncini.microservice.models.repositories.IInventoryRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

	private final IInventoryRepository inventoryRepository;

	@Transactional
	public List<InventoryResponse> isInStock(List<String> skuCodes) {
		return inventoryRepository.findBySkuCodeIn(skuCodes).stream().map(inventory -> InventoryResponse.builder()
				.skuCode(inventory.getSkuCode()).isInStock(inventory.getQuantity() > 0).build()).toList();
	}
}
