package com.example.publicwifi.wifi.servlet;

import com.example.publicwifi.wifi.WifiService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/wifi/load")
public class WifiSaveServlet extends HttpServlet {
    private WifiService wifiService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        wifiService = WifiService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long totalCount = wifiService.loadAndSaveAll();
        request.setAttribute("totalCount", totalCount);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/wifi/wifiLoad.jsp");
        dispatcher.forward(request, response);
    }
}
