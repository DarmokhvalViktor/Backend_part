package com.darmokhval.Backend_part.repository;

import com.darmokhval.Backend_part.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByFirstName(String firstName);
}
