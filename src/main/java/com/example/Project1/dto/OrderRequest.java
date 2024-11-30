package com.example.Project1.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private int userID;
    private String receiver;
    private String delivery_address;
    private String phonenumber;
    private List<Integer> cartItemIDs;
    private String payment_mehtod;
}
