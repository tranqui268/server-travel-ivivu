package com.projectj2ee.travel_server.service;

import com.projectj2ee.travel_server.dto.request.TourDateRequest;
import com.projectj2ee.travel_server.dto.response.ApiResponse;
import com.projectj2ee.travel_server.entity.TourDate;
import com.projectj2ee.travel_server.mapper.TourDateMapper;
import com.projectj2ee.travel_server.repository.TourDateRepository;
import com.projectj2ee.travel_server.repository.TourPackageRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TourDateService {
    @Autowired
    private final TourDateRepository tourDateRepository;

    @Autowired
    private TourDateMapper tourDateMapper;

    @Autowired
    private final TourPackageRepository tourPackageRepository;

    public ApiResponse<List<TourDate>> getAllTourDate(){
        return new ApiResponse<List<TourDate>>(HttpStatus.OK.value(), "Success",tourDateRepository.findAll());
    }

    public ApiResponse<TourDate> getTourDateById(String id){
        TourDate entity = tourDateRepository.findById(Long.parseLong(id))
                .orElseThrow(()->new RuntimeException("TourDate not found"));
        return new ApiResponse<TourDate>(HttpStatus.OK.value(), "Success",entity);
    }

    public ApiResponse<TourDate> addTourDate(TourDateRequest request){
        if (!checkPackageId(request.getPackageId()))
            throw new RuntimeException("Tour Package not found");
        TourDate entity = tourDateMapper.toTourDate(request);
        entity.setStatus(true);
        tourDateRepository.save(entity);
        return new ApiResponse<TourDate>(HttpStatus.CREATED.value(), "Create Success",entity);
    }

    public ApiResponse<TourDate> editTourDate(String id,TourDateRequest request){
        TourDate entity = tourDateRepository.findById(Long.parseLong(id))
                .orElseThrow(()->new RuntimeException("TourDate not found"));
        if (!checkPackageId(request.getPackageId()))
            throw new RuntimeException("Tour Package not found");
        entity = tourDateMapper.toTourDate(request);
        entity.setId(Integer.parseInt(id));
        entity.setStatus(true);
        tourDateRepository.save(entity);
        return new ApiResponse<TourDate>(HttpStatus.OK.value(), "Success",entity);
    }

    public ApiResponse<TourDate> deleteTourDateById(String id){
        TourDate entity = tourDateRepository.findById(Long.parseLong(id))
                .orElseThrow(()->new RuntimeException("TourDate not found"));
        entity.setStatus(true);
        tourDateRepository.save(entity);
        return new ApiResponse<TourDate>(HttpStatus.OK.value(), "Success",entity);
    }

    private Boolean checkPackageId(int id){
        return tourPackageRepository.existsById(id);
    }
}