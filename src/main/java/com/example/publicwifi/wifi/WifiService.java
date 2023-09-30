package com.example.publicwifi.wifi;

import com.example.publicwifi.util.Page;
import com.example.publicwifi.util.Pageable;
import com.example.publicwifi.wifi.dto.AddressDto;
import com.example.publicwifi.wifi.dto.KakaoResponseDto;
import com.example.publicwifi.wifi.dto.WifiResponseDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.UriBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WifiService {
    @Getter
    private static final WifiService instance = new WifiService();

    private static final String KEY = "694e4a7654646f31353662534d7568";
    private static final String KAKAO_KEY = "KakaoAK 50772213c82e83f32ae2305ce747b05d";
    private static final int ROW_PER_PAGE = 20;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final WifiDao wifiDao = WifiDao.getInstance();

    public List<Wifi> getNearbyWifi(double lnt, double lat) { // 근처 와이파이 정보 20개 가져오기
        AddressDto address = getAddress(lnt, lat);
        List<Wifi> wifiList = wifiDao.findAllByBorough(address.region(), address.roadName())
                .stream().map(o -> o.setDistance(lnt, lat))
                .sorted().toList();
        if (wifiList.size() > 20) {
            wifiList = wifiList.subList(0, 20);
        }
        return wifiList;
    }

    public Page<Wifi> getWifiListWithLoc(int currentPage, double lnt, double lat) { // 위치 기반 와이파이 api 조회 페이징
        int startRow = (currentPage - 1) * ROW_PER_PAGE + 1;
        int endRow = startRow + ROW_PER_PAGE - 1;

        AddressDto address = getAddress(lnt, lat);
        WifiResponseDto wifiResponseDto = searchWifi(startRow, endRow, address.region(), address.roadName());
        return new Page<>(Wifi.ofMyLoc(wifiResponseDto, lnt, lat),
                Pageable.of(wifiResponseDto.wifiInfo().totalCount(), currentPage, ROW_PER_PAGE));
    }

    public AddressDto getAddress(double lnt, double lat) {
        KakaoResponseDto kakaoResponseDto = searchAddress(lnt, lat);

        String region = "";
        String roadName = "";
        if (!kakaoResponseDto.documents().isEmpty()) {
            region = kakaoResponseDto.documents().get(0).address().region_2depth_name();
            if (kakaoResponseDto.documents().get(0).roadAddress() != null) {
                roadName = kakaoResponseDto.documents().get(0).roadAddress().road_name();
            }
        }

        return new AddressDto(region, roadName);
    }

    public KakaoResponseDto searchAddress(double lnt, double lat) {
        String url = "https://dapi.kakao.com/v2/local/geo/coord2address";
        URI uri = UriBuilder.fromUri(url)
                .queryParam("x", lnt)
                .queryParam("y", lat)
                .build();
        StringBuilder sb = httpConnection(uri, true);

        try {
            return objectMapper.readValue(sb.toString(), new TypeReference<>() {});
        } catch (IOException e) {
            throw new BadRequestException("올바르지 않은 요청입니다.");
        }
    }

    public WifiResponseDto searchWifi(int startRow, int endRow, String region, String roadName) {
        String url = "http://openapi.seoul.go.kr:8088";
        URI uri = UriBuilder.fromUri(url)
                .path(KEY)
                .path("json").path("TbPublicWifiInfo")
                .path(String.valueOf(startRow)).path(String.valueOf(endRow))
                .path(region)
                .path(roadName)
                .build();
        StringBuilder sb = httpConnection(uri, false);

        try {
            return objectMapper.readValue(sb.toString(), new TypeReference<>() {});
        } catch (UnrecognizedPropertyException e) {
            throw new IllegalArgumentException("해당하는 데이터가 없습니다.");
        } catch (IOException e) {
            throw new BadRequestException("올바르지 않은 요청입니다.");
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
            throw new RuntimeException("연결 요청에 실패하였습니다.");
        }
    }

    public Long loadAndSaveAll() { // 모든 와이파이 데이터 저장
        int rowPerPage = 1000;
        int currentPage = 1;
        int startRow = (currentPage - 1) * rowPerPage + 1;
        int endRow = startRow + rowPerPage - 1;

        // clear
        wifiDao.deleteAll();

        WifiResponseDto wifiResponseDto = searchWifi(startRow, endRow, "", "");
        Long totalCount = wifiResponseDto.wifiInfo().totalCount();
        long totalPages = (totalCount + rowPerPage - 1) / rowPerPage;
        wifiDao.saveAll(Wifi.of(wifiResponseDto));

        for (currentPage = 2; currentPage <= totalPages; currentPage++) {
            startRow = (currentPage - 1) * rowPerPage + 1;
            endRow = startRow + rowPerPage - 1;

            wifiResponseDto = searchWifi(startRow, endRow, "", "");
            wifiDao.saveAll(Wifi.of(wifiResponseDto));
        }
        return totalCount;
    }

    public Wifi findMgmtNum(String mgmtNum) {
        return wifiDao.findByMgmtNum(mgmtNum);
    }

    public Wifi findById(Long id) {
        return wifiDao.findById(id);
    }
}
