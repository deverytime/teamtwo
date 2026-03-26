package com.deverytime.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/user/id-find.do")
public class FindId extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/views/user/id-find.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json; charset=UTF-8");
		java.io.PrintWriter out = resp.getWriter();

		String action = req.getParameter("action");
		MemberService service = new MemberService(); // 서비스 호출
		int result = 0;

		// 액션에 따라 서비스에게 업무 지시
		if ("checkEmail".equals(action)) {
			result = service.checkEmail(req);
			
		} else if ("sendAuth".equals(action)) {
			result = service.sendAuthMail(req);
			
		} else if ("verify".equals(action)) {
			result = service.verifyAuthCode(req);
		}

		// 최종 결과만 클라이언트에게 전송
		out.print("{\"result\": " + result + "}");
		out.close();
	}
}