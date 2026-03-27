package com.deverytime.admin;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin/study-list.do")
public class ListStudy extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		AdminService service = new AdminService();
		String page = req.getParameter("page");
		String type = req.getParameter("type");
		String word = req.getParameter("word");

		HashMap<String, Object> result = service.getStudyList(page, type, word);

		req.setAttribute("list", result.get("list"));
		req.setAttribute("pagebar", result.get("pagebar"));
		req.setAttribute("type", result.get("type"));
		req.setAttribute("word", result.get("word"));
		req.setAttribute("totalCount", result.get("totalCount"));
		
		System.out.println("list: " + result.get("list"));
		System.out.println("pagebar: " + result.get("pagebar"));
		System.out.println("type: " + result.get("type"));
		System.out.println("word: " + result.get("word"));
		System.out.println("totalCount: " + result.get("totalCount"));

		req.getRequestDispatcher("/WEB-INF/views/admin/study-list.jsp").forward(req, resp);
	}
}