package com.example.Project1.dto;

import java.util.List;

import com.example.Project1.model.Product;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Wrap {
    private Product product;
    private List<ShoeDTO> details;
}
