package com.deverytime.board.trendingboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deverytime.board.BoardService;
import com.deverytime.model.BoardDto;
import com.deverytime.paging.PagingService;

@WebServlet(value = "/board/trendingboard/list.do")
public class List extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// List.java

		// 페이지를 위함
		String page = req.getParameter("page");
		if (page == null || page.equals(""))
			page = "1";

		// 목록조회

		BoardService service = new BoardService();
		BoardDto dto = new BoardDto();

		ArrayList<BoardDto> list = service.trendingList();

		// 4. 페이징
		int totalCount = service.getTotalCount(dto);
		PagingService pagingService = new PagingService();
		HashMap<String, String> pagingMap = pagingService.getPaging(page, totalCount, dto.getPageSize());
		
		String pagebar = pagingService.getPagebar(pagingMap, "list.do");

		// 5. 첨부
		req.setAttribute("list", list);
		
		req.setAttribute("pagebar", pagebar);

		req.getRequestDispatcher("/WEB-INF/views/board/trending/list.jsp").forward(req, resp);
	}

}
