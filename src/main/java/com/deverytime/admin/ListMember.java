package com.deverytime.admin;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin/list-member.do")
public class ListMember extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

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

		req.getRequestDispatcher("/WEB-INF/views/admin/member-list.jsp").forward(req, resp);
	}
}