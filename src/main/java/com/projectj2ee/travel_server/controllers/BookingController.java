package com.projectj2ee.travel_server.controllers;

import com.projectj2ee.travel_server.dto.request.BookingRequest;
import com.projectj2ee.travel_server.dto.response.ApiResponse;
import com.projectj2ee.travel_server.dto.response.PageResponse;
import com.projectj2ee.travel_server.entity.Booking;
import com.projectj2ee.travel_server.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
@AllArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/")
    public PageResponse<Booking> getAllBooking(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ){
        return bookingService.getAllBooking(page, size);
    }

    @GetMapping("/{id}")
    public  ApiResponse<Booking> getBookingId(@PathVariable("id") String id){
        return bookingService.getBookingById(id);
    }

    @PostMapping("/create")
    public  ApiResponse<Booking> createBooking(@RequestBody BookingRequest bookingRequest){
        return bookingService.addBooking(bookingRequest);
    }


    @Operation(summary = "Update booking", description = "action : 'confirm' use after send email and user confirm" +
            "<br>action : 'complete' use after user payment success" +
            "<br>action : 'cancelled' use after user cancelled booking or overtime payment")
    @PutMapping("/{action}/{id}")
    public ApiResponse<Booking> updateBooking(@PathVariable("action") String action, @PathVariable("id") String id){
        return bookingService.updateStatus(id,action);
    }

    @DeleteMapping("/{id}")
    public  ApiResponse<Void> deleteBooking(@PathVariable("id") String id){
        return bookingService.deleteBooking(id);
    }


}
