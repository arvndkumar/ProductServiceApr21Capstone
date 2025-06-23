package com.scaler.productserviceapr21capstone.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.scaler.productserviceapr21capstone.Exceptions.ProductNotFoundException;
import com.scaler.productserviceapr21capstone.Exceptions.ProductsNotAvailableException;
import com.scaler.productserviceapr21capstone.dtos.CreateFakeStoreRequestDto;
import com.scaler.productserviceapr21capstone.dtos.ProductResponseDto;
import com.scaler.productserviceapr21capstone.models.Product;
import com.scaler.productserviceapr21capstone.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {


    ProductService productService;

    public ProductController(@Qualifier("productDbService") ProductService productService) {
        this.productService = productService;
    }
    @GetMapping("/products/{id}")
    public ProductResponseDto getProductById(@PathVariable("id") long id) throws ProductNotFoundException {
        Product product = productService.getProductById(id);
        return ProductResponseDto.from(product);
    }
    @GetMapping("/products")
    public List<ProductResponseDto> getProducts() throws ProductsNotAvailableException {
        List<Product> products = productService.getAllProducts();

        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for (Product product : products) {
            productResponseDtos.add(ProductResponseDto.from(product));
        }
        return productResponseDtos;
    }


    @PostMapping("/products")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody CreateFakeStoreRequestDto createFakeStoreRequestDto) {

        Product product = productService.createProduct(createFakeStoreRequestDto.getName(),
                createFakeStoreRequestDto.getDescription(),
                createFakeStoreRequestDto.getPrice(),
                createFakeStoreRequestDto.getImageUrl(),
                createFakeStoreRequestDto.getCategory()
                );
        return new ResponseEntity<>(ProductResponseDto.from(product),HttpStatus.CREATED);


    }

    @PutMapping("/products/{id}")
    public ProductResponseDto replaceProduct(@PathVariable("id") long id, @RequestBody CreateFakeStoreRequestDto createFakeStoreRequestDto) {

        Product product = productService.replaceProduct(
                id,
                createFakeStoreRequestDto.getName(),
                createFakeStoreRequestDto.getDescription(),
                createFakeStoreRequestDto.getPrice(),
                createFakeStoreRequestDto.getImageUrl(),
                createFakeStoreRequestDto.getCategory()
        );

        return ProductResponseDto.from(product);

    }

    @PatchMapping(
            path = "/products/{id}",
            consumes = "application/json-patch+json"
    )
    public ProductResponseDto updateProduct(@PathVariable("id") long id, @RequestBody JsonPatch jsonPatch) throws ProductNotFoundException, JsonPatchException, JsonProcessingException {

        Product product = productService.applyPatchToProduct(id, jsonPatch);
        return ProductResponseDto.from(product);

    }


}
