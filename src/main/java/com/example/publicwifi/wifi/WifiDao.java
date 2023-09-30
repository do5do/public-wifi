package com.example.publicwifi.wifi;


import com.example.publicwifi.util.ConnectionManager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WifiDao {
    @Getter
    private static final WifiDao instance = new WifiDao();

    private Connection getConnection() {
        return ConnectionManager.getConnection();
    }

    public void deleteAll() {
        String sql = "delete from wifi";
        PreparedStatement pstmt = null;
        Connection conn = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            ConnectionManager.closeConnection(pstmt, conn, null);
        }
    }

    public void saveAll(List<Wifi> wifiList) {
        String sql = "insert into wifi(distance, mgmtNum, borough, wifiName, address, addrDetail, installLoc, installType, installAgency, serviceType, netType, installYear, inoutDoor, wifiConnEnv, x, y, workDate)" +
                " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = null;
        Connection conn = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            for (Wifi wifi : wifiList) {
                pstmt.setDouble(1, 0.0);
                pstmt.setString(2, wifi.getMgmtNum());
                pstmt.setString(3, wifi.getBorough());
                pstmt.setString(4, wifi.getWifiName());
                pstmt.setString(5, wifi.getAddress());
                pstmt.setString(6, wifi.getAddrDetail());
                pstmt.setString(7, wifi.getInstallLoc());
                pstmt.setString(8, wifi.getInstallType());
                pstmt.setString(9, wifi.getInstallAgency());
                pstmt.setString(10, wifi.getServiceType());
                pstmt.setString(11, wifi.getNetType());
                pstmt.setString(12, wifi.getInstallYear());
                pstmt.setString(13, wifi.getInoutDoor());
                pstmt.setString(14, wifi.getWifiConnEnv());
                pstmt.setDouble(15, wifi.getX());
                pstmt.setDouble(16, wifi.getY());
                pstmt.setString(17, wifi.getWorkDate());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            pstmt.clearBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closeConnection(pstmt, conn, null);
        }
    }

    public Long getTotal() {
        String sql = "select count(*) from wifi";
        PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closeConnection(pstmt, conn, rs);
        }
        return -1L;
    }

    public List<Wifi> findAllByBorough(String region, String address) {
        String sql = "select * from wifi where borough = ? and address like ?";
        PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet rs = null;
        List<Wifi> wifiList = new ArrayList<>();

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, region);
            pstmt.setString(2, "%" + address + "%");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Wifi wifi = Wifi.builder()
                        .wno(rs.getLong("wno"))
                        .mgmtNum(rs.getString("mgmtNum"))
                        .borough(rs.getString("borough"))
                        .wifiName(rs.getString("wifiName"))
                        .address(rs.getString("address"))
                        .addrDetail(rs.getString("addrDetail"))
                        .installLoc(rs.getString("installLoc"))
                        .installType(rs.getString("installType"))
                        .installAgency(rs.getString("installAgency"))
                        .serviceType(rs.getString("serviceType"))
                        .netType(rs.getString("netType"))
                        .installYear(rs.getString("installYear"))
                        .inoutDoor(rs.getString("inoutDoor"))
                        .wifiConnEnv(rs.getString("wifiConnEnv"))
                        .x(rs.getDouble("x"))
                        .y(rs.getDouble("y"))
                        .workDate(rs.getString("workDate"))
                        .build();
                wifiList.add(wifi);
            }
            return wifiList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closeConnection(pstmt, conn, rs);
        }
    }

    public Wifi findByMgmtNum(String mgmtNum) {
        String sql = "select * from wifi where mgmtNum = ?";
        PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, mgmtNum);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return Wifi.builder()
                        .wno(rs.getLong("wno"))
                        .mgmtNum(rs.getString("mgmtNum"))
                        .borough(rs.getString("borough"))
                        .wifiName(rs.getString("wifiName"))
                        .address(rs.getString("address"))
                        .addrDetail(rs.getString("addrDetail"))
                        .installLoc(rs.getString("installLoc"))
                        .installType(rs.getString("installType"))
                        .installAgency(rs.getString("installAgency"))
                        .serviceType(rs.getString("serviceType"))
                        .netType(rs.getString("netType"))
                        .installYear(rs.getString("installYear"))
                        .inoutDoor(rs.getString("inoutDoor"))
                        .wifiConnEnv(rs.getString("wifiConnEnv"))
                        .x(rs.getDouble("x"))
                        .y(rs.getDouble("y"))
                        .workDate(rs.getString("workDate"))
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closeConnection(pstmt, conn, rs);
        }
        return null;
    }

    public Wifi findById(Long id) {
        String sql = "select * from wifi where wno = ?";
        PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return Wifi.builder()
                        .wno(rs.getLong("wno"))
                        .mgmtNum(rs.getString("mgmtNum"))
                        .borough(rs.getString("borough"))
                        .wifiName(rs.getString("wifiName"))
                        .address(rs.getString("address"))
                        .addrDetail(rs.getString("addrDetail"))
                        .installLoc(rs.getString("installLoc"))
                        .installType(rs.getString("installType"))
                        .installAgency(rs.getString("installAgency"))
                        .serviceType(rs.getString("serviceType"))
                        .netType(rs.getString("netType"))
                        .installYear(rs.getString("installYear"))
                        .inoutDoor(rs.getString("inoutDoor"))
                        .wifiConnEnv(rs.getString("wifiConnEnv"))
                        .x(rs.getDouble("x"))
                        .y(rs.getDouble("y"))
                        .workDate(rs.getString("workDate"))
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closeConnection(pstmt, conn, rs);
        }
        return null;
    }
}
