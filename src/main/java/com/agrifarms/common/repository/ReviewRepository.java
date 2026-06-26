package com.agrifarms.common.repository;

import com.agrifarms.common.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
    List<Review> findByAssetId(String assetId);
    Optional<Review> findByBookingId(String bookingId);
}
