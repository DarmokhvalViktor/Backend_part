package com.darmokhval.Backend_part.repository;

import com.darmokhval.Backend_part.model.entity.tests.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
}
