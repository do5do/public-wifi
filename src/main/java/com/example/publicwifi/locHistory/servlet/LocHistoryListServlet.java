package com.example.publicwifi.locHistory.servlet;

import com.example.publicwifi.locHistory.LocHistory;
import com.example.publicwifi.locHistory.LocHistoryService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/locHistory")
public class LocHistoryListServlet extends HttpServlet {
    private LocHistoryService locHistoryService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        locHistoryService = LocHistoryService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<LocHistory> locHistories = locHistoryService.findAll();
        request.setAttribute("locHistories", locHistories);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/locHistory/locHistoryList.jsp");
        dispatcher.forward(request, response);
    }
}
