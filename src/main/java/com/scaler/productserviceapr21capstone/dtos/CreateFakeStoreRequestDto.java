package com.scaler.productserviceapr21capstone.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateFakeStoreRequestDto {

    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private String category;

}
