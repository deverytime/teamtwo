// 회원가입
// 작성자: 황윤재(26.03.25)
package com.deverytime.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/user/register.do")
public class Register extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 회원가입 폼 화면 띄우기
		req.getRequestDispatcher("/WEB-INF/views/user/register.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 1. 서비스 객체 소환
		MemberService service = new MemberService();
		
		// 2. 서비스에게 파일 업로드 및 DB 저장까지 위임
		int result = service.register(req);
		
		// 3. 결과에 따른 페이지 이동
		if (result > 0) {
			// 가입 성공: 기획서(MEM-01)에 따라 로그인 페이지로 이동
			resp.sendRedirect("/teamtwo/user/login.do");
		} else {
			// 가입 실패: 경고창 띄우고 뒤로 가기
			resp.setContentType("text/html; charset=UTF-8");
			resp.getWriter().print("<script>alert('회원가입에 실패했습니다.'); history.back();</script>");
			resp.getWriter().close();
		}
	}
}