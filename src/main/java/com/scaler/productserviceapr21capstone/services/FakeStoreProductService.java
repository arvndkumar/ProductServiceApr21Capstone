package com.scaler.productserviceapr21capstone.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.scaler.productserviceapr21capstone.Exceptions.ProductNotFoundException;
import com.scaler.productserviceapr21capstone.Exceptions.ProductsNotAvailableException;
import com.scaler.productserviceapr21capstone.dtos.FakeStoreRequestDto;
import com.scaler.productserviceapr21capstone.dtos.FakeStoreResponseDto;
import com.scaler.productserviceapr21capstone.models.Product;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductService {

    RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate =  restTemplate;
    }

    @Override
    public Product getProductById(long id) throws ProductNotFoundException {
        FakeStoreResponseDto fakeStoreResponseDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + id, FakeStoreResponseDto.class);

        if (fakeStoreResponseDto == null) {
            throw new ProductNotFoundException("Product with id " + id +" not found");
        }
        return fakeStoreResponseDto.toProduct();
    }

    @Override
    public List<Product> getAllProducts() throws ProductsNotAvailableException {

        FakeStoreResponseDto[] fakeStoreResponseDtos = restTemplate.getForObject("https://fakestoreapi.com/products/", FakeStoreResponseDto[].class);

        if (fakeStoreResponseDtos == null) {
            throw new ProductsNotAvailableException("Products not available");
        }

        ArrayList<Product> products = new ArrayList<>();

        for (FakeStoreResponseDto fakeStoreResponseDto : fakeStoreResponseDtos) {
            products.add(fakeStoreResponseDto.toProduct());
        }

        return products;
    }

    @Override
    public Product createProduct(String name, String description, double price, String imageUrl, String category) {
        FakeStoreRequestDto fakeStoreRequestDto = createFakeStoreRequestDto(name, description,price, imageUrl, category);
        FakeStoreResponseDto fakeStoreResponseDto = restTemplate.postForObject("https://fakestoreapi.com/products", fakeStoreRequestDto, FakeStoreResponseDto.class);
        return fakeStoreResponseDto.toProduct();
    }

    @Override
    public Product replaceProduct(long id, String name, String description, double price, String imageUrl, String category) {
        FakeStoreRequestDto updatedFakeStoreRequestDto = createFakeStoreRequestDto(name, description,price, imageUrl, category);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<FakeStoreRequestDto> requestEntity = new HttpEntity<>(updatedFakeStoreRequestDto, headers);

        ResponseEntity<FakeStoreResponseDto> responseEntity= restTemplate.exchange("https://fakestoreapi.com/products/" + id, HttpMethod.PUT, requestEntity, FakeStoreResponseDto.class);

        return responseEntity.getBody().toProduct();

        }

     private FakeStoreRequestDto createFakeStoreRequestDto(String name, String description, double price, String imageUrl, String category) {
        FakeStoreRequestDto fakeStoreRequestDto = new FakeStoreRequestDto();
        fakeStoreRequestDto.setTitle(name);
        fakeStoreRequestDto.setDescription(description);
        fakeStoreRequestDto.setPrice(price);
        fakeStoreRequestDto.setImage(imageUrl);
        fakeStoreRequestDto.setCategory(category);

        return fakeStoreRequestDto;
    }

    @Override
    public Product applyPatchToProduct(long id, JsonPatch patch) throws ProductNotFoundException, JsonPatchException, JsonProcessingException {
        //get existing product
        Product existingProduct = getProductById(id);

        //convert Product to Json format
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode productNode = objectMapper.valueToTree(objectMapper);

        //apply patch
        JsonNode patchNode = patch.apply(productNode);

        //convert back to product

        Product patchedProduct = objectMapper.treeToValue(patchNode, Product.class);

        return replaceProduct(id,
                patchedProduct.getName(),
                patchedProduct.getDescription(),
                patchedProduct.getPrice(),
                patchedProduct.getCategory().getName(),
                patchedProduct.getImageUrl()
        );
    }
}