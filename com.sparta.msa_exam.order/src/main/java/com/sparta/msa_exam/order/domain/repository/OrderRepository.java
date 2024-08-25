package com.sparta.msa_exam.order.domain.repository;

import com.sparta.msa_exam.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}