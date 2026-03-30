package com.deverytime.board.request;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deverytime.admin.AdminService;
import com.deverytime.model.MemberDto;
import com.deverytime.model.RequestDto;

@WebServlet("/board/request/add.do")
public class Add extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		
		// 세션에서 memberDto 가져오기
		MemberDto auth = (MemberDto) session.getAttribute("authDto");
		
		// 로그인안했으면 로그인 페이지로
		if (auth == null) {
		    resp.sendRedirect("/teamtwo/user/login.do");
		    return;
		}
		// dto 에 저장된 memberSeq 가져오기
		String memberSeq = auth.getSeq();
		
		req.setAttribute("memberSeq", memberSeq);

		req.getRequestDispatcher("/WEB-INF/views/board/request/add.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		
		// 세션에서 memberDto 가져오기
		MemberDto auth = (MemberDto) session.getAttribute("authDto");
		
		// 로그인안했으면 로그인 페이지로
		if (auth == null) {
		    resp.sendRedirect("/teamtwo/user/login.do");
		    return;
		}
		// dto 에 저장된 memberSeq 가져오기
		String memberSeq = auth.getSeq();
		AdminService service = new AdminService();
	    
		RequestDto dto = RequestDto.builder()
		        .title(req.getParameter("title"))
		        .subject(Integer.parseInt(req.getParameter("subject")))
		        .content(req.getParameter("content"))
		        .memberSeq(memberSeq)
		        .build();

	    int result = service.addRequest(dto);
	    
	    if (result == 1) {
	        resp.sendRedirect("/teamtwo/board/request/list.do");
	    } else {
	        req.setAttribute("error", "등록 실패");
	        req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
	    }
		
	}
}