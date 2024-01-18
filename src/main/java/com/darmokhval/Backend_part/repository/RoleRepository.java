package com.darmokhval.Backend_part.repository;

import com.darmokhval.Backend_part.entity.ERole;
import com.darmokhval.Backend_part.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
