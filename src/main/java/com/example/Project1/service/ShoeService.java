package com.example.Project1.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Project1.dto.ShoeDTO;
import com.example.Project1.dto.Wrap;
import com.example.Project1.model.Color;
import com.example.Project1.model.Product;
import com.example.Project1.model.Shoe;
import com.example.Project1.model.Size;
import com.example.Project1.repository.ColorRepository;
import com.example.Project1.repository.ShoeRepository;
import com.example.Project1.repository.SizeRepository;

@Service
public class ShoeService {
    @Autowired
    private ShoeRepository shoeRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private ColorService colorService;

    public List<Shoe> getAllShoes(){
        return shoeRepository.findAll();
    }

    public List<Shoe> getAllShoesByID_Product(int ID_Product) {
        return shoeRepository.findByProduct_ID(ID_Product);
    }
    
    public Shoe getShoeByIDColorSize(int ID, int size, String color){
        return shoeRepository.findShoeByIDColorSize(ID, size, color);
    }

    public Shoe getShoeByID(int ID){
        return shoeRepository.findShoeByID(ID);
    }

    public void setQuantity(int shoeID, int new_quantity){
        shoeRepository.updateQuantity(shoeID, new_quantity);
    }

    public String addShoe(Wrap wrap) {
        Product product = wrap.getProduct();
        List<Shoe> shoes = new ArrayList<>();

        productService.addProduct(product);

        for (ShoeDTO shoeDTO : wrap.getDetails()) {
            Size size = sizeService.getSizeBySize(shoeDTO.getSize());
            if (size == null) {
                size = new Size();
                size.setSize(shoeDTO.getSize());
                sizeRepository.save(size); // Lưu size nếu chưa có
            }

            Color color = colorService.getColorByColor(shoeDTO.getColor());
            if (color == null) {
                color = new Color();
                color.setColor(shoeDTO.getColor());
                colorRepository.save(color); // Lưu color nếu chưa có
            }

            Shoe shoe = new Shoe();
            shoe.setProduct(product);
            shoe.setSize(size);
            shoe.setColor(color);
            shoe.setQuantity(shoeDTO.getQuantity());

            shoes.add(shoe); // Thêm vào danh sách thay vì lưu ngay lập tức
        }

        shoeRepository.saveAll(shoes); // Lưu tất cả các shoe sau khi đã xây dựng xong
        return "Thành công";
    }
}
