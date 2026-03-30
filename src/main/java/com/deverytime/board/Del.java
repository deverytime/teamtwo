package com.deverytime.board;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/board/del.do")
public class Del extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//Del.java
		// 1. 정보받기
		String seq = req.getParameter("seq");
		String board = req.getParameter("board");
		
		//
		String category = req.getParameter("category");
		String searchType = req.getParameter("searchType");
		String keyword = req.getParameter("keyword");
		String page = req.getParameter("page");
		 		
		
		// 2. DB 작업(삭제)
		BoardService service = new BoardService();
		
		int result = service.del(seq);
		
		// 한글오류
	    String encodedKeyword = URLEncoder.encode(keyword, "UTF-8");
		
		if(result == 1) {
			// 삭제완료
			resp.sendRedirect("list.do?board=" + board
					+ "&category=" + category  
					+ "&searchType=" + searchType
					+ "&keyword=" + encodedKeyword
					+ "&page=" + page);
			
		} else {
			// 삭제실패
			resp.setContentType("text/html; charset=UTF-8");
	        resp.getWriter().println("<script>alert('삭제 실패'); history.back();</script>");
		}

	}

}