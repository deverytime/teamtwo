package com.deverytime.board.freeboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deverytime.model.BoardDto;

@WebServlet(value = "/board/freeboard/view.do")
public class View extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// View.java
		// 1. 정보 받아오기
		HttpSession session = req.getSession();
		
		String board = req.getParameter("board");
		String seq = req.getParameter("seq");
		
		BoardDto dto = new BoardDto();
		dto.setBoardType(board);
		dto.setSeq(seq);
		
		// 2. DB작업
		BoardService service = new BoardService();
		
		dto = service.view(dto);

		req.setAttribute("dto", dto);
		
		req.getRequestDispatcher("/WEB-INF/views/board/freeboard/view.jsp").forward(req, resp);
	}

}