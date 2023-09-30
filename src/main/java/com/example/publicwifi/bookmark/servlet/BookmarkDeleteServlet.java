package com.example.publicwifi.bookmark.servlet;

import com.example.publicwifi.bookmark.BookmarkService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/bookmark/delete")
public class BookmarkDeleteServlet extends HttpServlet {
    private BookmarkService bookmarkService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        bookmarkService = BookmarkService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bno = request.getParameter("bno");
        bookmarkService.delete(Long.parseLong(bno));

        request.setAttribute("msg", "삭제");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/bookmark/bookmarkResult.jsp");
        dispatcher.forward(request, response);
    }
}
