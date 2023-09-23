package com.example.publicwifi.wifi;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/wifi")
public class WifiListServlet extends HttpServlet {
    private final WifiService wifiService;

    public WifiListServlet(WifiService wifiService) {
        this.wifiService = wifiService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // get wifiList from service
    }
}
