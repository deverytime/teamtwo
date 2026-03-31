package com.deverytime.admin;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deverytime.model.MemberDto;

@WebServlet("/admin/member-list.do")
public class ListMember extends HttpServlet {

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
		String page = req.getParameter("page");
		String type = req.getParameter("type");
		String word = req.getParameter("word");

		HashMap<String, Object> result = service.getMemberList(page, type, word);

		req.setAttribute("list", result.get("list"));
		req.setAttribute("pagebar", result.get("pagebar"));
		req.setAttribute("type", result.get("type"));
		req.setAttribute("word", result.get("word"));
		req.setAttribute("totalCount", result.get("totalCount"));
		
		System.out.println("list: " + (result.get("list")).toString());
		System.out.println("pagebar: " + (result.get("pagebar")).toString());
		System.out.println("type: " + result.get("type"));
		System.out.println("word: " + result.get("word"));
		System.out.println("totalCount: " + result.get("totalCount"));

		req.getRequestDispatcher("/WEB-INF/views/admin/member-list.jsp").forward(req, resp);
	}
}