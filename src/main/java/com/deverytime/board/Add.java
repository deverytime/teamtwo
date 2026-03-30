package com.deverytime.board;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deverytime.model.BoardDto;
import com.deverytime.model.FileDto;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet(value = "/board/add.do")
public class Add extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Add.java

		// 1.로그인 했나 안했나
		HttpSession session = req.getSession();

		// session.setAttribute("auth", "um1234"); // 로그인 기능 연결전 임시 세션 만들기 끝나면 삭제

		if (session.getAttribute("auth") == null) {
			resp.sendRedirect("/teamtwo/user/login.do");
			return;
		}

		// 2. 어느 게시판에서 온건지 저장
		String board = req.getParameter("board");
		String category = req.getParameter("category");
		String keyword = req.getParameter("keyword");
		String searchType = req.getParameter("searchType");

		// 3. 주제 보내주기
		BoardService service = new BoardService();

		req.setAttribute("board", board);
		req.setAttribute("categoryMap", service.getCategoryMap());
		req.setAttribute("searchType", searchType);
		req.setAttribute("category", category);
		req.setAttribute("keyword", keyword);

		req.getRequestDispatcher("/WEB-INF/views/board/add.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// AddOk.java

		// 1. 보낸 정보 받기
		String uploadPath = getServletContext().getRealPath("/uploads");
		// System.out.println("업로드 경로: " + uploadPath);

		// 폴더 없으면 자동 생성
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}

		MultipartRequest mr = new MultipartRequest(req, uploadPath, 10 * 1024 * 1024, // 10MB
				"UTF-8", new DefaultFileRenamePolicy());

		HttpSession session = req.getSession();

		String id = (String) session.getAttribute("auth");
		String board = mr.getParameter("board");
		String category = mr.getParameter("subject");
		String title = mr.getParameter("title");
		String content = mr.getParameter("content");
		String searchType = mr.getParameter("searchType");
		String keyword = mr.getParameter("keyword");
		String paramCategory = mr.getParameter("category");
		String page = mr.getParameter("page");

		BoardDto dto = new BoardDto();
		dto.setId(id);
		dto.setBoardType(board);
		dto.setCategory(category);
		dto.setTitle(title);
		dto.setContent(content);

		System.out.println(keyword);
		System.out.println(keyword);
		System.out.println(keyword);
		System.out.println(keyword);
		
		BoardService service = new BoardService();

		int result = service.add(dto);

		if (result > 0) {
			// 파일 올리기
		    Enumeration<String> fileNames = mr.getFileNames();

		    while (fileNames.hasMoreElements()) {
		        String fieldName = fileNames.nextElement();

		        String originName = mr.getOriginalFileName(fieldName);
		        String saveName = mr.getFilesystemName(fieldName);
		        File file = mr.getFile(fieldName);

		        if (originName != null && saveName != null && file != null) {

		            FileDto fileDto = new FileDto();
		            fileDto.setOriginName(originName);
		            fileDto.setName(saveName);
		            fileDto.setPath("/uploads");
		            fileDto.setFileSize(file.length());

		            // 방금 작성한 글번호
		            fileDto.setPostSeq(String.valueOf(result));

		            service.addFile(fileDto);
		        }
		    }
		    
		    // 한글오류
		    String encodedKeyword = URLEncoder.encode(keyword, "UTF-8");

		    resp.sendRedirect("list.do?board=" + dto.getBoardType() + "&category=" + paramCategory + "&searchType=" + searchType + "&keyword=" + encodedKeyword + "&page=" + page );
		} else {
			// history.back();
//			resp.sendRedirect("javascript:history.back()");
			PrintWriter out = resp.getWriter();
			out.println("<script>history.back();</script>");
		}

	}

}
