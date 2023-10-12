package com.koncini.microservices.models.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.koncini.microservices.models.Inventory;

public interface IInventoryRepository extends JpaRepository<Inventory, Long> {
	List<Inventory> findBySkuCodeIn(List<String> skuCode);
}
