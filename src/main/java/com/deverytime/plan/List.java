package com.deverytime.plan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deverytime.model.PlanDto;

@WebServlet("/plan/list.do")
public class List extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		PlanService service = new PlanService();
		
		// TODO 로그인 기능 구현 후 제거해야 함
		session.setAttribute("auth", 4);
		int memberSeq = (Integer) session.getAttribute("auth");
		
		// page
		String page = req.getParameter("page");
		String title = req.getParameter("title");
		String type = req.getParameter("type");
		
		// 페이징 + 조건 포함 map 생성
		HashMap<String, String> map = service.getPaging(page, title, type, memberSeq);

		// 페이지바 생성
		String pagebar = service.getPagebar(map);

		// 리스트
		ArrayList<PlanDto> list = service.list(map);
		
		// member 학습계획 list jsp에 전달
		// 전달
		req.setAttribute("map", map);
		req.setAttribute("pagebar", pagebar);
		req.setAttribute("list", list);
		
		req.getRequestDispatcher("/WEB-INF/views/plan/list.jsp").forward(req, resp);
	}
}
