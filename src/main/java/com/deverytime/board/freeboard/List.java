package com.deverytime.board.freeboard;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deverytime.model.BoardDto;

@WebServlet(value = "/board/freeboard/list.do")
public class List extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//List.java
		// 1. 작성된 글 불러오기
		// 2. 넘기기
		BoardService service = new BoardService();
		
		ArrayList<BoardDto> list = service.list();
		
		String board = "1";
		
		req.setAttribute("list", list);
		req.setAttribute("board", board);
		req.setAttribute("categoryMap", service.getCategoryMap());

		req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);
	}

}
