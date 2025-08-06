package com.enten.yeogiya.naver_maps.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

// 주소/장소 검색 결과를 담는 응답 클래스
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressSearchResponse {
    private int total;                              // 총 검색 결과 개수
    private List<AddressSearchResult> items;        // 검색 결과 목록
}