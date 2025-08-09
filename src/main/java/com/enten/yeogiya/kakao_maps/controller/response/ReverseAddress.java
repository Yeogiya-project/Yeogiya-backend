package com.enten.yeogiya.kakao_maps.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReverseAddress {
    @JsonProperty("address_name")
    private String addressName;

    @JsonProperty("region_1depth_name")
    private String region1depthName;

    @JsonProperty("region_2depth_name")
    private String region2depthName;

    @JsonProperty("region_3depth_name")
    private String region3depthName;

    @JsonProperty("mountain_yn")
    private String mountainYn;

    @JsonProperty("main_address_no")
    private String mainAddressNo;

    @JsonProperty("sub_address_no")
    private String subAddressNo;
}
