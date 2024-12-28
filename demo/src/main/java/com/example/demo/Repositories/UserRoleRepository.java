package com.example.demo.Repositories;

import com.example.demo.Entities.Role;
import com.example.demo.Entities.enums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findRoleByName(UserRoles name);
}
