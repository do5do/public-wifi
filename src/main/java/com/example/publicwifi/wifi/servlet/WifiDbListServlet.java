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
import java.util.List;

@WebServlet("/wifi/v2")
public class WifiDbListServlet extends HttpServlet {
    private WifiService wifiService;

    @Override
    public void init() {
        wifiService = WifiService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lnt = request.getParameter("lnt");

        if (lnt != null) {
            String lat = request.getParameter("lat");
            List<Wifi> nearbyWifi = wifiService.getNearbyWifi(Double.parseDouble(lnt), Double.parseDouble(lat));

            request.setAttribute("lnt", lnt);
            request.setAttribute("lat", lat);
            request.setAttribute("wifiList", nearbyWifi);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/wifi/wifiList.jsp");
        dispatcher.forward(request, response);
    }
}
