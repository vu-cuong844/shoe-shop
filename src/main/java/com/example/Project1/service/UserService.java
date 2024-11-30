package com.example.Project1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Project1.config.CustomUserDetails;
import com.example.Project1.model.Cart;
import com.example.Project1.model.User;
import com.example.Project1.repository.CartRepository;
import com.example.Project1.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
    
    @Autowired
    private UserRepository userReposity;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userReposity.findAll();
    }

    public User getUserByID(int ID){
        return userReposity.findByID(ID).orElse(null);
    }

    public User getUserByPhone(String numberphone){
        return userReposity.findByPhone(numberphone).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userReposity.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CustomUserDetails(user.getID(), user.getUsername(), user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole()));
    }



    public String registerUser(User user){
        if(userReposity.findByUsername(user.getUsername()).isPresent()){
            return "User already exists";
        }

        if(userReposity.findByPhone(user.getPhone()).isPresent()){
            return "Phone number already exists";
        }



        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER_1");
        userReposity.save(user);

        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);

        return "Đăng ký thành công";

    }

    public String updateUser(User user){
        if(userReposity.existsById((long) user.getID())){
            userReposity.save(user);
            return "Update successfully";
        }
        return "User not found. Update failed!";
    }

    public void setUserRole(int ID, String role){
        userReposity.updateUserRole(role, ID);
    }


    
}
