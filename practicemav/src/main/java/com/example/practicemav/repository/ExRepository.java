package com.example.practicemav.repository;

import com.example.practicemav.model.Exhibition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExRepository extends JpaRepository<Exhibition, Long> {
}
