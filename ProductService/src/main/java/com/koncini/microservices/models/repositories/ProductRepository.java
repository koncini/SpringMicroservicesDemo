package com.koncini.microservices.models.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.koncini.microservices.models.Product;

public interface ProductRepository extends MongoRepository<Product, String>{

}
