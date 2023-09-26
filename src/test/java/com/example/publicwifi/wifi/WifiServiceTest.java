package com.example.publicwifi.wifi;

import com.example.publicwifi.common.Page;
import com.example.publicwifi.wifi.dto.KakaoResponseDto;
import com.example.publicwifi.wifi.dto.WifiResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WifiServiceTest {
    WifiService wifiService = new WifiService();

    @Test
    void searchWifi() throws IOException {
        // given
        int startRow = 1;
        int endRow = 5;
//        String region = "강남구";
        String region = "부산진구";
        String roadName = "";

        // when
        WifiResponseDto responseDto = wifiService.searchWifi(startRow, endRow, region, roadName);

        // then
        assertEquals(responseDto.wifiInfo().row().size(), 5);
    }

    @Test
    void getWifiList() throws IOException {
        // given
        int currentPage = 1;
        double lnt = 127.062453121811;
        double lat = 37.4826884445598;

        // when
        Page<Wifi> wifiPage = wifiService.getWifiList(currentPage, lnt, lat);

        // then
        assertEquals(wifiPage.getContents().size(), 20);
        assertEquals(wifiPage.getPageable().getCurrentPage(), 1);
    }

    @Test
    void searchAddress() throws IOException {
        // given
        double lnt = 127.062453121811;
        double lat = 37.4826884445598;

        // when
        KakaoResponseDto kakaoResponseDto = wifiService.searchAddress(lnt, lat);
        String region = kakaoResponseDto.documents().get(0).address().region_2depth_name();
        String roadName = "";
        if (kakaoResponseDto.documents().get(0).roadAddress() != null) {
            roadName = kakaoResponseDto.documents().get(0).roadAddress().road_name();
        }

        // then
        assertEquals(region, "강남구");
        assertEquals(roadName, "");
    }
}