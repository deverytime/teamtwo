package com.deverytime.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/user/find-id.do")
public class FindId extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/views/user/find-id.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		resp.setContentType("application/json; charset=UTF-8");
		java.io.PrintWriter out = resp.getWriter();

		String action = req.getParameter("action");
		com.deverytime.model.MemberDao dao = new com.deverytime.model.MemberDao();

		if ("checkEmail".equals(action)) {
			// 1단계: 이메일 존재 확인
			String email = req.getParameter("email");
			int result = dao.checkEmail(email);
			out.print("{\"result\": " + result + "}");

		} else if ("sendAuth".equals(action)) {
			// 2단계: 이름+이메일 확인 후 인증번호 발송
			String email = req.getParameter("email");
			String name = req.getParameter("name");

			String id = dao.findId(name, email);
			if (id != null) {
				// 랜덤 6자리 생성 및 세션 저장
				String authCode = String.format("%06d", (int) (Math.random() * 1000000));
				req.getSession().setAttribute("findIdAuthCode", authCode);

				// 진짜 메일 쏘기!
				String subject = "[deverytime] 아이디 찾기 인증번호 안내";
				String content = "<h3>요청하신 인증번호입니다.</h3><h1>" + authCode + "</h1><p>화면에 위 번호를 입력해주세요.</p>";
				com.deverytime.library.MailUtil.sendMail(email, subject, content);

				out.print("{\"result\": 1}");
			} else {
				out.print("{\"result\": 0}");
			}

		} else if ("verify".equals(action)) {
			// 3단계: 인증번호 확인 후 진짜 아이디 발송
			String inputCode = req.getParameter("authCode");
			String sessionCode = (String) req.getSession().getAttribute("findIdAuthCode");

			if (sessionCode != null && sessionCode.equals(inputCode)) {
				String email = req.getParameter("email");
				String name = req.getParameter("name");
				String id = dao.findId(name, email);

				// 아이디 메일 쏘기!
				String subject = "[deverytime] 아이디 찾기 결과 안내";
				String content = "<h3>" + name + "님의 아이디입니다.</h3><h1>" + id + "</h1><p>로그인 페이지를 이용해주세요.</p>";
				com.deverytime.library.MailUtil.sendMail(email, subject, content);

				// 볼일 끝난 세션 파기
				req.getSession().removeAttribute("findIdAuthCode");
				out.print("{\"result\": 1}");
			} else {
				out.print("{\"result\": 0}");
			}
		}
		out.close();
	}
}