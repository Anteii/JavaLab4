package com.example.lab4.repository;

import com.example.lab4.model.BookAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookAuditRepository extends JpaRepository<BookAudit, Integer> {
}
