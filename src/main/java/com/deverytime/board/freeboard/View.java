package com.deverytime.board.freeboard;

import java.io.IOException;

import java.util.HashSet;
import java.util.Set;

import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deverytime.model.BoardDto;
import com.deverytime.model.FileDto;

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
		
		// 검색 카테고리 추가
		String category = req.getParameter("category");
		String searchType = req.getParameter("searchType");
		String keyword = req.getParameter("keyword");
		
		dto.setCategory(category);
		dto.setSearchType(searchType);
		dto.setKeyword(keyword);
		
		BoardService service = new BoardService();
		
		// 2. 새로 고침 중복 조회수 방지
		Set<String> viewedPosts = (Set<String>) session.getAttribute("viewedPosts");
		if(viewedPosts == null) {
			// 처음 접속이면 null이므로 새 HashSet생성 후 세션에 저
			viewedPosts = new HashSet<>();
			session.setAttribute("viewedPosts", viewedPosts);
		}
		// viewPosts에 현재 글번호가 없으면 -> 처음 보는 글
		// viewPosts에 현재 글번호가 있으면 -> 이미 본 글
		if(!viewedPosts.contains(seq)) {
			
			// 현재 글 번호를 목록에 추가
			viewedPosts.add(seq);
			
			// 변경된 목록을 세션에 다시 저장
			session.setAttribute("viewedPosts", viewedPosts);
			
			// 조회수 + 1
			service.increaseReadCount(dto.getSeq());
		}
		
		
		// 3. DB작업 + 댓글도 가져와야함
		
		dto = service.view(dto);
		
		
		// 4. 파일 처리
		List<FileDto> fileList = service.getFileList(seq);
		
		req.setAttribute("dto", dto);
		req.setAttribute("fileList", fileList);
		
		req.getRequestDispatcher("/WEB-INF/views/board/freeboard/view.jsp").forward(req, resp);
	}

}