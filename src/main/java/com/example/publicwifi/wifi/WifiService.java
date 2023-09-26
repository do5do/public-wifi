package com.example.publicwifi.wifi;

import com.example.publicwifi.common.Page;
import com.example.publicwifi.common.Pageable;
import com.example.publicwifi.wifi.dto.KakaoResponseDto;
import com.example.publicwifi.wifi.dto.WifiResponseDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.UriBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;

public class WifiService {
    private static final String KEY = "694e4a7654646f31353662534d7568";
    private static final String KAKAO_KEY = "KakaoAK 50772213c82e83f32ae2305ce747b05d";
    private static final int ROW_PER_PAGE = 20;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Page<Wifi> getWifiList(Integer currentPage, double lnt, double lat) throws IOException {
        int startRow = (currentPage - 1) * ROW_PER_PAGE + 1;
        int endRow = startRow + ROW_PER_PAGE - 1;

        KakaoResponseDto kakaoResponseDto = searchAddress(lnt, lat);
        String region = kakaoResponseDto.documents().get(0).address().region_2depth_name();
        String roadName = "";
        if (kakaoResponseDto.documents().get(0).roadAddress() != null) {
            roadName = kakaoResponseDto.documents().get(0).roadAddress().road_name();
        }

        WifiResponseDto wifiResponseDto = searchWifi(startRow, endRow, region, roadName);
        return new Page<>(Wifi.of(wifiResponseDto, lnt, lat), Pageable.of(wifiResponseDto.wifiInfo().totalCount(), currentPage, ROW_PER_PAGE));
    }

    public KakaoResponseDto searchAddress(double lnt, double lat) throws IOException {
        String url = "https://dapi.kakao.com/v2/local/geo/coord2address";
        URI uri = UriBuilder.fromUri(url)
                .queryParam("x", lnt)
                .queryParam("y", lat)
                .build();

        System.out.println("uri = " + uri);
        StringBuilder sb = httpConnection(uri, true);
        return objectMapper.readValue(sb.toString(), new TypeReference<>() {});
    }

    public WifiResponseDto searchWifi(int startRow, int endRow, String region, String roadName) throws IOException {
        String url = "http://openapi.seoul.go.kr:8088";
        URI uri = UriBuilder.fromUri(url)
                .path(KEY)
                .path("json").path("TbPublicWifiInfo")
                .path(String.valueOf(startRow)).path(String.valueOf(endRow))
                .path(region)
                .path(roadName)
                .build();

        System.out.println("uri = " + uri);
        StringBuilder sb = httpConnection(uri, false);

        try {
            return objectMapper.readValue(sb.toString(), new TypeReference<>() {});
        } catch (UnrecognizedPropertyException e) {
            throw new IllegalArgumentException("해당하는 데이터가 없습니다.");
        }
    }

    private StringBuilder httpConnection(URI uri, boolean setHeader) {
        try {
            HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
            if (setHeader) {
                conn.setRequestProperty(HttpHeaders.AUTHORIZATION, KAKAO_KEY);
            }
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
            return sb;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
