package com.example.publicwifi.wifi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record WifiResponseDto(@JsonProperty("TbPublicWifiInfo") WifiInfo wifiInfo) {
    public record WifiInfo(
            @JsonProperty("list_total_count") Long totalCount,
            @JsonProperty("RESULT") Result result,
            List<Row> row
    ) {
        public record Result(@JsonProperty("CODE") String code, @JsonProperty("MESSAGE") String message) {}

        public record Row(
                String X_SWIFI_MGR_NO,
                String X_SWIFI_WRDOFC,
                String X_SWIFI_MAIN_NM,
                String X_SWIFI_ADRES1,
                String X_SWIFI_ADRES2,
                String X_SWIFI_INSTL_FLOOR,
                String X_SWIFI_INSTL_TY,
                String X_SWIFI_INSTL_MBY,
                String X_SWIFI_SVC_SE,
                String X_SWIFI_CMCWR,
                String X_SWIFI_CNSTC_YEAR,
                String X_SWIFI_INOUT_DOOR,
                String X_SWIFI_REMARS3,
                Double LAT, // 값이 반대로 넘어와서 lat가 x
                Double LNT, // y
                String WORK_DTTM
        ) {}
    }
}