package com.example.publicwifi.bookmarkGroup.servlet;

import com.example.publicwifi.bookmarkGroup.BookmarkGroupService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/bookmarkGroup/update")
public class BookmarkGroupUpdateServlet extends HttpServlet {
    private BookmarkGroupService bookmarkGroupService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        bookmarkGroupService = BookmarkGroupService.getInstance();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("bgno");
        String name = request.getParameter("name");
        String seq = request.getParameter("seq");
        bookmarkGroupService.updateBookmarkGroup(name, Long.parseLong(seq), Long.parseLong(id));

        request.setAttribute("msg", "수정");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/bookmarkGroup/bookmarkGroupResult.jsp");
        dispatcher.forward(request, response);
    }
}
