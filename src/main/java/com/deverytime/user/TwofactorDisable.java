package com.deverytime.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/user/twofactor-disable.do")
public class TwofactorDisable extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그인을 하지 않고 접속 시도 시 로그인 페이지로 이동
		if (req.getSession().getAttribute("auth") == null) {
			resp.sendRedirect("/teamtwo/user/login.do");
			return;
		}

		MemberService service = new MemberService();
		
		// 서비스에게 0 (비활성화) 으로 바꾸라고 지시
		service.toggleTwoFactor(req, 0);

		// 처리가 끝나면 마이페이지로 이동
		resp.sendRedirect("/teamtwo/user/mypage.do");
	}
}