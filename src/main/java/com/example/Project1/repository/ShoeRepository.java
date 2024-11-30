package com.example.Project1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.Project1.model.Shoe;

@Repository
public interface ShoeRepository extends JpaRepository<Shoe, Long>{
    List<Shoe> findByProduct_ID(int id);

    @Query("SELECT s FROM Shoe s WHERE s.product.ID = :productID AND s.size.size = :size AND s.color.color = :color")
    Shoe findShoeByIDColorSize(@Param("productID") int ID, @Param("size") int size, @Param("color") String color);

    @Query("SELECT s FROM Shoe s WHERE s.ID =: shoeID")
    Shoe findShoeByID(@Param("shoeID") int ID);

    @Transactional
    @Modifying
    @Query("UPDATE Shoe s SET s.quantity = :new_quantity WHERE s.ID = :shoeID")
    void updateQuantity(@Param("shoeID") int ID, @Param("new_quantity") int new_quantity);
}
