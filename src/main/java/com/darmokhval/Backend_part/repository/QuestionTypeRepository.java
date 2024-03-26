package com.darmokhval.Backend_part.repository;

import com.darmokhval.Backend_part.model.entity.tests.EQuestionType;
import com.darmokhval.Backend_part.model.entity.tests.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionTypeRepository extends JpaRepository<QuestionType, Long> {
    Optional<QuestionType> findByType(EQuestionType type);
}
