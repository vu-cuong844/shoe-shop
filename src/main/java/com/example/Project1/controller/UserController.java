package com.example.Project1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.Project1.dto.OrderRequest;
import com.example.Project1.dto.ShoeDTO;
import com.example.Project1.model.Cart;
import com.example.Project1.model.CartItem;
import com.example.Project1.model.Product;
import com.example.Project1.model.Shoe;
import com.example.Project1.model.User;
import com.example.Project1.service.CartItemService;
import com.example.Project1.service.CartService;
import com.example.Project1.service.ProductService;
import com.example.Project1.service.ShoeService;
import com.example.Project1.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ShoeService shoeService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @GetMapping("/home")
    public String getAllUser(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        model.addAttribute("user", userService.getUserByID(customUserDetails.getID()));
        List<Product> products = productService.getAllProductSale();
        model.addAttribute("products", products);
        return "home";
    }

    @GetMapping("/reject")
    public String showRejectPage() {
        return "user_reject";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        String result = userService.registerUser(user);
        model.addAttribute("message", result);
        return "signup";
    }

    @GetMapping("/account")
    public String account(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        User user = userService.getUserByID(customUserDetails.getID());
        model.addAttribute("user", user);
        // System.out.println(user.getID());
        model.addAttribute("username", customUserDetails.getUsername());
        return "user_account";
    }

    @PostMapping("/updateAccount")
    public String updateAccount(@ModelAttribute("user") User user,
            @AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        // System.out.println(customUserDetails.getID());
        User currenUser = userService.getUserByID(customUserDetails.getID());

        currenUser.setFullName(user.getFullName());
        currenUser.setEmail(user.getEmail());
        currenUser.setPhone(user.getPhone());
        currenUser.setUsername(user.getUsername());
        currenUser.setDateOfBirth(user.getDateOfBirth());
        currenUser.setCountry(user.getCountry());
        currenUser.setProvine(user.getProvine());
        currenUser.setDistrict(user.getDistrict());
        currenUser.setStreet(user.getStreet());
        currenUser.setAddress(user.getAddress());

        String result = userService.updateUser(currenUser);
        model.addAttribute("message", result);
        model.addAttribute("username", currenUser.getUsername());
        model.addAttribute("user", userService.getUserByID(customUserDetails.getID()));
        return "user_account";
    }

    @GetMapping("/product/shoeID={id}")
    public String showProductDetailPage(@PathVariable("id") int ID,
            @AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        User user = userService.getUserByID(customUserDetails.getID());
        model.addAttribute("user", user);
        List<Shoe> shoes = shoeService.getAllShoesByID_Product(ID);
        Product product = shoes.getFirst().getProduct();
        ShoeDTO shoeDTO = new ShoeDTO();
        model.addAttribute("product", product);
        model.addAttribute("shoes", shoes);
        model.addAttribute("shoeDTO", shoeDTO);
        return "user_detailProduct";
    }

    @PostMapping("/product/buy/product_id={id}")
    public String buyShoe(@PathVariable("id") int ID, @ModelAttribute ShoeDTO shoeDTO,
            @AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        Shoe shoe = shoeService.getShoeByIDColorSize(ID, shoeDTO.getSize(), shoeDTO.getColor());

        if (shoe == null) {
            model.addAttribute("message", "Không có sản phẩm tương ứng");
            return this.showProductDetailPage(ID, customUserDetails, model);
            // return "redirect:/user/product/" + ID;
        }

        if (shoe.getQuantity() < shoeDTO.getQuantity()) {
            model.addAttribute("message", "Hết hàng!!!");
            return this.showProductDetailPage(ID, customUserDetails, model);
        }

        User user = userService.getUserByID(customUserDetails.getID());
        Cart cart = cartService.getCartByUserID(user.getID());
        CartItem cartItem = new CartItem(shoe, cart, shoeDTO.getQuantity());
        cartItemService.addCartItem(cartItem);
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem);
        model.addAttribute("user", user);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("message", null);
        return "user_payment";
    }

    @PostMapping("/product/addToCart/product_id={id}")
    public String addToCart(@PathVariable("id") int ID, @ModelAttribute ShoeDTO shoeDTO,
            @AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        Shoe shoe = shoeService.getShoeByIDColorSize(ID, shoeDTO.getSize(), shoeDTO.getColor());
        Cart cart = cartService.getCartByUserID(customUserDetails.getID());
        CartItem cartItem = new CartItem(shoe, cart, shoeDTO.getQuantity());
        String result = cartService.addToCart(cartItem);
        model.addAttribute("message", result);
        return "redirect:/user/product/shoeID=" + ID;
    }

    @GetMapping("/cart")
    public String showCartPage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        User user = userService.getUserByID(customUserDetails.getID());
        List<CartItem> shoes = cartService.getCartOfUser(user);
        List<CartItem> cartItems = new ArrayList<>();
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("shoes", shoes);
        model.addAttribute("user", user);

        return "user_cart";
    }

    @GetMapping("/product/delete_from_cart/{id}")
    public String deleteCartItem(@PathVariable("id") int ID) {
        cartItemService.deleteCartItemByID(ID);
        return "redirect:/user/cart";
    }

    @PostMapping("/cart/checkout")
    public String buyManyShoes(@RequestParam(required = false) List<Integer> cartItemsID,
            @AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        User user = userService.getUserByID(customUserDetails.getID());

        if (cartItemsID == null) {
            return "redirect:/user/cart";
        }

        List<CartItem> cartItems = cartItemService.getAllCartItems(cartItemsID);
        model.addAttribute("user", user);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("orderRequest", new OrderRequest());
        return "user_payment";
    }
}
