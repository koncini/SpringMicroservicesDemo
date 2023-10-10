package com.koncini.microservice.models.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.koncini.microservice.models.Inventory;

public interface IInventoryRepository extends JpaRepository<Inventory, Long>{
	Optional<Inventory> findBySkuCode();
}
