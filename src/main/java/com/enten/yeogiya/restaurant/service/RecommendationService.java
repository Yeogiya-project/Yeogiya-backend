package com.enten.yeogiya.restaurant.service;

import com.enten.yeogiya.restaurant.dto.RestaurantDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
@Service
public class RecommendationService {

    @Value("${kakao.rest.api.key}")
    private String kakaoRestApiKey;

    private static final String KAKAO_LOCAL_SEARCH_URL = "https://dapi.kakao.com/v2/local/search/keyword.json";

    public RestaurantDTO recommend(double latitude, double longitude, String keyword) {

        // 1. 카카오 호출을을 위한 URI
        URI uri = UriComponentsBuilder
                .fromUriString(KAKAO_LOCAL_SEARCH_URL)
                .queryParam("query", keyword)
                .queryParam("x", longitude)
                .queryParam("y", latitude)
                .queryParam("radius", 1000) // 검색 반경
                .queryParam("category_group_code", "FD6")
                .queryParam("sort", "distance")
                .encode()
                .build()
                .toUri();

        log.info("Request URI: {}", uri);

        // 2. API 호출을 위한 HTTP 헤더
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoRestApiKey);

        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        // 3. RestTemplate를 사용하여 API 호출
        ParameterizedTypeReference<Map<String, Object>> responseType = new ParameterizedTypeReference<>() {};
        var responseEntity = new RestTemplate().exchange(uri, HttpMethod.GET, httpEntity, responseType);

        // 4. 응답 데이터에서 식당 목록(documents) 추출
        List<Map<String, Object>> searchResultDocuments = (List<Map<String, Object>>) responseEntity.getBody().get("documents");

        if (searchResultDocuments == null || searchResultDocuments.isEmpty()) {
            log.warn("검색 결과가 없습니다. (lat: {}, lng: {}, keyword: {})", latitude, longitude, keyword);
            return null;
        }

        // 5. 검색된 식당 목록 중 하나를 랜덤으로 선택
        Map<String, Object> randomItem = searchResultDocuments.get(new Random().nextInt(searchResultDocuments.size()));

        // 6. 선택된 식당 정보를 RestaurantDTO에 매핑하여 반환
        return new RestaurantDTO(
                (String) randomItem.get("place_name"),
                (String) randomItem.get("category_name"),
                (String) randomItem.get("address_name"),
                (String) randomItem.get("road_address_name"),
                (String) randomItem.get("place_url"),
                Double.parseDouble((String) randomItem.get("y")),
                Double.parseDouble((String) randomItem.get("x"))
        );
    }
}
