package com.koncini.microservice.models.service;

import org.springframework.stereotype.Service;

import com.koncini.microservice.models.repositories.IInventoryRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

	private final IInventoryRepository inventoryRepository;

	@Transactional
	public boolean isInStock(String skuCode) {
		return inventoryRepository.findBySkuCode().isPresent();
	}
}
