package com.deverytime.board;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deverytime.model.BoardDto;

@WebServlet(value = "/board/freeboard/recommend.do")
public class Recommend extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//recommend.java
		
		// 1. 정보받기
		resp.setContentType("text/html; charset=UTF-8");
		HttpSession session = req.getSession();
		
		String category = req.getParameter("category"); // 주소 파라미터
		String searchType = req.getParameter("searchType");
		String keyword = req.getParameter("keyword");
		String page = req.getParameter("page");
		
		// 로그인 안한 사용자면 안내 메시지 출력
		if(session.getAttribute("auth") == null) {
		    resp.sendRedirect("view.do?board=" + req.getParameter("board") + "&seq=" + req.getParameter("seq") +"&msg=login");
		    return;
		}
		
		String seq = req.getParameter("seq");
		String board = req.getParameter("board");
		
		BoardDto dto = new BoardDto();
		dto.setSeq(seq);
		dto.setId((String)session.getAttribute("auth"));
		
		// 2. 추천 처리
		BoardService service = new BoardService();
		
		int result = service.recommend(dto);
		
		
		String encodedKeyword = URLEncoder.encode(keyword, "UTF-8");
		
		if(result == 1) {
			// 추천 처리
			resp.sendRedirect("view.do?board=" + board + "&seq=" + seq + "&category=" + category + "&searchType=" + searchType + "&keyword=" + keyword + "&page=" + page);
			
		} else {
			// 이미 추천한 경우
			resp.sendRedirect("view.do?board=" + board + "&seq=" + seq + "&category=" + category + "&searchType=" + searchType + "&keyword=" + keyword + "&page=" + page +"&msg=already");
		}
		 
		
		
	}

}