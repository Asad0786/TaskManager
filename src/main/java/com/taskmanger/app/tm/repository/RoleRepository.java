package com.taskmanger.app.tm.repository;

import com.taskmanger.app.tm.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String user);
}
