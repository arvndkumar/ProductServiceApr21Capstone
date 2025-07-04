package com.scaler.productserviceapr21capstone.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreRequestDto {
    private String title;
    private String description;
    private double price;
    private String image;
    private String category;
}
