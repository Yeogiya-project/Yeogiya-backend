package com.enten.yeogiya.restaurant.controller;


import com.enten.yeogiya.restaurant.dto.RestaurantDTO;
import com.enten.yeogiya.restaurant.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RecommendationService recommendationService;

    @GetMapping("/recommend")
    public ResponseEntity<RestaurantDTO> recommendRestaurant(
            @RequestParam("lat") double lat,
            @RequestParam("lng") double lng,
            @RequestParam("keyword") String keyword,
            @RequestParam("category") String categoryCode) {

        // 서비스 클래스를 호출하여 비즈니스 로직 수행
        RestaurantDTO result = recommendationService.recommend(lat, lng, keyword, categoryCode);

        if (result == null) {
            // 결과 없으면 404 Not Found 반환
            return ResponseEntity.notFound().build();
        }

        // 결과 있으면 200 응다바과 함께 식당 정보 반환
        return ResponseEntity.ok(result);
    }
}
