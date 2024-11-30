package com.example.Project1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.Project1.model.CartItem;

@Repository
public interface CartItemReposritory extends JpaRepository<CartItem, Long> {

    @Query("SELECT c FROM CartItem c WHERE c.ID = :ID")
    public CartItem findCartItemByID(@Param("ID") int ID);

    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem c WHERE c.ID = :ID")
    public void deleteCartItemByID(@Param("ID") int ID);

    @Query("SELECT c FROM CartItem c WHERE c.shoe.ID = :productID AND c.cart.ID = :cartID")
    public CartItem findCartItemByProductIdAndCartID(@Param("productID")int productID, @Param("cartID") int cartID);
    
} 
