package com.enten.yeogiya.kakao_maps.controller;

import com.enten.yeogiya.kakao_maps.controller.request.MeetingPlaceRequest;
import com.enten.yeogiya.kakao_maps.controller.request.ReverseGeocodingRequest;
import com.enten.yeogiya.kakao_maps.controller.request.SearchRequest;
import com.enten.yeogiya.kakao_maps.controller.request.SearchType;
import com.enten.yeogiya.kakao_maps.controller.response.MeetingPlaceResponse;
import com.enten.yeogiya.kakao_maps.controller.response.ReverseGeocodingResponse;
import com.enten.yeogiya.kakao_maps.controller.response.SearchResponse;
import com.enten.yeogiya.kakao_maps.service.KakaoMapsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/kakao-maps")
public class KaKaoMapsController {

    private final KakaoMapsService kakaoMapsService;

    @GetMapping("/search")
    public Mono<ResponseEntity<SearchResponse>> searchPlaces(
            @ModelAttribute SearchRequest query,
            @RequestParam SearchType searchType
    ) {
        return kakaoMapsService.searchPlaces(query, searchType)
                .map(ResponseEntity::ok)
                .onErrorResume(error -> {
                    System.err.println("검색 에러: " + error.getMessage());
                    error.printStackTrace();
                    return Mono.just(ResponseEntity.internalServerError().build());
                });
    }

    @GetMapping("/reverse-geocoding")
    public Mono<ResponseEntity<ReverseGeocodingResponse>> reverseGeocoding(
            @ModelAttribute ReverseGeocodingRequest query) {

        return kakaoMapsService.reverseGeocoding(query)
                .map(ResponseEntity::ok)
                .onErrorResume(error -> {
                    System.err.println("지오코딩 에러: " + error.getMessage());
                    error.printStackTrace();
                    return Mono.just(ResponseEntity.internalServerError().build());
                });
    }

    @PostMapping("/meeting-place")
    public Mono<ResponseEntity<MeetingPlaceResponse>> findMeetingPlace(
            @RequestBody MeetingPlaceRequest request) {

        return kakaoMapsService.findMeetingPlace(request)
                .map(ResponseEntity::ok)
                .onErrorResume(error -> {
                    System.err.println("약속 장소 검색 에러: " + error.getMessage());
                    error.printStackTrace();
                    return Mono.just(ResponseEntity.badRequest().build());
                });
    }
}