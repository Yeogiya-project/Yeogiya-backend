package com.enten.yeogiya.kakao_maps.controller.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SearchResponse {
    private SearchMeta meta;
    private List<SearchDocument> documents;
}
