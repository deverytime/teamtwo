package com.deverytime.user;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deverytime.model.MemberDto;

@WebServlet("/user/login.do")
public class Login extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그인 화면 띄우기
		req.getRequestDispatcher("/WEB-INF/views/user/login.jsp").forward(req, resp);
		// System.out.println("현재 로그인한 사람: " + req.getSession().getAttribute("auth"));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 1. 서비스 객체한테 로그인 검사 위임
		MemberService service = new MemberService();
		int result = service.login(req); 
		
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		
		// 2. 결과에 따른 처리
		if (result == 1) {
			// 로그인 성공
			// Service에서 넘겨준 회원 전체 정보(DTO)를 꺼냄
			MemberDto dto = (MemberDto) req.getAttribute("memberDto"); 
			HttpSession session = req.getSession();
			
			// 1) 인증 티켓 (아이디만 단일 저장 - 로그인 여부 확인용)
			session.setAttribute("auth", dto.getId()); 
			
			// 2) 개인 정보 뭉치 (글쓰기 등에서 seq, name 꺼내 쓸 때 활용)
			session.setAttribute("authDto", dto); 
			
			
			// 아이디 자동 저장 쿠키 생성
			String id = req.getParameter("id");
			String saveId = req.getParameter("saveId");
			if (saveId != null) {
				Cookie cookie = new Cookie("saveId", id);
				cookie.setMaxAge(60 * 60 * 24 * 30); // 30일
				cookie.setPath("/");
				resp.addCookie(cookie);
			} else {
				Cookie cookie = new Cookie("saveId", "");
				cookie.setMaxAge(0);
				cookie.setPath("/");
				resp.addCookie(cookie);
			}

			// 2차 인증 활성화 여부에 따른 페이지 이동
			if (dto.getTwoFactor() == 1) {
				// 2차 인증 페이지에서의 분기 처리를 위해 mode=login 이라는 꼬리표를 달아서 보냄
				resp.sendRedirect("/teamtwo/user/twofactor-setup.do?mode=login"); 
			} else {
				resp.sendRedirect("/teamtwo/index.do"); 
			}
			
		} else if (result == 0) {
			out.print("<script>alert('존재하지 않는 아이디입니다.'); history.back();</script>");
			out.close();
		} else if (result == -1) {
			out.print("<script>alert('로그인에 5회 이상 실패하여 계정 이용이 제한됩니다.'); location.href='/teamtwo/user/pw-find.do';</script>");
			out.close();
		} else if (result == -2) {
			// Service에서 넘겨준 현재 실패 횟수 꺼내기
			int currentFail = (int) req.getAttribute("currentFail");
			out.print("<script>alert('비밀번호가 일치하지 않습니다. (실패 " + currentFail + "/5)'); history.back();</script>");
			out.close();
		}
	}
}