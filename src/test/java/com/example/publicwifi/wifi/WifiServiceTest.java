package com.example.publicwifi.wifi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WifiServiceTest {
    WifiService wifiService = new WifiService();

    @Test
    void searchWifi() throws IOException {
        // when
        List<Wifi> wifiList = wifiService.searchWifi();

        // then
        Assertions.assertEquals(wifiList.size(), 20);
    }
}