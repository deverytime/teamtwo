package com.deverytime.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/user/check-pw.do")
public class CheckPw extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그인 여부 확인
		if (req.getSession().getAttribute("auth") == null) {
			resp.sendRedirect("/teamtwo/user/login.do");
			return;
		}
		
		// 비밀번호 확인을 한 후 어떤 목적지로 이동할지
		// 목적지(target) 파라미터를 받아서 JSP로 넘겨줌
		String target = req.getParameter("target");
		req.setAttribute("target", target);
		
		req.getRequestDispatcher("/WEB-INF/views/user/check-pw.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json; charset=UTF-8");
		
		// MemberService 불러서 비밀번호 맞는지 물어보기
		MemberService service = new MemberService();
		int result = service.checkPassword(req);
		
		// 결과 던져주기
		java.io.PrintWriter out = resp.getWriter();
		out.print("{\"result\": " + result + "}");
		out.close();
	}
}