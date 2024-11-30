package com.example.Project1.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Project1.model.Shipper;
import com.example.Project1.model.User;
import com.example.Project1.repository.ShipperRepository;
import com.example.Project1.repository.UserRepository;

@Service
public class AdminService {
    @Autowired
    private ShipperRepository shipperRepository;

    @Autowired
    private UserRepository userRepository;

    public String add_new_user(User user){
        if(userRepository.findByPhone(user.getPhone()).isPresent()){
            return "User already exists";
        }
        User newUser = userRepository.save(user);
        if (newUser.getRole().equals("SHIPPER")){
            Shipper shipper = new Shipper();
            shipper.setUser(newUser);
            shipper.setCreate_date(LocalDateTime.now());
            shipperRepository.save(shipper);
        }
        return "Thành công";
    }
}
