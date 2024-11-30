package com.example.Project1.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Project1.model.Product;

import jakarta.transaction.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    Optional<Product> findByID(int id);

    @Query("SELECT p FROM Product p WHERE p.status = :status")
    List<Product> findProductByStatus(@Param("status") int status);

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.status = :status WHERE p.ID = :ID")
    void updateProductStatus(@Param("status") int status, @Param("ID") int ID);
} 