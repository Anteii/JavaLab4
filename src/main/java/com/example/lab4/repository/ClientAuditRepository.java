package com.example.lab4.repository;

import com.example.lab4.model.ClientAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientAuditRepository extends JpaRepository<ClientAudit, Integer> {
}
