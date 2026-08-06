package com.agrifarms.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agrifarms.common.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {

    List<Booking> findByFarmerId(String farmerId);

    List<Booking> findByProviderId(String providerId);

    long countByProviderIdAndStatusIn(String providerId, java.util.Collection<String> statuses);

    List<Booking> findByAssetId(String assetId);

    long countByAssetIdAndStatusIn(String assetId, java.util.Collection<String> statuses);
}
