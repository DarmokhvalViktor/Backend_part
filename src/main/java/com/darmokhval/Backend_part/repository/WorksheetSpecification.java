package com.darmokhval.Backend_part.repository;

import com.darmokhval.Backend_part.model.entity.tests.Worksheet;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class WorksheetSpecification {

    public static Specification<Worksheet> withFilters(String grade, String subject, String title) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(grade != null && !grade.isEmpty()) {
                predicates.add(criteriaBuilder
                        .like(criteriaBuilder.upper(root.get("grade")),
                                "%" + grade.toUpperCase() + "%"));
            }
            if(subject != null && !subject.isEmpty()) {
                predicates.add(criteriaBuilder
                        .like(criteriaBuilder.upper(root.get("subject")),
                                "%" + subject.toUpperCase() + "%"));
            }
            if(title != null && !title.isEmpty()) {
                predicates.add(criteriaBuilder
                        .like(criteriaBuilder.upper(root.get("title")),
                                "%" + title.toUpperCase() + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
