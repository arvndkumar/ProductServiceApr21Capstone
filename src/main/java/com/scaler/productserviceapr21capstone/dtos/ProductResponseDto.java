package com.scaler.productserviceapr21capstone.dtos;

import com.scaler.productserviceapr21capstone.models.Category;
import com.scaler.productserviceapr21capstone.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto {
    private long id;
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private String category;

    public static ProductResponseDto from(Product product){
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(product.getId());
        productResponseDto.setName(product.getName());
        productResponseDto.setDescription(product.getDescription());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setImageUrl(product.getImageUrl());
        productResponseDto.setCategory(product.getCategory().getName());

        return productResponseDto;
    }
}
