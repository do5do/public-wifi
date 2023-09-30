package com.example.publicwifi.locHistory;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocHistory {
    private Long hno;
    private double lnt; // x
    private double lat; // y
    private Timestamp searchedDate; // 조회 일자

    @Builder
    public LocHistory(Long hno, double lnt, double lat, Timestamp searchedDate) {
        this.hno = hno;
        this.lnt = lnt;
        this.lat = lat;
        this.searchedDate = searchedDate;
    }
}
