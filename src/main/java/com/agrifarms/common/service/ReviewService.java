package com.agrifarms.common.service;

import com.agrifarms.common.entity.Review;
import com.agrifarms.common.repository.ReviewRepository;
import com.agrifarms.common.repository.EquipmentRepository;
import com.agrifarms.common.repository.TransportVehicleRepository;
import com.agrifarms.common.repository.ServiceOfferingRepository;
import com.agrifarms.common.repository.WorkerGroupRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final EquipmentRepository equipmentRepository;
    private final TransportVehicleRepository vehicleRepository;
    private final ServiceOfferingRepository serviceRepository;
    private final WorkerGroupRepository workerGroupRepository;

    public ReviewService(ReviewRepository reviewRepository,
                         EquipmentRepository equipmentRepository,
                         TransportVehicleRepository vehicleRepository,
                         ServiceOfferingRepository serviceRepository,
                         WorkerGroupRepository workerGroupRepository) {
        this.reviewRepository = reviewRepository;
        this.equipmentRepository = equipmentRepository;
        this.vehicleRepository = vehicleRepository;
        this.serviceRepository = serviceRepository;
        this.workerGroupRepository = workerGroupRepository;
    }

    public Review saveReview(Review review) {
        Review saved = reviewRepository.save(review);
        updateAssetRating(review.getAssetId());
        return saved;
    }

    public List<Review> getReviewsByAssetId(String assetId) {
        return reviewRepository.findByAssetId(assetId);
    }

    public boolean hasReviewForBooking(String bookingId) {
        return reviewRepository.findByBookingId(bookingId).isPresent();
    }

    private void updateAssetRating(String assetId) {
        List<Review> reviews = reviewRepository.findByAssetId(assetId);
        if (reviews.isEmpty()) return;

        double sum = 0;
        for (Review r : reviews) {
            sum += r.getRating();
        }
        double avg = sum / reviews.size();
        BigDecimal roundedAvg = BigDecimal.valueOf(avg).setScale(1, RoundingMode.HALF_UP);

        // Try updating equipment
        equipmentRepository.findById(assetId).ifPresent(e -> {
            e.setRating(roundedAvg);
            equipmentRepository.save(e);
        });

        // Try updating vehicle
        vehicleRepository.findById(assetId).ifPresent(v -> {
            v.setRating(roundedAvg);
            vehicleRepository.save(v);
        });

        // Try updating service
        serviceRepository.findById(assetId).ifPresent(s -> {
            s.setRating(roundedAvg);
            serviceRepository.save(s);
        });

        // Try updating worker group
        workerGroupRepository.findById(assetId).ifPresent(w -> {
            w.setRating(roundedAvg);
            workerGroupRepository.save(w);
        });
    }
}
