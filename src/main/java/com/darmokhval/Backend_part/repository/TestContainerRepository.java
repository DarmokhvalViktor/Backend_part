package com.darmokhval.Backend_part.repository;

import com.darmokhval.Backend_part.entity.tests.TestContainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestContainerRepository extends JpaRepository<TestContainer, Integer> {
    List<TestContainer> findByGrade(String grade);
}
