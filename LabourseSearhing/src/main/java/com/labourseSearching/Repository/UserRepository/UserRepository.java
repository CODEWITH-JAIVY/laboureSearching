package com.labourseSearching.Repository.UserRepository;

import com.labourseSearching.Entity.Users.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(@NotBlank(message = "Email required") @Email(message = "Invalid email") String email);
}