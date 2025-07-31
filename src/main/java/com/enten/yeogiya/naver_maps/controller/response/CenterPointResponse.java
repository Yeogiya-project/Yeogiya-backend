package com.enten.yeogiya.naver_maps.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 중간지점 계산 결과를 담는 응답 클래스
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CenterPointResponse {
    private double lat;     // 중간지점 위도
    private double lng;     // 중간지점 경도
    private int count;      // 계산에 사용된 위치 개수
}