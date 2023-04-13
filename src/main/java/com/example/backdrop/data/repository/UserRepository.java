package com.example.backdrop.data.repository;

import com.example.backdrop.data.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByNameIgnoreCase(String name);
    @Modifying
    @Transactional
    @Query("update User user set user.isVerified=true where user.name=?1")
    void isVerified(String name);

}
