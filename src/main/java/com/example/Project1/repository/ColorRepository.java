package com.example.Project1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Project1.model.Color;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long>{
    Optional<Color> findByColor(String color);
}
