package com.role.implementation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.role.implementation.model.Products;



@Repository
public interface ProductRepository extends JpaRepository<Products, Long>{
	
}
