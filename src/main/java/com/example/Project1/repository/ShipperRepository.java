package com.example.Project1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Project1.model.Shipper;

@Repository
public interface ShipperRepository extends JpaRepository<Shipper, Long> {
    @Query("SELECT s FROM Shipper s WHERE s.user.phone = :phone AND s.user.role = :role")
    Shipper findShipperByPhoneAndRole(@Param("phone") String phone, @Param("role") String role);

    @Query("SELECT s FROM Shipper s WHERE s.ID = :shipperID")
    Shipper findShipperById(@Param("shipperID") int shipperID);
}
