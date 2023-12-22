package com.darmokhval.Backend_part.repository;

import com.darmokhval.Backend_part.entity.tests.TestContainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestContainerRepository extends JpaRepository<TestContainer, Integer> {
}
