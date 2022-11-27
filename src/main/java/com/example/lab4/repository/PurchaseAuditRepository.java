package com.example.lab4.repository;

import com.example.lab4.model.PurchaseAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseAuditRepository extends JpaRepository<PurchaseAudit, Integer> {
}
