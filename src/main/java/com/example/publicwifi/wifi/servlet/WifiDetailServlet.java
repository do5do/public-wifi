package com.example.publicwifi.wifi.servlet;

import com.example.publicwifi.bookmarkGroup.BookmarkGroup;
import com.example.publicwifi.bookmarkGroup.BookmarkGroupService;
import com.example.publicwifi.wifi.Wifi;
import com.example.publicwifi.wifi.WifiService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/wifi/detail")
public class WifiDetailServlet extends HttpServlet {
    private WifiService wifiService;
    private BookmarkGroupService bookmarkGroupService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        wifiService = WifiService.getInstance();
        bookmarkGroupService = BookmarkGroupService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String wno = request.getParameter("wno");
        Wifi wifi;
        if (wno != null) {
            wifi = wifiService.findById(Long.parseLong(wno));
        } else {
            String mgmtNum = request.getParameter("mgmtNum");
            wifi = wifiService.findMgmtNum(mgmtNum);
        }

        List<BookmarkGroup> bookmarkGroups = bookmarkGroupService.findAll();

        request.setAttribute("wifi", wifi);
        request.setAttribute("bookmarkGroups", bookmarkGroups);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/wifi/wifiDetail.jsp");
        dispatcher.forward(request, response);
    }
}
