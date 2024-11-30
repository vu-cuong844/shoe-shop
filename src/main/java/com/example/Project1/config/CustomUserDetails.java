package com.example.Project1.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails{

    private int ID;
    private String username;
    private String password;
    private Collection<GrantedAuthority> authorities;

    public CustomUserDetails(int ID, String username, String password, Collection<GrantedAuthority> authorities){
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public int getID() {
        return this.ID;
    }

    
}