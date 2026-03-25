package com.deverytime.plan;

import java.io.IOException;
import java.util.ArrayList;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deverytime.model.PlanDto;
import com.deverytime.model.PlanService;

@WebServlet("/plan.do")
public class List extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		PlanService service = new PlanService();
		
		// TODO 로그인 기능 구현 후 제거해야 함
		session.setAttribute("auth", 4);
		
		int memberSeq = (Integer) session.getAttribute("auth");
		
		ArrayList<PlanDto> list = service.list(memberSeq);
		
		// member 학습계획 list jsp에 전달
		req.setAttribute("list", list);
		
		req.getRequestDispatcher("/WEB-INF/views/plan/list.jsp").forward(req, resp);
	}

}
