package com.example.Project1.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "cart_item")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @JoinColumn(name = "ID_product")
    @ManyToOne
    private Shoe shoe;

    @JoinColumn(name = "ID_cart")
    @ManyToOne
    private Cart cart;

    @Column(name = "Quantity_buy")
    private int quantity;

    @Column(name = "Price")
    private double  price;

    public CartItem(Shoe shoe, Cart cart,int quantity){
        this.shoe = shoe;
        this.quantity = quantity;
        this.price = shoe.getProduct().getSale_price();
        this.cart =cart;
    }

}
