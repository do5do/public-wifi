package com.example.publicwifi.util;

import lombok.Getter;

import java.util.List;

@Getter
public class Page<T> {
    private List<T> contents;
    private Pageable pageable;

    public Page() {}

    public Page(List<T> contents, Pageable pageable) {
        this.contents = contents;
        this.pageable = pageable;
    }
}
