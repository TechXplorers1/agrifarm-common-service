package com.agrifarms.common.repository;

import com.agrifarms.common.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    List<Booking> findByFarmerId(String farmerId);

    List<Booking> findByProviderId(String providerId);
<<<<<<< HEAD
    long countByProviderIdAndStatusIn(String providerId, java.util.Collection<String> statuses);
=======

    List<Booking> findByAssetId(String assetId);
>>>>>>> 6a3fc0c1aeaf20611009613722e2f788ea1da2fb
}
