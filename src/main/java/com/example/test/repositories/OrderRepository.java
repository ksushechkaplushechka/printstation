package com.example.test.repositories;

import com.example.test.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByContractNumberContainingIgnoreCase(String contractNumber); // Измените на правильное поле
}
