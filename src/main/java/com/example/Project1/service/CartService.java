package com.example.Project1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Project1.model.Cart;
import com.example.Project1.model.CartItem;
import com.example.Project1.model.User;
import com.example.Project1.repository.CartItemReposritory;
import com.example.Project1.repository.CartRepository;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemReposritory cartItemReposritory;

    public Cart getCartByUserID(int ID){
        return cartRepository.findCartByUserID(ID);
    }

    public List<CartItem> getCartOfUser(User user){
        return cartRepository.findCartItemsByUserID(user.getID());
    }

    public String addToCart(CartItem cartItem){
        CartItem cartItem2 = cartItemReposritory.findCartItemByProductIdAndCartID(cartItem.getShoe().getID(), cartItem.getCart().getID());
        if(cartItem2 != null){
            cartItem2.setQuantity(cartItem2.getQuantity() + cartItem.getQuantity());
            cartItemReposritory.save(cartItem2);
        }else{
            cartItemReposritory.save(cartItem);
        }
        return "Thêm thành công";
    }
}
