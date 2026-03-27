package com.deverytime.user;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/user/pw-edit.do")
public class EditPw extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getSession().getAttribute("auth") == null) {
			resp.sendRedirect("/teamtwo/user/login.do");
			return;
		}
		req.getRequestDispatcher("/WEB-INF/views/user/pw-edit.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 서비스 객체한테 비밀번호 변경 + 세션 파기 업무 위임
		MemberService service = new MemberService();
		int result = service.editPassword(req);

		resp.setContentType("application/json; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.print("{\"result\": " + result + "}");
		out.close();
	}
}