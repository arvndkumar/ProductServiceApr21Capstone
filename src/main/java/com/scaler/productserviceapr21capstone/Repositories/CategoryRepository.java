package com.scaler.productserviceapr21capstone.Repositories;

import com.scaler.productserviceapr21capstone.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>
{
    Category  findByName(String category);
    Category save(Category category);
}
