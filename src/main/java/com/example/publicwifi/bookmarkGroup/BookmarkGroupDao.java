package com.example.publicwifi.bookmarkGroup;

import com.example.publicwifi.util.ConnectionManager;
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
public class BookmarkGroupDao {
    @Getter
    private static final BookmarkGroupDao instance = new BookmarkGroupDao();

    private Connection getConnection() {
        return ConnectionManager.getConnection();
    }

    public List<BookmarkGroup> findAll() {
        String sql = "select * from bookmarkGroup";
        PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet rs = null;
        List<BookmarkGroup> bookmarkGroups = new ArrayList<>();

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                BookmarkGroup bookmarkGroup = BookmarkGroup.builder()
                        .bgno(rs.getLong("bgno"))
                        .name(rs.getString("name"))
                        .seq(rs.getLong("seq"))
                        .createdDate(rs.getTimestamp("createdDate"))
                        .modifiedDate(rs.getTimestamp("modifiedDate"))
                        .build();
                bookmarkGroups.add(bookmarkGroup);
            }
            return bookmarkGroups;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closeConnection(pstmt, conn, rs);
        }
    }

    public void save(BookmarkGroup bookmarkGroup) {
        String sql = "insert into bookmarkGroup(name, seq, createdDate)" +
                " values(?, ?, datetime('now', 'localtime'))";
        PreparedStatement pstmt = null;
        Connection conn = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, bookmarkGroup.getName());
            pstmt.setLong(2, bookmarkGroup.getSeq());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            ConnectionManager.closeConnection(pstmt, conn, null);
        }
    }

    public BookmarkGroup findById(Long id) {
        String sql = "select * from bookmarkGroup where bgno = ?";
        PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return BookmarkGroup.builder()
                        .bgno(rs.getLong("bgno"))
                        .name(rs.getString("name"))
                        .seq(rs.getLong("seq"))
                        .createdDate(rs.getTimestamp("createdDate"))
                        .modifiedDate(rs.getTimestamp("modifiedDate"))
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closeConnection(pstmt, conn, rs);
        }
        return null;
    }

    public void update(String name, Long seq, Long id) {
        String sql = "update bookmarkGroup set name = ?, seq = ?, " +
                "modifiedDate = datetime('now', 'localtime') where bgno = ?";
        PreparedStatement pstmt = null;
        Connection conn = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, name);
            pstmt.setLong(2, seq);
            pstmt.setLong(3, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            ConnectionManager.closeConnection(pstmt, conn, null);
        }
    }

    public void delete(Long id) {
        String sql = "delete from bookmarkGroup where bgno = ?";
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
