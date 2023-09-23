package com.example.publicwifi.wifi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.UriBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.List;

public class WifiService {
    private final String KEY = "694e4a7654646f31353662534d7568";

    public List<Wifi> searchWifi() throws IOException {
        String url = "http://openapi.seoul.go.kr:8088";
        URI uri = UriBuilder.fromUri(url)
                .path(KEY)
                .path("json").path("TbPublicWifiInfo")
                .path("1").path("20")
                .build();

        HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
        conn.setRequestMethod("GET");
        conn.setDoOutput(true);

        StringBuilder sb = new StringBuilder();
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        WifiResponseDto wifiResponseDto = objectMapper.readValue(sb.toString(), new TypeReference<>() {});
        return Wifi.of(wifiResponseDto);
    }
}
