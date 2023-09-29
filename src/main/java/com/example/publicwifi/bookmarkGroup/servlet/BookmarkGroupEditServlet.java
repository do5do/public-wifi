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

@WebServlet("/bookmarkGroup/edit")
public class BookmarkGroupEditServlet extends HttpServlet {
    private BookmarkGroupService bookmarkGroupService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        bookmarkGroupService = BookmarkGroupService.getInstance();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("bgno");
        BookmarkGroup bookmarkGroup = bookmarkGroupService.findById(Long.parseLong(id));

        request.setAttribute("bookmarkGroup", bookmarkGroup);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/bookmarkGroup/bookmarkGroupEditForm.jsp");
        dispatcher.forward(request, response);
    }
}
