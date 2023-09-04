package com.toy.cafesearch.repository;

import com.toy.cafesearch.dto.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
