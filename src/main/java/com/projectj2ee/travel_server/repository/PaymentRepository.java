package com.projectj2ee.travel_server.repository;

import com.projectj2ee.travel_server.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Page<Payment> findAll(Pageable pageable);
}