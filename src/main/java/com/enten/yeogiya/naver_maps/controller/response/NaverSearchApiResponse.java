package com.enten.yeogiya.naver_maps.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

// 네이버 Search API의 실제 응답 구조
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverSearchApiResponse {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<NaverSearchItem> items;
}