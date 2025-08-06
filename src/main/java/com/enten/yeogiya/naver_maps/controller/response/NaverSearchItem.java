package com.enten.yeogiya.naver_maps.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 네이버 Search API의 개별 아이템 구조
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverSearchItem {
    private String title;           // 장소명
    private String link;            // 링크
    private String category;        // 카테고리
    private String description;     // 설명
    private String telephone;       // 전화번호
    private String address;         // 지번 주소
    private String roadAddress;     // 도로명 주소
    private String mapx;            // 경도 (네이버는 소문자)
    private String mapy;            // 위도 (네이버는 소문자)
}