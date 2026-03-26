package com.deverytime.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/user/mypage.do")
public class Mypage extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		
		// 1. 로그인 안 한 사람은 마이페이지로 진입 불가 (로그인 페이지로 이동)
		if (session.getAttribute("auth") == null) {
			resp.sendRedirect("/teamtwo/user/login.do");
			return; // 여기서 멈춤
		}
		
		// 2. 로그인 한 사람은 마이페이지 뷰(JSP)로 입장
		req.getRequestDispatcher("/WEB-INF/views/user/mypage.jsp").forward(req, resp);
	}
}