package com.example.Project1.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Project1.dto.ShipperOrderDTO;
import com.example.Project1.model.Order;
import com.example.Project1.model.Shipper;
import com.example.Project1.model.ShipperOrder;
import com.example.Project1.repository.ShipperOrderReponsitory;

@Service
public class ShipperOrderService {
    @Autowired
    private ShipperOrderReponsitory shipperOrderReponsitory;

    @Autowired
    private ShipperService shipperService;

    @Autowired
    private OrderService orderService;

    public ShipperOrder getShipperOrderByID(int ID){
        return shipperOrderReponsitory.findShipperOrderByID(ID);
    }

    public void setShipperOrderStatus(int ID, String new_status){
        Order order = shipperOrderReponsitory.findShipperOrderByID(ID).getOrder();
        LocalDateTime now = LocalDateTime.now();
        orderService.setOrderReceiveDate(order.getID(), now);
        shipperOrderReponsitory.updateShipperOrderStatusByID(ID, new_status, now);
    }

    public void addItem(ShipperOrderDTO shipperOrderDTO){
        Shipper shipper = shipperService.getShipperByID(shipperOrderDTO.getShipperID());
        List<Order> orders = orderService.getAllOrdersWithID(shipperOrderDTO.getOrderIDs());
        LocalDateTime now = LocalDateTime.now();
        String status = "Nhận giao hàng";
        String order_status = "Vận chuyển";
        for (Order order : orders) {
            ShipperOrder shipperOrder = new ShipperOrder(shipper, order, now, status);
            orderService.setOrderStatus(order.getID(), order_status);
            shipperOrderReponsitory.save(shipperOrder);
        }
    }

    public List<ShipperOrder> getAllShipperOrderByShipperID(int shipperID){
        return shipperOrderReponsitory.findAllShipperOrderByShipperID(shipperID);
    }

    public List<ShipperOrder> getShipperOrdersOfShipperByStatus(int shipperID,String status){
        return shipperOrderReponsitory.findAllShipperOrderByStatus(shipperID, status);
    }
}

