package com.deverytime.admin;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deverytime.model.MemberDto;

@WebServlet("/board/request/list.do")
public class ListRequest extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		AdminService service = new AdminService();

		String page = req.getParameter("page");
		String type = req.getParameter("type");
		String word = req.getParameter("word");
		String subject = req.getParameter("subject");
		String status = req.getParameter("status");

		HashMap<String, Object> result = service.getRequestList(page, type, word, subject, status);

		req.setAttribute("list", result.get("list"));
		req.setAttribute("pagebar", result.get("pagebar"));
		req.setAttribute("type", result.get("type"));
		req.setAttribute("word", result.get("word"));
		req.setAttribute("subject", result.get("subject"));
		req.setAttribute("status", result.get("status"));
		req.setAttribute("totalCount", result.get("totalCount"));

		System.out.println("list: " + result.get("list"));
		System.out.println("pagebar: " + result.get("pagebar"));
		System.out.println("type: " + result.get("type"));
		System.out.println("word: " + result.get("word"));
		System.out.println("subject: " + result.get("subject"));
		System.out.println("status: " + result.get("status"));
		System.out.println("totalCount: " + result.get("totalCount"));

		req.getRequestDispatcher("/WEB-INF/views/board/request/list.jsp").forward(req, resp);
	}
}