package com.agrifarms.common.controller;

import com.agrifarms.common.dto.DtoMapper;
import com.agrifarms.common.dto.ReviewDTO;
import com.agrifarms.common.entity.Review;
import com.agrifarms.common.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    private final ReviewService reviewService;
    private final DtoMapper dtoMapper;

    public ReviewController(ReviewService reviewService, DtoMapper dtoMapper) {
        this.reviewService = reviewService;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping
    public ReviewDTO submitReview(@RequestBody ReviewDTO reviewDTO) {
        Review review = dtoMapper.toReviewEntity(reviewDTO);
        Review saved = reviewService.saveReview(review);
        return dtoMapper.toReviewDTO(saved);
    }

    @GetMapping("/asset/{assetId}")
    public List<ReviewDTO> getReviewsForAsset(@PathVariable String assetId) {
        return reviewService.getReviewsByAssetId(assetId)
                .stream()
                .map(dtoMapper::toReviewDTO)
                .collect(Collectors.toList());
    }
}
