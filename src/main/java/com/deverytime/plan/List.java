package com.deverytime.plan;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deverytime.model.PlanDto;
import com.deverytime.model.PlanService;

@WebServlet("/plan.do")
public class List extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PlanService service = new PlanService();
		PlanDto dto = service.add();
		System.out.println(dto.getId());
		
		// TODO Auto-generated method stub
		req.getRequestDispatcher("/WEB-INF/views/plan/plantest.jsp").forward(req, resp);
	}

}
