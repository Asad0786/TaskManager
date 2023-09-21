package com.taskmanger.app.tm.repository;

import com.taskmanger.app.tm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String username);
}
