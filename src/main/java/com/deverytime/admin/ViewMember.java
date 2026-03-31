package com.deverytime.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deverytime.model.AdminMemberDto;
import com.deverytime.model.MemberDto;

@WebServlet("/admin/member-view.do")
public class ViewMember extends HttpServlet {

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
		
		AdminService service = new AdminService();
		
		String seq = req.getParameter("seq");
		AdminMemberDto dto = service.getMemberDetailInfo(seq);
		
		// TODO 확인용, 문제없으면 삭제해야 함
		System.out.println("========== Member Detail ==========");
		System.out.println("seq           : " + dto.getSeq());
		System.out.println("name          : " + dto.getName());
		System.out.println("id            : " + dto.getId());
		System.out.println("nickname      : " + dto.getNickname());
		System.out.println("email         : " + dto.getEmail());
		System.out.println("status        : " + dto.getStatus());
		System.out.println("failCount     : " + dto.getFailCount());

		System.out.println("-----------------------------------");

		System.out.println("totalPosts    : " + dto.getTotalPosts());
		System.out.println("totalComments : " + dto.getTotalComments());
		System.out.println("totalStudies  : " + dto.getTotalStudies());
		System.out.println("===================================");
		
		req.setAttribute("dto", dto);

		req.getRequestDispatcher("/WEB-INF/views/admin/member-view.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		AdminService service = new AdminService();

		String seq = req.getParameter("seq");
		String statusStr = req.getParameter("status");

		// 방어코드
		if (seq == null || statusStr == null) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		int status = Integer.parseInt(statusStr);

		// 상태 변경
		service.updateMemberStatus(seq, status);

		// 다시 상세 페이지로 이동
		resp.sendRedirect("/teamtwo/admin/member-view.do?seq=" + seq);
	}
}