package com.scaler.productserviceapr21capstone.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.scaler.productserviceapr21capstone.Exceptions.ProductNotFoundException;
import com.scaler.productserviceapr21capstone.Exceptions.ProductsNotAvailableException;
import com.scaler.productserviceapr21capstone.Repositories.CategoryRepository;
import com.scaler.productserviceapr21capstone.Repositories.ProductRepository;
import com.scaler.productserviceapr21capstone.models.Category;
import com.scaler.productserviceapr21capstone.models.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productDbService")
public class ProductDbService implements ProductService{


    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public ProductDbService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Product getProductById(long id) throws ProductNotFoundException {
        return null;
    }

    @Override
    public List<Product> getAllProducts() throws ProductsNotAvailableException {
        return List.of();
    }

    @Override
    public Product createProduct(String name, String description, double price, String imageUrl, String category) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(imageUrl);
        Category category1 = getCategoryFromDb(category);
        category1.setName(category);
        product.setCategory(category1);
        productRepository.save(product);
        return product;
    }

    @Override
    public Product replaceProduct(long id, String name, String description, double price, String imageUrl, String category) {
        return null;
    }

    @Override
    public Product applyPatchToProduct(long id, JsonPatch patch) throws ProductNotFoundException, JsonPatchException, JsonProcessingException {
        return null;
    }

    private Category getCategoryFromDb(String categoryName) {
        Category category = categoryRepository.findByName(categoryName);
        if(category != null){
            return category;
        }
        Category newCategory = new Category();
        newCategory.setName(categoryName);
        categoryRepository.save(newCategory);
        return newCategory;
    }
}
