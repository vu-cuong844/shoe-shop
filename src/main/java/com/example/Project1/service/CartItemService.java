package com.example.Project1.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.Project1.model.CartItem;
import com.example.Project1.repository.CartItemReposritory;

@Repository
public class CartItemService {
    @Autowired
    private CartItemReposritory cartItemReposritory;

    public CartItem addCartItem(CartItem cartItem){
        return cartItemReposritory.save(cartItem);
    }

    public CartItem getCartItemByID(int ID){
        return cartItemReposritory.findCartItemByID(ID);
    }

    public CartItem getCartItemByShoeIDAndCartID(int shoeID, int cartID){
        return cartItemReposritory.findCartItemByProductIdAndCartID(shoeID, cartID);
    }

    public void deleteCartItemByID(int ID){
        cartItemReposritory.deleteCartItemByID(ID);
    }

    public List<CartItem> getAllCartItems(List<Integer> IDs){
        List<CartItem> cartItems = new ArrayList<>();
        for (int ID : IDs) {
            cartItems.add(cartItemReposritory.findCartItemByID(ID));
        }
        return cartItems;
    }

    public void deleteCartItems(List<CartItem> cartItems){
        for (CartItem cartItem : cartItems) {
            cartItemReposritory.deleteCartItemByID(cartItem.getID());
        }
    }
}
