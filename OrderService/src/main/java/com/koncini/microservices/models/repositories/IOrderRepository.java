package com.koncini.microservices.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.koncini.microservices.models.Order;

public interface IOrderRepository extends JpaRepository<Order, Long>{

}
