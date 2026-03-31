package com.deverytime.comment;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deverytime.model.BoardDto;
import com.deverytime.model.CommentDto;

@WebServlet(value = "/comment/add.do")
public class Add extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Add.java
		// 1.정보 받아오기
		

		String seq = req.getParameter("seq");
		String board = req.getParameter("board");
		
		// 검색 카테고리 추가
		String category = req.getParameter("category");
		String searchType = req.getParameter("searchType");
		String keyword = req.getParameter("keyword");
		String page = req.getParameter("page");
		// 검색어 한글 오류 방지
		String keywordEnc = java.net.URLEncoder.encode(keyword, "UTF-8");
		
		// 돌아가기
		String redirectUrl = "/teamtwo/board/view.do?seq=" + seq + "&board=" + board + "&category=" + category
				+ "&searchType=" + searchType + "&keyword=" + keywordEnc + "&page=" + page;
		
		req.setCharacterEncoding("UTF-8");
		// 1.로그인 했나 안했나
		HttpSession session = req.getSession();
		if (session.getAttribute("auth") == null) {
			resp.sendRedirect(redirectUrl);
		}
		

		BoardDto dto = new BoardDto();
		dto.setBoardType(board);
		dto.setSeq(seq);

		// 

		dto.setCategory(category);
		dto.setSearchType(searchType);
		dto.setKeyword(keyword);

		// 댓글 정보
		
		CommentDto cDto = new CommentDto();
		String postSeq = seq; // 댓글 처리를 위한 글번호
		String id = (String) session.getAttribute("auth");
		String content = req.getParameter("content");

		cDto.setPostSeq(postSeq);
		cDto.setContent(content);
		cDto.setId(id);

		CommentService service = new CommentService();

		int result = service.add(cDto);

		// 리다이렉트 (상태보존)
		resp.sendRedirect(redirectUrl);

	}

}
