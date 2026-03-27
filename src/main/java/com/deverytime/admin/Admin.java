package com.deverytime.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deverytime.model.MemberDto;

@WebServlet("/admin/admin.do")
public class Admin extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 1. 관리자 권한 체크
		MemberDto authDto = (MemberDto) req.getSession().getAttribute("authDto");
		
		// type 1이 아니면 진입 불가
		if (authDto == null || authDto.getType() != 1) {
			resp.setContentType("text/html; charset=UTF-8");
			resp.getWriter().print("<script>alert('관리자만 접근 가능합니다.'); location.href='/teamtwo/index.do';</script>");
			resp.getWriter().close();
			return;
		}

		// 2. 서비스 불러서 대시보드 통계 가져오기
		AdminService service = new AdminService();
		service.loadDashboardStats(req);

		// 3. 관리자 메인 화면 띄우기
		req.getRequestDispatcher("/WEB-INF/views/admin/admin.jsp").forward(req, resp);
	}
}