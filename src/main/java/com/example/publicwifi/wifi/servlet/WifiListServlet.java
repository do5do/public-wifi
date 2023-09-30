package com.example.publicwifi.wifi.servlet;

import com.example.publicwifi.util.Page;
import com.example.publicwifi.wifi.Wifi;
import com.example.publicwifi.wifi.WifiService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/wifi")
public class WifiListServlet extends HttpServlet {
    private WifiService wifiService;

    @Override
    public void init() {
        wifiService = WifiService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("page");

        if (page == null || page.isEmpty()) {
            request.setAttribute("pageable", null);
            request.setAttribute("wifiList", null);
        } else {
            String lnt = request.getParameter("lnt");
            String lat = request.getParameter("lat");
            Page<Wifi> wifiPage = wifiService.getWifiListWithLoc(Integer.parseInt(page), Double.parseDouble(lnt), Double.parseDouble(lat));

            request.setAttribute("lnt", lnt);
            request.setAttribute("lat", lat);
            request.setAttribute("pageable", wifiPage.getPageable());
            request.setAttribute("wifiList", wifiPage.getContents());
        }

        request.setAttribute("type", "open");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/wifi/wifiList.jsp");
        dispatcher.forward(request, response);
    }
}
