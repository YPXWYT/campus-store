package com.tna.campus_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tna.campus_store.beans.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
