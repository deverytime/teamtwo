package com.deverytime.user;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/user/twofactor-setup.do")
public class TwofactorSetup extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getSession().getAttribute("auth") == null) {
			resp.sendRedirect("/teamtwo/user/login.do");
			return;
		}
		req.getRequestDispatcher("/WEB-INF/views/user/twofactor-setup.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json; charset=UTF-8");
		PrintWriter out = resp.getWriter();

		String action = req.getParameter("action");
		// 서비스 객체 호출
		MemberService service = new MemberService();

		if ("sendAuth".equals(action)) {
			// ==========================================
			// 1. 이메일 발송 (서비스한테 위임)
			// ==========================================
			String code = service.sendTwoFactorAuthMail(req);

			if (code != null) {
				out.print("{\"result\": 1, \"code\": \"" + code + "\"}");
			} else {
				out.print("{\"result\": 0}");
			}

		} else if ("enable".equals(action)) {
			// ==========================================
			// 2. 2차 인증 활성화 (기존 코드와 동일)
			// ==========================================
			int result = service.toggleTwoFactor(req, 1);
			out.print("{\"result\": " + result + "}");
		}

		out.close();
	}
}