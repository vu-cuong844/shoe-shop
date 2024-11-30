package com.example.Project1.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.Project1.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername (String Username);

    Optional<User> findByPhone(String Phone_Number);

    Optional<User> findByEmail(String email);

    Optional<User> findByID(int ID);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.role = :new_role WHERE u.ID = :userID")
    void updateUserRole(@Param("new_role") String newRole, @Param("userID") int userID);
}
