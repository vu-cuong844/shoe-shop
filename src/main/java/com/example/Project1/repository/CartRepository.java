package com.example.Project1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Project1.model.Cart;
import com.example.Project1.model.CartItem;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT ci FROM CartItem ci JOIN ci.cart c JOIN c.user u WHERE u.ID = :userID")
    List<CartItem> findCartItemsByUserID(@Param("userID") int userID);

    @Query("SELECT c FROM Cart c WHERE c.user.ID = :userID")
    Cart findCartByUserID(@Param("userID") int ID);
}
