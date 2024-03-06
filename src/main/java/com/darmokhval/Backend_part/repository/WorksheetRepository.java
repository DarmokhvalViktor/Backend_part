package com.darmokhval.Backend_part.repository;

import com.darmokhval.Backend_part.model.entity.tests.Worksheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface WorksheetRepository extends JpaRepository<Worksheet, Integer>, JpaSpecificationExecutor<Worksheet> {
}
