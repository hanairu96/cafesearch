package com.toy.cafesearch.repository;

import com.toy.cafesearch.dto.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findAllByCafeId(String cafeId);
    List<Review> findByMemberId(String memberId);
}
