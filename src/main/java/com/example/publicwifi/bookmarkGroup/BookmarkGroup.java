package com.example.publicwifi.bookmarkGroup;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkGroup {
    private Long bgno;
    private String name;
    private Long seq;
    private Timestamp createdDate;
    private Timestamp modifiedDate;

    @Builder
    public BookmarkGroup(Long bgno, String name, Long seq, Timestamp createdDate, Timestamp modifiedDate) {
        this.bgno = bgno;
        this.name = name;
        this.seq = seq;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
