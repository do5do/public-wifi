package com.example.publicwifi.bookmark;

import com.example.publicwifi.bookmarkGroup.BookmarkGroup;
import com.example.publicwifi.util.ConnectionManager;
import com.example.publicwifi.wifi.Wifi;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkDao {
    @Getter
    private static final BookmarkDao instance = new BookmarkDao();

    private Connection getConnection() {
        return ConnectionManager.getConnection();
    }

    public List<Bookmark> findAll() {
        String sql = "select * from bookmark";
        PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet rs = null;
        List<Bookmark> bookmarks = new ArrayList<>();

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Bookmark bookmark = Bookmark.builder()
                        .bno(rs.getLong("bno"))
                        .bgno(rs.getLong("bgno"))
                        .wno(rs.getLong("wno"))
                        .bookmarkName(rs.getString("bookmarkName"))
                        .wifiName(rs.getString("wifiName"))
                        .createdDate(rs.getTimestamp("createdDate"))
                        .build();
                bookmarks.add(bookmark);
            }
            return bookmarks;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closeConnection(pstmt, conn, rs);
        }
    }

    public void save(BookmarkGroup bookmarkGroup, Wifi wifi) {
        String sql = "insert into bookmark(bgno, wno, bookmarkName, wifiName, createdDate)" +
                " values(?, ?, ?, ?, datetime('now', 'localtime'))";
        PreparedStatement pstmt = null;
        Connection conn = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setLong(1, bookmarkGroup.getBgno());
            pstmt.setLong(2, wifi.getWno());
            pstmt.setString(3, bookmarkGroup.getName());
            pstmt.setString(4, wifi.getWifiName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            ConnectionManager.closeConnection(pstmt, conn, null);
        }
    }

    public void delete(Long id) {
        String sql = "delete from bookmark where bno = ?";
        PreparedStatement pstmt = null;
        Connection conn = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            ConnectionManager.closeConnection(pstmt, conn, null);
        }
    }
}
