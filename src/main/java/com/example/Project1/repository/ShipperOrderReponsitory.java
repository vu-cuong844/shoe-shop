package com.example.Project1.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.Project1.model.ShipperOrder;

@Repository
public interface ShipperOrderReponsitory extends JpaRepository<ShipperOrder, Long> {
    @Query("SELECT so FROM ShipperOrder so WHERE so.shipper.user.ID = :shipperID")
    List<ShipperOrder> findAllShipperOrderByShipperID(@Param("shipperID") int shipperID);
    
    @Query("SELECT so FROM ShipperOrder so WHERE so.ID = :ID")
    ShipperOrder findShipperOrderByID(@Param("ID") int ID);

    @Query("SELECT so FROM ShipperOrder so WHERE so.status = :status AND so.shipper.user.ID = :shipperID")
    List<ShipperOrder> findAllShipperOrderByStatus(@Param("shipperID") int shipperID, @Param("status") String status);
 
    @Transactional
    @Modifying
    @Query("UPDATE ShipperOrder so SET so.status = :new_status, so.delivery_date = :new_date WHERE so.ID = :ID")
    void updateShipperOrderStatusByID(@Param("ID") int ID, @Param("new_status") String new_status, @Param("new_date") LocalDateTime newLocalDateTime);
}
