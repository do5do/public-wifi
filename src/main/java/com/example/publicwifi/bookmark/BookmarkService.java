package com.example.publicwifi.bookmark;

import com.example.publicwifi.bookmarkGroup.BookmarkGroup;
import com.example.publicwifi.bookmarkGroup.BookmarkGroupDao;
import com.example.publicwifi.wifi.Wifi;
import com.example.publicwifi.wifi.WifiDao;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkService {
    @Getter
    private static final BookmarkService instance = new BookmarkService();
    private final BookmarkDao bookmarkDao = BookmarkDao.getInstance();
    private final BookmarkGroupDao bookmarkGroupDao = BookmarkGroupDao.getInstance();
    private final WifiDao wifiDao = WifiDao.getInstance();

    public List<Bookmark> findAll() {
        return bookmarkDao.findAll();
    }

    public void saveBookmark(Long bgno, Long wno) {
        BookmarkGroup bookmarkGroup = bookmarkGroupDao.findById(bgno);
        Wifi wifi = wifiDao.findById(wno);
        bookmarkDao.save(bookmarkGroup, wifi);
    }

    public void delete(Long id) {
        bookmarkDao.delete(id);
    }
}
