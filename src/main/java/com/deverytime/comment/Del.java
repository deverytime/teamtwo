package com.deverytime.comment;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/comment/del.do")
public class Del extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Del.java
		// 삭제할 정보 가져오기
		String seq = req.getParameter("seq"); // 댓글 번호
		String postSeq = req.getParameter("postSeq");
		String board = req.getParameter("board");
		String category = req.getParameter("category");
		String searchType = req.getParameter("searchType");
		String keyword = req.getParameter("keyword");
		String page = req.getParameter("page");
		
		
		// 삭제
		CommentService service = new CommentService();
		
		int result = service.del(seq);
		
		
		// 한글 오류 방지
		String keywordEnc = URLEncoder.encode(req.getParameter("keyword"), StandardCharsets.UTF_8);

		String redirectUrl = "/teamtwo/board/freeboard/view.do?seq=" + postSeq +
		    "&board=" + req.getParameter("board") +
		    "&category=" + category+
		    "&searchType=" + searchType +
		    "&keyword=" + keywordEnc +
		    "&page=" + page;

		resp.sendRedirect(redirectUrl);  // encodeRedirectURL 제거!
	}

}
