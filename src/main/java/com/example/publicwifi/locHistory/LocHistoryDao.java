package com.example.publicwifi.locHistory;

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
public class LocHistoryDao {
    @Getter
    private static final LocHistoryDao instance = new LocHistoryDao();

    private Connection getConnection() {
        return ConnectionManager.getConnection();
    }

    public List<LocHistory> findAll() {
        String sql = "select * from locHistory";
        PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet rs = null;
        List<LocHistory> locHistories = new ArrayList<>();

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                LocHistory locHistory = LocHistory.builder()
                        .hno(rs.getLong("hno"))
                        .lnt(rs.getDouble("lnt"))
                        .lat(rs.getDouble("lat"))
                        .searchedDate(rs.getTimestamp("searchedDate"))
                        .build();
                locHistories.add(locHistory);
            }
            return locHistories;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closeConnection(pstmt, conn, rs);
        }
    }

    public void save(double lnt, double lat) {
        String sql = "insert into locHistory(lnt, lat, searchedDate)" +
                " values(?, ?, datetime('now', 'localtime'))";
        PreparedStatement pstmt = null;
        Connection conn = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setDouble(1, lnt);
            pstmt.setDouble(2, lat);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            ConnectionManager.closeConnection(pstmt, conn, null);
        }
    }

    public void delete(Long id) {
        String sql = "delete from locHistory where hno = ?";
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
