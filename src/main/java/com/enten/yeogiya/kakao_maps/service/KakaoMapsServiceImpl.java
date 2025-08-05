package com.enten.yeogiya.kakao_maps.service;

import com.enten.yeogiya.kakao_maps.controller.request.ReverseGeocodingRequest;
import com.enten.yeogiya.kakao_maps.controller.request.SearchRequest;
import com.enten.yeogiya.kakao_maps.controller.response.ReverseGeocodingResponse;
import com.enten.yeogiya.kakao_maps.controller.response.SearchResponse;
import com.enten.yeogiya.kakao_maps.controller.request.SearchType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class KakaoMapsServiceImpl implements KakaoMapsService {
    private final WebClient kakaoWebClient;

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    public KakaoMapsServiceImpl(@Qualifier("kakaoWebClient") WebClient kakaoWebClient) {
        this.kakaoWebClient = kakaoWebClient;
    }

    @Override
    public Mono<SearchResponse> searchPlaces(SearchRequest query, SearchType searchType) {
        String path = switch (searchType) {
            case KEYWORD -> "/v2/local/search/keyword.json";
            case CATEGORY -> "/v2/local/search/category.json";
        };
        return kakaoWebClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path(path);

                    if (searchType == SearchType.KEYWORD) {
                        if (query.getQuery() == null || query.getQuery().trim().isEmpty()) {
                            throw new IllegalArgumentException("키워드 검색 시 query는 필수입니다.");
                        }
                        uriBuilder.queryParam("query", query.getQuery());
                    }

                    if (searchType == SearchType.CATEGORY) {
                        if (query.getCategoryGroupCode() == null) {
                            throw new IllegalArgumentException("카테고리 검색 시 category_group_code는 필수입니다.");
                        }
                        if (query.getX() == null || query.getY() == null) {
                            throw new IllegalArgumentException("카테고리 검색 시 x, y 좌표는 필수입니다.");
                        }
                        if (query.getRadius() == null) {
                            throw new IllegalArgumentException("카테고리 검색 시 radius는 필수입니다.");
                        }
                        uriBuilder.queryParam("category_group_code", query.getCategoryGroupCode().name());
                    }

                    if (query.getX() != null && query.getY() != null) {
                        uriBuilder.queryParam("x", query.getX())
                                .queryParam("y", query.getY());
                    }
                    if (query.getRadius() != null) {
                        uriBuilder.queryParam("radius", query.getRadius());
                    }
                    if (query.getRect() != null) {
                        uriBuilder.queryParam("rect", query.getRect());
                    }
                    if (query.getPage() != null) {
                        uriBuilder.queryParam("page", query.getPage());
                    }
                    if (query.getSize() != null) {
                        uriBuilder.queryParam("size", query.getSize());
                    }
                    if (query.getSort() != null) {
                        uriBuilder.queryParam("sort", query.getSort());
                    }

                    return uriBuilder.build();
                })
                .header("Authorization", "KakaoAK " + kakaoApiKey)
                .retrieve()
                .bodyToMono(SearchResponse.class);
    }

    @Override
    public Mono<ReverseGeocodingResponse> reverseGeocoding(ReverseGeocodingRequest query) {
        return kakaoWebClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path("/v2/local/geo/coord2address.json");

                    if (query.getX() == null || query.getY() == null) {
                        throw new IllegalArgumentException("x, y 좌표는 필수입니다.");
                    }
                    uriBuilder.queryParam("x", query.getX())
                            .queryParam("y", query.getY());

                    if (query.getInputCoord() != null) {
                        uriBuilder.queryParam("input_coord", query.getInputCoord());
                    }

                    return uriBuilder.build();
                })
                .header("Authorization", "KakaoAK " + kakaoApiKey)
                .retrieve()
                .bodyToMono(ReverseGeocodingResponse.class);
    }
}
