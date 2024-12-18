package com.projectj2ee.travel_server.service;

import com.projectj2ee.travel_server.dto.request.TourFeatureRequest;
import com.projectj2ee.travel_server.dto.response.ApiResponse;
import com.projectj2ee.travel_server.entity.TourFeature;
import com.projectj2ee.travel_server.repository.TourFeatureRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TourFeatureService {
    @Autowired
    private final TourFeatureRepository tourFeatureRepository;

    public ApiResponse<List<TourFeature>> getAllTourFeature(){
        return new ApiResponse<List<TourFeature>>(HttpStatus.OK.value(), "Success",tourFeatureRepository.findAll());
    }

    public ApiResponse<TourFeature> getTourFeatureById(int id){
        TourFeature entity = tourFeatureRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("TourFeature not found"));
        return new ApiResponse<TourFeature>(HttpStatus.OK.value(), "Success",entity);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<TourFeature> addTourFeature(TourFeatureRequest tourFeatureRequest){
        TourFeature entity = new TourFeature();
        entity.setName(tourFeatureRequest.getName());
        entity.setDescription(tourFeatureRequest.getDescription());
        entity.setStatus(true);
        tourFeatureRepository.save(entity);
        return new ApiResponse<TourFeature>(HttpStatus.CREATED.value(), "Create Success",entity);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<TourFeature> editTourFeature(int id,TourFeatureRequest tourFeatureRequest){
        TourFeature entity = tourFeatureRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("TourFeature not found"));
        entity.setId(id);
        entity.setName(tourFeatureRequest.getName());
        entity.setDescription(tourFeatureRequest.getDescription());
        entity.setStatus(true);
        tourFeatureRepository.save(entity);
        return new ApiResponse<TourFeature>(HttpStatus.OK.value(), "Update Success",entity);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<TourFeature> deleteTourFeature(int id){
        TourFeature entity = tourFeatureRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("TourFeature not found"));
        entity.setStatus(false);
        tourFeatureRepository.save(entity);
        return new ApiResponse<TourFeature>(HttpStatus.OK.value(), "Delete Success",entity);
    }

}
