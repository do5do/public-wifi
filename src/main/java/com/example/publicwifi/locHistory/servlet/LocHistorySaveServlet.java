package com.example.publicwifi.locHistory.servlet;

import com.example.publicwifi.locHistory.LocHistoryService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/locHistory/save")
public class LocHistorySaveServlet extends HttpServlet {
    private LocHistoryService locHistoryService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        locHistoryService = LocHistoryService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lnt = request.getParameter("lnt");
        String lat = request.getParameter("lat");
        locHistoryService.save(Double.parseDouble(lnt), Double.parseDouble(lat));
    }
}
