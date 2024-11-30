package com.example.Project1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Project1.config.CustomUserDetails;
import com.example.Project1.dto.ShipperOrderDTO;
import com.example.Project1.dto.Wrap;
import com.example.Project1.model.Order;
import com.example.Project1.model.Product;
import com.example.Project1.model.Shipper;
import com.example.Project1.model.User;
import com.example.Project1.service.AdminService;
import com.example.Project1.service.OrderService;
import com.example.Project1.service.ProductService;
import com.example.Project1.service.ShipperOrderService;
import com.example.Project1.service.ShipperService;
import com.example.Project1.service.ShoeService;
import com.example.Project1.service.UserService;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ShoeService shoeService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ShipperService shipperService;

    @Autowired
    private ShipperOrderService shipperOrderService;

    @GetMapping("/home")
    public String showHomePage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        User user = userService.getUserByID(customUserDetails.getID());
        model.addAttribute("user", user);
        return "admin_home";
    }

    @GetMapping("/product")
    public String showProductPage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        User user = userService.getUserByID(customUserDetails.getID());
        model.addAttribute("user", user);
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("wrap", new Wrap());
        return "admin_product";
    }

    @GetMapping("/order")
    public String showOderPage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model){
        User user = userService.getUserByID(customUserDetails.getID());
        model.addAttribute("user", user);
        model.addAttribute("orders", orderService.getAllOrder());
        return "admin_order";
    }

    @GetMapping("/user")
    public String showUserPage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        User user = userService.getUserByID(customUserDetails.getID());
        model.addAttribute("user", user);
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("new_user", new User());
        List<Integer> cartItemsIDs = new ArrayList<>();
        model.addAttribute("cartItemsIDs", cartItemsIDs);
        return "admin_user";
    }

    @PostMapping("/product/add")
    public String addProduct(@ModelAttribute("wrap") Wrap wrap){
        shoeService.addShoe(wrap);
        return "redirect:/admin/product";
    }

    @GetMapping("/product/delete_productID={id}")
    public String deleteProduct(@PathVariable("id") int id){
        productService.setStatus(0, id);
        return "redirect:/admin/product";
    }

    @GetMapping("/user/disable_user/user_id={id}")
    public String disableUser(@PathVariable("id") int ID){
        userService.setUserRole(ID, "USER_0");
        return "redirect:/admin/user";
    }

    @GetMapping("/user/enable_user/user_id={id}")
    public String enableUser(@PathVariable("id") int ID){
        userService.setUserRole(ID, "USER_1");
        return "redirect:/admin/user";
    }

    @PostMapping("/user/add_new_user")
    public String addNewUser(@ModelAttribute User new_user){
        adminService.add_new_user(new_user);
        return "redirect:/admin/user";
    }

    @GetMapping("/transfer")
    public String showTransferPage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model){
        User user = userService.getUserByID(customUserDetails.getID());
        model.addAttribute("user", user);
        return "admin_transfer";
    }

    @PostMapping("/product/transfer")
    public String transfer(@RequestParam(required = false) List<Integer> orderIDs, @AuthenticationPrincipal CustomUserDetails customUserDetails, Model model){
        List<Order> orders = orderService.getAllOrdersWithID(orderIDs);
        model.addAttribute("orders", orders);
        model.addAttribute("user", userService.getUserByID(customUserDetails.getID()));
        return "admin_transfer";
    }

    @GetMapping("/shipper/searchShipper")
    public ResponseEntity<?> searchShipper(@RequestParam(required = false) String numberphone){
        Shipper shipper = shipperService.getShipperByPhone(numberphone);
        if (shipper == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy shipper");
        }
        return ResponseEntity.ok(shipper);
    }

    @PostMapping("/orders/assign-shipper")
    public String assignShipper(@ModelAttribute ShipperOrderDTO shipperOrderDTO){
        shipperOrderService.addItem(shipperOrderDTO);
        return "redirect:/admin/order";
    }

}
