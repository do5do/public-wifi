package com.example.publicwifi.wifi;

import com.example.publicwifi.wifi.dto.WifiResponseDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wifi implements Comparable<Wifi> {
    private Long wno;
    private Double distance;
    private String mgmtNum; // 관리 번호
    private String borough; // 자치구
    private String wifiName; // 와이파이명
    private String address; // 도로명 주소
    private String addrDetail; // 상세 주소
    private String installLoc; // 설치 위치
    private String installType; // 설치 유형
    private String installAgency; // 설치 기관
    private String serviceType; // 서비스 구분
    private String netType; // 망 종류
    private String installYear; // 설치 년도
    private String inoutDoor; // 실외 구분
    private String wifiConnEnv; // 와이파이 접속 환경
    private double x; // lnt
    private double y; // lat
    private String workDate; // 작업일자

    @Builder
    public Wifi(Long wno, String mgmtNum, String borough, String wifiName, String address, String addrDetail, String installLoc, String installType, String installAgency, String serviceType, String netType, String installYear, String inoutDoor, String wifiConnEnv, double x, double y, String workDate) {
        this.wno = wno;
        this.mgmtNum = mgmtNum;
        this.borough = borough;
        this.wifiName = wifiName;
        this.address = address;
        this.addrDetail = addrDetail;
        this.installLoc = installLoc;
        this.installType = installType;
        this.installAgency = installAgency;
        this.serviceType = serviceType;
        this.netType = netType;
        this.installYear = installYear;
        this.inoutDoor = inoutDoor;
        this.wifiConnEnv = wifiConnEnv;
        this.x = x;
        this.y = y;
        this.workDate = workDate;
    }

    public void setWno(Long wno) {
        this.wno = wno;
    }

    public Wifi setDistance(double x, double y) { // 내 위치 좌표
        double dx = x - this.x;
        double dy = y - this.y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        this.distance = BigDecimal.valueOf(distance)
                .setScale(4, RoundingMode.HALF_EVEN).doubleValue();
        return this;
    }

    public static Wifi toEntity(WifiResponseDto.WifiInfo.Row row) {
        return Wifi.builder()
                .mgmtNum(row.X_SWIFI_MGR_NO())
                .borough(row.X_SWIFI_WRDOFC())
                .wifiName(row.X_SWIFI_MAIN_NM())
                .address(row.X_SWIFI_ADRES1())
                .addrDetail(row.X_SWIFI_ADRES2())
                .installLoc(row.X_SWIFI_INSTL_FLOOR())
                .installType(row.X_SWIFI_INSTL_TY())
                .installAgency(row.X_SWIFI_INSTL_MBY())
                .serviceType(row.X_SWIFI_SVC_SE())
                .netType(row.X_SWIFI_CMCWR())
                .installYear(row.X_SWIFI_CNSTC_YEAR())
                .inoutDoor(row.X_SWIFI_INOUT_DOOR())
                .wifiConnEnv(row.X_SWIFI_REMARS3())
                .x(row.LAT())
                .y(row.LNT())
                .workDate(row.WORK_DTTM())
                .build();
    }

    public static List<Wifi> ofMyLoc(WifiResponseDto responseDto, double myLnt, double myLat) {
        return responseDto.wifiInfo().row().stream()
                .map(Wifi::toEntity)
                .map(o -> o.setDistance(myLnt, myLat))
                .sorted().toList(); // 거리순 정렬
    }

    public static List<Wifi> of(WifiResponseDto responseDto) {
        return responseDto.wifiInfo().row().stream()
                .map(Wifi::toEntity)
                .toList();
    }

    @Override
    public int compareTo(Wifi o) {
        return Double.compare(this.distance, (o.distance));
    }
}
