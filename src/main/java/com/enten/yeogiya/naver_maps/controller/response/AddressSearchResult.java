package com.enten.yeogiya.naver_maps.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 개별 검색 결과 항목을 담는 클래스
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressSearchResult {
    private String title;           // 장소명 (예: "스타벅스 강남점")
    private String category;        // 카테고리 (예: "카페", "주소")
    private String address;         // 지번 주소
    private String roadAddress;     // 도로명 주소
    private String mapX;            // 경도 (longitude)
    private String mapY;            // 위도 (latitude)
    private String distance;        // 거리 (사용하지 않음)
}