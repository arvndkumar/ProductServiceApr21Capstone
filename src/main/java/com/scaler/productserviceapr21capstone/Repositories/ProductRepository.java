package com.scaler.productserviceapr21capstone.Repositories;

import com.scaler.productserviceapr21capstone.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 1st Argument: Entity Type for the repository
 * 2nd Argument: Type of the primary key
 **/

public interface ProductRepository extends JpaRepository<Product, Long> {

}
