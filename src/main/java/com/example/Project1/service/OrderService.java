package com.example.Project1.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Project1.dto.OrderRequest;
import com.example.Project1.model.CartItem;
import com.example.Project1.model.Order;
import com.example.Project1.model.Shoe;
import com.example.Project1.model.User;
import com.example.Project1.repository.OrderRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ShoeService shoeService;

    public Order getOrderByID(int orderID) {
        return orderRepository.findOrderByID(orderID);
    }

    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    public List<Order> getAllOrdersOfUser(int userID) {
        return orderRepository.findAllOrderByUserId(userID);
    }

    public void setOrderStatus(int orderID, String new_status) {
        orderRepository.updateOrderStatus(orderID, new_status);
    }

    public List<Order> getAllOrdersWithID(List<Integer> orderIDs) {
        List<Order> orders = new ArrayList<>();
        for (int id : orderIDs) {
            orders.add(orderRepository.findOrderByID(id));
        }
        return orders;
    }

    public String cancelOrder(int orderID){
        Order order = orderRepository.findOrderByID(orderID);

        LocalDateTime orderDate = order.getOrder_date();
        LocalDateTime cancelDate = LocalDateTime.now();

        if (Duration.between(orderDate, cancelDate).toHours() > 48 ) {
            return "Quá thời gian hủy";
        }

        if (order.getStatus().equals("Đã hủy")) {
            return "Đơn hàng đã được hủy trước đó";
        }

        //chuyển trạng thái trang 'Đã hủy'
        this.setOrderStatus(orderID, "Đã hủy");
        //Công trả lại hàng cho shoe
        shoeService.setQuantity(order.getShoes().getID(), order.getShoes().getQuantity() + order.getQuantity());
        return "";
    }

    public void setOrderReceiveDate(int orderID, LocalDateTime dateTime) {
        orderRepository.updateOrderReceiveDate(orderID, dateTime);
    }

    public List<Order> createOrders(OrderRequest orderRequest) {
        User user = userService.getUserByID(orderRequest.getUserID());
        List<Order> orders = new ArrayList<>();

        for (int cartItemID : orderRequest.getCartItemIDs()) {
            Order order = new Order();
            order.setUser(user);
            order.setDelivery_address(orderRequest.getDelivery_address());
            order.setPayment_method(orderRequest.getPayment_mehtod());
            order.setReceiver(orderRequest.getReceiver());
            order.setPhonenumber(orderRequest.getPhonenumber());
            order.setOrder_date(LocalDateTime.now());
            order.setStatus("Chờ thanh toán");

            CartItem cartItem = cartItemService.getCartItemByID(cartItemID);
            Shoe shoe = cartItem.getShoe();
            shoeService.setQuantity(shoe.getID(), shoe.getQuantity() - cartItem.getQuantity());

            order.setShoes(shoe);
            order.setPrice(cartItem.getPrice());
            order.setQuantity(cartItem.getQuantity());
            orders.add(order);
        }

        orderRepository.saveAll(orders);

        return orders;
    }

}
