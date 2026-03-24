package com.test.deverytime.board.freeboard;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/freeboard/list.do")
public class List extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//List.java
		String column = req.getParameter("column");
		String word = req.getParameter("word");
		String search = "n";
		
		if((column == null & word == null) || word.trim().equals("")) {
			search = "n";
		} else {
			search = "y";
		}
		
		HashMap<String,String> map = new HashMap<String,String>();
		
		map.put("column", column);
		map.put("word", word);
		map.put("search", search);
		
		String page = req.getParameter("page");
		
		int nowPage = 0;	//현재 페이지 번호
		int totalCount = 0;	//총 게시물 수
		int pageSize = 10;	//한 페이지에서 보여줄 게시물 수
		int totalPage = 0;	//총 페이지 수
		int begin = 0;		//페이징 시작 위치(SQL)
		int end = 0;		//페이징 끝 위치(SQL)
		int n = 0;			//페이지바의 페이지 번호
		int loop = 0;		//페이지바의 루프 변수
		int blockSize = 10;	//페이지바의 페이지 개수
		
		if (page == null || page.equals("")) {
			nowPage = 1;
		} else {
			nowPage = Integer.parseInt(page);
		}
		
		//- list.do?page=1 > where rnum between 1 and 10
		//- list.do?page=2 > where rnum between 11 and 20
		//- list.do?page=3 > where rnum between 21 and 30
		begin = ((nowPage - 1) * pageSize) + 1;
		end = begin + pageSize - 1;
		
		map.put("begin", String.valueOf(begin));
		map.put("end", end + "");
		map.put("nowPage", nowPage + "");
		
		HttpSession session = req.getSession();
		
		BoardService service = new BoardService();
		
		ArrayList<BoardDto> list = service.list(map);
		
		

		req.getRequestDispatcher("/WEB-INF/views/freeboard/list.jsp").forward(req, resp);
	}

}
