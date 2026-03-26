package com.deverytime.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/user/pw-find.do")
public class FindPw extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/views/user/pw-find.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json; charset=UTF-8");
		java.io.PrintWriter out = resp.getWriter();

		String action = req.getParameter("action");
		MemberService service = new MemberService(); // 서비스 호출
		int result = 0;

		// 교통 정리
		if ("checkId".equals(action)) {
			result = service.checkIdForPw(req);
		} else if ("sendAuth".equals(action)) {
			result = service.sendAuthMailForPw(req);
		} else if ("verify".equals(action)) {
			result = service.verifyAuthCodeAndSendPw(req);
		}

		out.print("{\"result\": " + result + "}");
		out.close();
	}
}