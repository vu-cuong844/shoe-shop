package com.example.Project1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Project1.model.Shipper;
import com.example.Project1.repository.ShipperRepository;

@Service
public class ShipperService {
    @Autowired
    private ShipperRepository shipperRepository;

    public void addNewShipper(Shipper shipper){
        shipperRepository.save(shipper);
    }

    public Shipper getShipperByID(int ID){
        return shipperRepository.findShipperById(ID);
    }

    public Shipper getShipperByPhone(String phone){
        return shipperRepository.findShipperByPhoneAndRole(phone, "SHIPPER");
    }
}
