package com.example.Project1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Project1.config.CustomUserDetails;
import com.example.Project1.model.ShipperOrder;
import com.example.Project1.service.OrderService;
import com.example.Project1.service.ShipperOrderService;
import com.example.Project1.service.UserService;

@Controller
@RequestMapping("/shipper")
public class ShipperController {
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ShipperOrderService shipperOrderService;

    @GetMapping("/home")
    public String showHomePage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        model.addAttribute("user", userService.getUserByID(customUserDetails.getID()));
        return "shipper_home";
    }

    @GetMapping("/order")
    public String showOrderPage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        model.addAttribute("user", userService.getUserByID(customUserDetails.getID()));
        List<ShipperOrder> allOrders = shipperOrderService.getShipperOrdersOfShipperByStatus(customUserDetails.getID(),"Nhận giao hàng");
        model.addAttribute("allOrders", allOrders);
        return "shipper_order";
    }

    @PutMapping("/delivery/order/{id}")
    public String deliveryOrder(@PathVariable("id") int ID){
        shipperOrderService.setShipperOrderStatus(ID, "Đã giao");
        orderService.setOrderStatus(shipperOrderService.getShipperOrderByID(ID).getOrder().getID(), "Thành công");
        return "redirect:/shipper/order";
    }

    @GetMapping("/deliveried_order")
    public String showDeliveriedOrderPage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        model.addAttribute("user", userService.getUserByID(customUserDetails.getID()));
        model.addAttribute("allOrders", shipperOrderService.getShipperOrdersOfShipperByStatus(customUserDetails.getID(),"Đã giao"));
        return "shipper_deliveried_order";
    }

    @GetMapping("/account")
    public String showAccountPage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        model.addAttribute("user", userService.getUserByID(customUserDetails.getID()));
        return "shipper_account";
    }
}
