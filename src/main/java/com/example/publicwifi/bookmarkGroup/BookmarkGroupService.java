package com.example.publicwifi.bookmarkGroup;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkGroupService {
    @Getter
    private static final BookmarkGroupService instance = new BookmarkGroupService();

    private final BookmarkGroupDao bookmarkGroupDao = BookmarkGroupDao.getInstance();

    public List<BookmarkGroup> findAll() {
        return bookmarkGroupDao.findAll();
    }

    public void saveBookmarkGroup(String name, Long seq) {
        BookmarkGroup bookmarkGroup = BookmarkGroup.builder()
                .name(name)
                .seq(seq)
                .build();
        bookmarkGroupDao.save(bookmarkGroup);
    }

    public BookmarkGroup findById(Long id) {
        return bookmarkGroupDao.findById(id);
    }

    public void updateBookmarkGroup(String name, Long seq, Long id) {
        bookmarkGroupDao.update(name, seq, id);
    }

    public void delete(Long id) {
        bookmarkGroupDao.delete(id);
    }
}
