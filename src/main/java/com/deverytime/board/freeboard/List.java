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
		// 1. 파라미터 받기
		String board = req.getParameter("board");
		if(board == null) board = "1"; // 기본값 자유게시판
		
		String category = req.getParameter("category"); //null 가능 -> 전체 주제
		
		// 검색 기능을 위해 추가
		String searchType = req.getParameter("searchType");
		String keyword = req.getParameter("keyword");
		
		// 2. 목록조회

		BoardService service = new BoardService();
		BoardDto dto = new BoardDto();
		
		dto.setBoardType(board);
		dto.setCategory(category);
		dto.setSearchType(searchType);
		dto.setKeyword(keyword);
				
		ArrayList<BoardDto> list = service.list(dto);
		
		
		// 3. 첨부
		req.setAttribute("list", list);
		req.setAttribute("board", board);
		req.setAttribute("param", dto);
		req.setAttribute("category", category);
		req.setAttribute("categoryMap", service.getCategoryMap());

		req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);
	}

}
