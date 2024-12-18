package com.projectj2ee.travel_server.repository;

import com.projectj2ee.travel_server.entity.TourPackage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TourPackageRepository extends JpaRepository<TourPackage,Integer> {
    Boolean existsById(int id);
    Page<TourPackage> findAll(Pageable pageable);

    @Query(value = "SELECT t FROM TourPackage t ORDER BY t.tourCode DESC")
    Optional<TourPackage> findFirstByOrderByTourCodeDesc();

    TourPackage findByTourCode(String tourCode);
    Page<TourPackage> findByDepart(String depart, Pageable pageable);
}
