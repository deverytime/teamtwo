package com.deverytime.board.freeboard;

import java.io.IOException;
import java.io.PrintWriter;

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
		
		// 로그인 안한 사용자면 안내 메시지 출력
		if(session.getAttribute("auth") == null) {
		    PrintWriter out = resp.getWriter();
		    out.println("<script>");
		    out.println("alert('로그인한 사용자만 추천할 수 있습니다!');");
		    out.println("history.back();");
		    out.println("</script>");
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
		
		if(result == 1) {
			// 추천 처리
			resp.sendRedirect("view.do?board=" + board + "&seq=" + seq);
			
		} else {
			// 이미 추천한 경우
			PrintWriter out = resp.getWriter();
		    out.println("<script>");
		    out.println("alert('이미 추천한 글입니다!');");
		    out.println("history.back();");
		    out.println("</script>");
		}
		 
		
		
	}

}