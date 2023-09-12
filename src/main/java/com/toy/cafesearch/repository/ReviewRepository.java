package com.toy.cafesearch.repository;

import com.toy.cafesearch.Service.ReivewService;
import com.toy.cafesearch.dto.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findAllByCafeName(String cafeName);
}
