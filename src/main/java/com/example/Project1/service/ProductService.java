package com.example.Project1.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.Project1.model.Product;
import com.example.Project1.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Value("${upload.dir}")
    private String uploadDir;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getAllProductSale(){
        return productRepository.findProductByStatus(1);
    }

    public Product getById(int id){
        return productRepository.findByID(id).orElse(null);
    }

    public void setStatus(int status, int ID){
        productRepository.updateProductStatus(status, ID);
    }

    public String addProduct(Product product) {

        if (productRepository.findByName(product.getName()).isPresent()) {
            return "Sản phẩm đã tồn tại";
        }
    
        try {
           
            if (!product.getImage().isEmpty()) {
    
                Path uploadPath = Path.of(uploadDir, "product");
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);  
                }
            
                String fileName = System.currentTimeMillis() + "_" + product.getImage().getOriginalFilename();
                Path targetLocation = uploadPath.resolve(fileName);
    
                Files.copy(product.getImage().getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
    
                product.setImage_url("/images/product/" + fileName);
            }
            product.setStatus(1);
            productRepository.save(product);
            return "Thêm sản phẩm thành công";
            
        } catch (IOException e) {
            return "Lưu thất bại";
        }
    }
    
}
