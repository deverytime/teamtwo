package com.test.deverytime.study;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.test.semi.board.BoardService;
import com.test.semi.model.BoardDto;

@WebServlet(value = "/study/study-list.do")
public class ListStudy extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
			String word = req.getParameter("word");
			String search = "n"; //목록보기(n), 검색하기(y)
			
			if(word == null || word.trim().equals("")) {
				search = "n";
			} else {
				search = "y";
			}
			
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("word", word);
			map.put("search", search);
			
			String page = req.getParameter("page");
			
			int nowPage = 0;		//현재 페이지 번호
			int totalCount = 0; 	//총 스터디 수
			int pageSize = 8; 		//한페이지에서 보여줄 스터디 수
			int totalPage = 0;      //총 페이지 수
			int begin = 0;			//페이징 시작 위치
			int end = 0;			//페이징 끝 위치
			int n = 0; 				//페이지 바의 페이지 번호
			int loop = 0 ;			//페이지 바의 루프변수
			int blockSize = 10; 		//페이지 바의 페이지 개수
			
			if(page == null && page.equals("")) {
				nowPage = 1;
			} else {
				nowPage = Integer.parseInt(page);
			}
			
			begin = ((nowPage - 1) * pageSize) + 1;
			end = begin + pageSize - 1;
			
			map.put("begin", begin + "");
			map.put("end", end + "");
			map.put("nowPage", nowPage + "");
			
				
		
		req.getRequestDispatcher("/WEB-INF/views/study/study-list.jsp").forward(req, resp);
		
	}
	
}
