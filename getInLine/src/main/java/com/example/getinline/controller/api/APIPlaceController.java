package com.example.getinline.controller.api;

import com.example.getinline.constant.PlaceType;
import com.example.getinline.dto.APIDataResponse;
import com.example.getinline.dto.PlaceDTO;
import com.example.getinline.dto.PlaceRequest;
import com.example.getinline.dto.PlaceResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
//@RequestMapping("/api")
public class APIPlaceController {
    @GetMapping("/places")
    public APIDataResponse<List<PlaceDTO>> getPlaces(){

        return APIDataResponse.of(List.of(PlaceDTO.of(
                PlaceType.COMMON,
                "랄라배드민턴장",
                "서울시 강남구 강남대로 1234",
                "010-1234-5678",
                30,
                "신장개업"
        )));
    }

    @PostMapping("/places")
    public APIDataResponse<Void> createPlace(@RequestBody PlaceRequest placeRequest) {
        return APIDataResponse.empty();
    }

    @GetMapping("/places/{placeId}")
    public APIDataResponse<PlaceResponse> getPlace(@PathVariable Long placeId) {
        if (placeId.equals(2L)) {
            return APIDataResponse.empty();
        }
        return APIDataResponse.of(PlaceResponse.of(
                PlaceType.COMMON,
                "랄라배드민턴장",
                "서울시 강남구 강남대로 1234",
                "010-1234-5678",
                30,
                "신장개업"
        ));
    }
    @PutMapping("places/{placeId}")
    public APIDataResponse<Void> modifyPlace(
            @PathVariable Long placeId,
            @RequestBody PlaceRequest placeRequest
    ) {
        return APIDataResponse.empty();
    }


    @DeleteMapping("/places/{placeId}")
    public APIDataResponse<Void> removePlace(@PathVariable Long placeId) {
        return APIDataResponse.empty();
    }

}

