package com.koncini.microservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.koncini.microservice.models.service.InventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/invantory")
@RequiredArgsConstructor
public class InventoryController {

	private final InventoryService inventoryService;

	@GetMapping("/{sku/code}")
	@ResponseStatus(HttpStatus.OK)
	public boolean isInStock(@PathVariable String skuCode) {
		return inventoryService.isInStock(skuCode);
	}
}
