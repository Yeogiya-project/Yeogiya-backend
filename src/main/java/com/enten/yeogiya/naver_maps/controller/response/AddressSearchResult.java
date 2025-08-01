package com.enten.yeogiya.naver_maps.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 개별 검색 결과 항목을 담는 클래스
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressSearchResult {
    private String title;           // 장소명 (예: "스타벅스 강남점")
    private String category;        // 카테고리 (예: "카페", "주소")
    private String address;         // 지번 주소
    private String roadAddress;     // 도로명 주소
    private String mapx;            // 경도 (longitude) - 네이버 API는 소문자 x
    private String mapy;            // 위도 (latitude) - 네이버 API는 소문자 y
    private String distance;        // 거리 (사용하지 않음)
    
    // 기존 코드와의 호환성을 위한 getter 메서드
    public String getMapX() {
        return this.mapx;
    }
    
    public String getMapY() {
        return this.mapy;
    }
}