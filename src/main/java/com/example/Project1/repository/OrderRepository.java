package com.example.Project1.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.Project1.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.ID = :ID")
    Order findOrderByID(@Param("ID") int ID);
    
    @Query("SELECT o FROM Order o WHERE o.user.ID = :userId ORDER BY o.order_date DESC")
    List<Order> findAllOrderByUserId(@Param("userId") int userId);

    @Query("SELECT o FROM Order o ORDER BY o.order_date DESC")
    List<Order> findAll();

    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.status = :new_status WHERE o.ID = :orderID ")
    void updateOrderStatus(@Param("orderID") int orderID, @Param("new_status") String new_status);

    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.receive_date = :receive_date WHERE o.ID = :orderID")
    void updateOrderReceiveDate(@Param("orderID") int orderID, @Param("receive_date") LocalDateTime receive_date);

}
