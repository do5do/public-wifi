package com.example.publicwifi.wifi;

import java.util.List;

public class Wifi {
    private double distance;
    private String mgmtNum; // 관리 번호
    private String borough; // 자치구
    private String wifiName;
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
    private double x;
    private double y;
    private String workDate; // 작업일자

    public Wifi(String mgmtNum, String borough, String wifiName, String address, String addrDetail, String installLoc, String installType, String installAgency, String serviceType, String netType, String installYear, String inoutDoor, String wifiConnEnv, double x, double y, String workDate) {
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

    private void setDistance(double x, double y) { // 내 위치 좌표
        double dx = x - this.x;
        double dy = y - this.y;
        this.distance = Math.sqrt(dx * dx + dy * dy);
    }


    public static List<Wifi> of(WifiResponseDto responseDto) {
        return responseDto.tbPublicWifiInfo().row().stream()
                .map(o -> new Wifi(o.X_SWIFI_MGR_NO(), o.X_SWIFI_WRDOFC(), o.X_SWIFI_MAIN_NM(), o.X_SWIFI_ADRES1(), o.X_SWIFI_ADRES2(),
                        o.X_SWIFI_INSTL_FLOOR(), o.X_SWIFI_INSTL_TY(), o.X_SWIFI_INSTL_MBY(), o.X_SWIFI_SVC_SE(), o.X_SWIFI_CMCWR(),
                        o.X_SWIFI_CNSTC_YEAR(), o.X_SWIFI_INOUT_DOOR(), o.X_SWIFI_REMARS3(), o.LAT(), o.LNT(), o.WORK_DTTM()))
                .toList();
    }
}
