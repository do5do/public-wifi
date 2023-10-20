package com.example.publicwifi.util;

import java.sql.*;

public class ConnectionManager {
    public static Connection getConnection() {
        try {
            String driver = "org.sqlite.JDBC";
//            String jdbcUrl = "jdbc:sqlite:publicWifi.db";
            String jdbcUrl = "jdbc:sqlite:/Users/doheekim/study/publicWifi/publicWifi.db";
            Class.forName(driver);
            return DriverManager.getConnection(jdbcUrl);
        } catch (ClassNotFoundException | SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public static void closeConnection(PreparedStatement pstmt, Connection conn, ResultSet rs) {
        try {
            if (pstmt != null) {
                pstmt.close();
            }

            if (rs != null) {
                rs.close();
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
