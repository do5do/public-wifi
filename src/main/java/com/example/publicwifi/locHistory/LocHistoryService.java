package com.example.publicwifi.locHistory;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocHistoryService {
    @Getter
    private static final LocHistoryService instance = new LocHistoryService();

    private final LocHistoryDao locHistoryDao = LocHistoryDao.getInstance();

    public List<LocHistory> findAll() {
        return locHistoryDao.findAll();
    }

    public void save(double lnt, double lat) {
        locHistoryDao.save(lnt, lat);
    }

    public void delete(Long id) {
        locHistoryDao.delete(id);
    }
}
