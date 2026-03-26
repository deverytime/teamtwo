package com.deverytime.board.freeboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/board/freeboard/del.do")
public class Del extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//Del.java
		// 1. 정보받기
		String seq = req.getParameter("seq");
		String boardType = req.getParameter("boardType");
		
		// 2. DB 작업(삭제)
		BoardService service = new BoardService();
		
		int result = service.del(seq);
		
		if(result == 1) {
			// 삭제완료
			resp.sendRedirect("list.do?board=" + boardType);
			
		} else {
			// 삭제실패
			resp.setContentType("text/html; charset=UTF-8");
	        resp.getWriter().println("<script>alert('삭제 실패'); history.back();</script>");
		}

	}

}