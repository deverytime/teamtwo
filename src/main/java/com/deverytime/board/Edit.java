package com.deverytime.board;

import java.io.File;
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
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet(value = "/board/edit.do")
public class Edit extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Edit.java

		// 1.로그인 했나 안했나
		HttpSession session = req.getSession();
		if(session.getAttribute("auth") == null) {
			return;
		}

		// 2. 어느 게시판에서 온건지 저장
		String board = req.getParameter("board");
		String seq = req.getParameter("seq");

		// 3. 입력된 정보 가져와서 뿌려주기
		BoardService service = new BoardService();
		BoardDto dto = new BoardDto();
		dto.setSeq(seq);
		dto = service.getPostBySeq(dto); // 글번호를 가지고 정보 가져와서 보여주기
		dto.setBoardType(board);

		req.setAttribute("dto", dto);
		req.setAttribute("categoryMap", service.getCategoryMap());

		req.getRequestDispatcher("/WEB-INF/views/board/edit.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 1. 보낸 정보 받기
		HttpSession session = req.getSession();
		if(session.getAttribute("auth") == null) {
			return;
		}
		
		String uploadPath = getServletContext().getRealPath("/uploads");
		// System.out.println("업로드 경로: " + uploadPath);

		// 폴더 없으면 자동 생성
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}

		MultipartRequest mr = new MultipartRequest(req, uploadPath, 10 * 1024 * 1024, // 10MB
				"UTF-8", new DefaultFileRenamePolicy());


		String id = (String) session.getAttribute("auth");
		String board = mr.getParameter("board");
		String category = mr.getParameter("subject"); // 작성자가 선택한 카테고리
		String title = mr.getParameter("title");
		String content = mr.getParameter("content");
		String seq = mr.getParameter("seq");
		String paramCategory = mr.getParameter("category"); // 주소 파라미터
		String page = mr.getParameter("page");
		String searchType = mr.getParameter("searchType");
		String keyword = mr.getParameter("keyword");
		
		BoardDto dto = new BoardDto();
		dto.setId(id);
		dto.setBoardType(board);
		dto.setCategory(category);
		dto.setTitle(title);
		dto.setContent(content);
		dto.setSeq(seq);
		
		BoardService service = new BoardService();
		String encodedKeyword = URLEncoder.encode(keyword, "UTF-8");

		int result = service.edit(dto);
		
		if (result == 1) {
			// view.do로
			resp.sendRedirect("view.do?board=" + board 
					+ "&seq=" + dto.getSeq()
					+ "&category=" + paramCategory
					+ "&searchType=" + searchType
					+ "&keyword=" + encodedKeyword
					+ "&page=" + page
					);
		} else {
			// history.back();
//					resp.sendRedirect("javascript:history.back()");
			PrintWriter out = resp.getWriter();
			out.println("<script>history.back();</script>");
		}

	}

}
