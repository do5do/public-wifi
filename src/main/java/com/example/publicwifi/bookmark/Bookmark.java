package com.example.publicwifi.bookmark;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark {
    private Long bno;
    private Long bgno; // bookmark group
    private Long wno; // wifi
    private String bookmarkName;
    private String wifiName;
    private Timestamp createdDate;

    @Builder
    public Bookmark(Long bno, Long bgno, Long wno, String bookmarkName, String wifiName, Timestamp createdDate) {
        this.bno = bno;
        this.bgno = bgno;
        this.wno = wno;
        this.bookmarkName = bookmarkName;
        this.wifiName = wifiName;
        this.createdDate = createdDate;
    }
}
