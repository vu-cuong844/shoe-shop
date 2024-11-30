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
public class ShipperOrderDTO {
    private List<Integer> orderIDs;
    private int shipperID;
}
