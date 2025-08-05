package com.enten.yeogiya.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO {

    private String title;
    private String category;
    private String address;
    private String roadAddress;
    private String placeUrl;
    private double latitude; // 위도(y)
    private double longitude; // 경도(x)

}
