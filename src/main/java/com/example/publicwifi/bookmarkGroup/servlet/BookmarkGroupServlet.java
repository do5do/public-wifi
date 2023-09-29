package com.example.publicwifi.bookmarkGroup.servlet;

import com.example.publicwifi.bookmarkGroup.BookmarkGroup;
import com.example.publicwifi.bookmarkGroup.BookmarkGroupService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/bookmarkGroup")
public class BookmarkGroupServlet extends HttpServlet {
    private BookmarkGroupService bookmarkGroupService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        bookmarkGroupService = BookmarkGroupService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<BookmarkGroup> bookmarkGroups = bookmarkGroupService.findAll();
        request.setAttribute("bookmarkGroups", bookmarkGroups);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/bookmarkGroup/bookmarkGroup.jsp");
        dispatcher.forward(request, response);
    }
}
