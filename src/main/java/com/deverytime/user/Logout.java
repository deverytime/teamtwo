package com.deverytime.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(value = "/user/logout.do")
public class Logout extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 1. 인증 티켓(세션) 제거하기
		HttpSession session = req.getSession();
		session.invalidate();
		
		// 2. '방금 전까지 있던 주소(Referer)' 가져오기
		String referer = req.getHeader("Referer");
		
		// 3. 튕겨내야 하는 '로그인 필수 / 보안 페이지' 조건 검사
		if (referer != null && (
			referer.contains("/admin/") ||             // 관리자 메뉴 
			referer.contains("/user/mypage.do") ||     // 마이페이지
			referer.contains("/plan/list.do")       // 나의 학습계획
			// 필요한 보안 페이지가 있다면 여기에 || 로 계속 추가
		)) {
			// 보안 구역에서 로그아웃 버튼을 눌렀다면 강제로 메인 화면으로 쫓아냄
			resp.sendRedirect("/teamtwo/index.do");
		} else {
			// 일반 자유게시판 같은 오픈된 곳이라면 원래 있던 위치에 머문다
			resp.sendRedirect(referer != null ? referer : "/teamtwo/index.do");
		}
		
	}
}