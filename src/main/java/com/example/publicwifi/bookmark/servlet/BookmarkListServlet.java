package com.example.publicwifi.bookmark.servlet;

import com.example.publicwifi.bookmark.Bookmark;
import com.example.publicwifi.bookmark.BookmarkService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/bookmark")
public class BookmarkListServlet extends HttpServlet {
    private BookmarkService bookmarkService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        bookmarkService = BookmarkService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Bookmark> bookmarks = bookmarkService.findAll();
        request.setAttribute("bookmarks", bookmarks);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/bookmark/bookmarkList.jsp");
        dispatcher.forward(request, response);
    }
}
