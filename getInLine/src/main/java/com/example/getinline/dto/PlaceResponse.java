package com.example.getinline.dto;

import com.example.getinline.constant.PlaceType;

public record PlaceResponse(
        PlaceType placeType,
        String placeName,
        String address,
        String phoneNumber,
        Integer capacity,
        String memo
) {
    public static PlaceResponse of(
            PlaceType placeType,
            String placeName,
            String address,
            String phoneNumber,
            Integer capacity,
            String memo
    ) {
        return new PlaceResponse(placeType, placeName, address, phoneNumber, capacity, memo);
    }
}