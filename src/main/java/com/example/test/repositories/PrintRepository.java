package com.example.test.repositories;

import com.example.test.model.Print;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrintRepository extends JpaRepository<Print, Long> {
    List<Print> findByNameContainingIgnoreCase(String name);
}
