package com.example.lab4.repository;


import com.example.lab4.model.DBChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends JpaRepository<DBChange, Integer> {
}
