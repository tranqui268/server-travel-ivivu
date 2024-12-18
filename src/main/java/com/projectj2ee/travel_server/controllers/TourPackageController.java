package com.projectj2ee.travel_server.controllers;

import com.projectj2ee.travel_server.dto.request.TourPackageRequest;
import com.projectj2ee.travel_server.dto.response.ApiResponse;
import com.projectj2ee.travel_server.dto.response.PageResponse;
import com.projectj2ee.travel_server.dto.response.TourPackageResponse;
import com.projectj2ee.travel_server.entity.TourPackage;
import com.projectj2ee.travel_server.service.GoogleDriveService;
import com.projectj2ee.travel_server.service.TourPackageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/tour_package")
@AllArgsConstructor
public class TourPackageController {
    private final TourPackageService tourPackageService;
    private  final GoogleDriveService googleDriveService;

    @GetMapping("/")
    public PageResponse<TourPackageResponse> getAllTourPackage(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "12") int size
    ){
        return tourPackageService.getAllTourPackage(page, size);
    }

    @GetMapping("/{id}")
    public ApiResponse<TourPackageResponse> getTourPackageById(@PathVariable("id") int id){
        return tourPackageService.getTourPackageById(id);
    }

    @GetMapping("/depart")
    public PageResponse<TourPackage> getAllTourPackageByDepart(
            @RequestParam(value = "depart", required = true) String depart,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "12") int size
    ){
        return tourPackageService.getAllTourPackageByDepart(depart,page, size);
    }

    @PostMapping("/create")
    public ApiResponse<TourPackage> createTourPackage(@RequestBody TourPackageRequest tourPackageRequest,
                                                      @RequestParam("files")MultipartFile[] files,
                                                      @RequestParam("filetxt")MultipartFile filetxt){
        return tourPackageService.addTourPackage(tourPackageRequest,files,filetxt);
    }

    @PutMapping("/{id}")
    public  ApiResponse<TourPackage> editTourPackage(@PathVariable("id") int id,
                                                     @RequestBody TourPackageRequest tourPackageRequest){
        return tourPackageService.editTourPackage(id, tourPackageRequest);
    }

    @PutMapping("/add-image/{id}")
    public ApiResponse<String> addImage(@PathVariable("id") int id,
                                        @RequestParam("files")MultipartFile[] files){
        return tourPackageService.addImageTourPackage(files, id);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<TourPackage> deleteTourPackage(@PathVariable("id")int id){
        return tourPackageService.deleteTourPackage(id);
    }

    @PostMapping("/test")
    public String uploadFile( @RequestParam("file")MultipartFile file) throws IOException, GeneralSecurityException {
        if (file.isEmpty()){
            return "File is empty";
        }
//        File tempFile = File.createTempFile("temp",null);
//        file.transferTo(tempFile);
        return googleDriveService.uploadFile(file,"T001");
    }
}
