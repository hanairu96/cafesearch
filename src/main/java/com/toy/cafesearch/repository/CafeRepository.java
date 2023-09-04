package com.toy.cafesearch.repository;

import com.toy.cafesearch.dto.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeRepository extends JpaRepository<Cafe, String> {
}
