package com.enten.yeogiya.kakao_maps.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReverseMeta {
    @JsonProperty("total_count")
    private Integer totalCount;
}