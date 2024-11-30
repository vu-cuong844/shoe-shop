package com.example.Project1.model;

import com.example.Project1.dto.ShoeDTO;


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
@Table(name = "product_variant")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Shoe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @ManyToOne()
    @JoinColumn(name = "ID_Product")
    private Product product;

    @ManyToOne()
    @JoinColumn(name = "ID_Size")
    private Size size;

    @ManyToOne()
    @JoinColumn(name = "ID_Color")
    private Color color;

    @Column(name = "Quantity")
    private int quantity;

    public Shoe(Product product, ShoeDTO shoeDTO){
        this.product = product;
        this.size = new Size(shoeDTO.getSize());
        this.color = new Color(shoeDTO.getColor());
        this.quantity = shoeDTO.getQuantity();
    }
}
