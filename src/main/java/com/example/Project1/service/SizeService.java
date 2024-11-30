package com.example.Project1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Project1.model.Size;
import com.example.Project1.repository.SizeRepository;

@Service
public class SizeService {
    @Autowired
    private SizeRepository sizeRepository;

    public Size getSizeBySize(int size){
        return sizeRepository.findBySize(size).orElse(null);
    }

    public Size addSize(Size size){
        return sizeRepository.save(size);
    }
}
