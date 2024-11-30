package com.example.Project1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Project1.model.Size;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
    Optional<Size> findBySize(int size);
}
