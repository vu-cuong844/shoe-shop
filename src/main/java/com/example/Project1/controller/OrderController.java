package com.example.Project1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Project1.config.CustomUserDetails;
import com.example.Project1.dto.OrderRequest;
import com.example.Project1.model.Order;
import com.example.Project1.model.User;
import com.example.Project1.service.CartItemService;
import com.example.Project1.service.OrderService;
import com.example.Project1.service.ShipperOrderService;
import com.example.Project1.service.UserService;

@Controller
@RequestMapping("/user/order")
public class OrderController {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ShipperOrderService shipperOrderService;
    

    @GetMapping("")
    public String showOrderPage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model){
        User user = userService.getUserByID(customUserDetails.getID());
        List<Order> orders = orderService.getAllOrdersOfUser(user.getID());
        model.addAttribute("user", user);
        model.addAttribute("orders", orders);
        return "user_order";
    }

    @PostMapping("")
    public String createOrder(@ModelAttribute("orderRequest") OrderRequest orderRequest, Model model){
        orderService.createOrders(orderRequest);
        cartItemService.deleteCartItems(cartItemService.getAllCartItems(orderRequest.getCartItemIDs()));
        return "redirect:/user/order";

    }

    @GetMapping("/orderID={id}")
    public String showOrderDetails(@PathVariable("id") int id, @AuthenticationPrincipal CustomUserDetails customUserDetails, Model model){
        model.addAttribute("user", userService.getUserByID(customUserDetails.getID()));
        model.addAttribute("order", orderService.getOrderByID(id));
        return "user_detail_order";
    }

    @PutMapping("/cancel/orderID={id}")
    public String cancelOrder(@PathVariable("id") int id, @AuthenticationPrincipal CustomUserDetails custom, Model model){
        orderService.setOrderStatus(id, "Đã hủy");

        return "redirect:/user/order";
    }

    @PutMapping("/cancel_order/orderID={id}")
    public String cacelOrder(@PathVariable("id") int orderID){
        orderService.cancelOrder(orderID);
        return "redirect:/user/order";
    }

}
