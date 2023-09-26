package com.example.publicwifi.common;

import lombok.Getter;

@Getter
public class Pageable {
    private Long totalElements; // 전체 개수
    private final int pagePerBlock = 10; // 블럭(페이지 네비) 당 페이지 수
    private int rowPerPage; // 페이지 당 컨텐츠 개수
    private int currentPage;
    private int startPage;
    private int endPage;
    private int totalPages; // 전체 페이지 수

    public Pageable() {}

    public Pageable(Long totalElements, int currentPage, int rowPerPage) {
        this.totalElements = totalElements;
        this.currentPage = currentPage;
        this.rowPerPage = rowPerPage;

        totalPages = (int) Math.ceil((double) this.totalElements / this.rowPerPage);
        startPage = this.currentPage - (this.currentPage - 1) % pagePerBlock;
        endPage = startPage + pagePerBlock - 1;

        endPage = Math.min(endPage, totalPages);
    }

    public static Pageable of(Long totalElements, int currentPage, int rowPerPage) {
        return new Pageable(totalElements, currentPage, rowPerPage);
    }
}
