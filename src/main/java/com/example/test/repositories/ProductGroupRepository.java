package com.example.test.repositories;

import com.example.test.model.ProductGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductGroupRepository extends JpaRepository<ProductGroup, Long> {
    // Измените метод на следующее
    List<ProductGroup> findByGroupNameContainingIgnoreCase(String name);
}
