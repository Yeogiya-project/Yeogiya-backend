package com.enten.yeogiya.kakao_maps.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchRequest {
    private String query;

    private CategoryGroupCode categoryGroupCode;

    private String x;
    private String y;
    private Integer radius;
    private String rect;
    private Integer page;
    private Integer size;
    private String sort;
}

