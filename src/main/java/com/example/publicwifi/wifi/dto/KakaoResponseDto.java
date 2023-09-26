package com.example.publicwifi.wifi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record KakaoResponseDto(Meta meta, List<Documents> documents) {
    public record Meta(@JsonProperty("total_count") String totalCount) {}

    public record Documents(@JsonProperty("road_address") RoadAddress roadAddress, Address address) {
        public record RoadAddress(String address_name,
                                  String region_1depth_name,
                                  String region_2depth_name,
                                  String region_3depth_name,
                                  String road_name,
                                  String underground_yn,
                                  String main_building_no,
                                  String sub_building_no,
                                  String building_name,
                                  String zone_no
        ) {}

        public record Address(String address_name,
                              String region_1depth_name,
                              String region_2depth_name,
                              String region_3depth_name,
                              String mountain_yn,
                              String main_address_no,
                              String sub_address_no,
                              String zip_code
        ) {}
    }
}
