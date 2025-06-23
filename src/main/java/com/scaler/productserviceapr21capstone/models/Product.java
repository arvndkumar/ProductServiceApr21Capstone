package com.scaler.productserviceapr21capstone.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends Base {

    private String description;
    private double price;
    private String imageUrl;
    @ManyToOne
    private Category category;

}
