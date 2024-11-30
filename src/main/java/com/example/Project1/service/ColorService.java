package com.example.Project1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Project1.model.Color;
import com.example.Project1.repository.ColorRepository;

@Service
public class ColorService {
    @Autowired
    private ColorRepository colorRepository;

    public Color getColorByColor(String color){
        return colorRepository.findByColor(color).orElse(null);
    }

    public Color addColor(Color color){
        return colorRepository.save(color);
    }
}
