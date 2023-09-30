package com.example.publicwifi.wifi;

import com.example.publicwifi.util.Page;
import com.example.publicwifi.wifi.dto.KakaoResponseDto;
import com.example.publicwifi.wifi.dto.WifiResponseDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WifiServiceTest {
    WifiService wifiService = WifiService.getInstance();
    WifiDao wifiDao = WifiDao.getInstance();

    @Test
    void searchWifi() {
        // given
        int startRow = 1;
        int endRow = 5;
        String region = "강남구";
        String roadName = "";

        // when
        WifiResponseDto responseDto = wifiService.searchWifi(startRow, endRow, region, roadName);

        // then
        assertEquals(responseDto.wifiInfo().row().size(), 5);
        assertEquals(responseDto.wifiInfo().row().get(0).X_SWIFI_WRDOFC(), region);
    }

    @Test
    void getWifiList() {
        // given
        int currentPage = 1;
        double lnt = 127.062453121811;
        double lat = 37.4826884445598;

        // when
        Page<Wifi> wifiPage = wifiService.getWifiListWithLoc(currentPage, lnt, lat);

        // then
        assertEquals(wifiPage.getContents().size(), 20);
        assertEquals(wifiPage.getPageable().getCurrentPage(), 1);
    }

    @Test
    void searchAddress() {
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

    @Test
    void saveWifiList() {
        // when
        Long totalCount = wifiService.loadAndSaveAll();

        // then
        Long getTotal = wifiDao.getTotal();
        assertEquals(totalCount, getTotal);
    }

    @Test
    void getNearbyWifi() {
        // given
        double lnt = 126.901955141101;
        double lat = 37.5662141900954;

        // when
        List<Wifi> wifis = wifiService.getNearbyWifi(lnt, lat);

        // then
        assertEquals(wifis.get(0).getBorough(), "마포구");
    }
}