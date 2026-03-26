package com.deverytime.plan;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deverytime.model.PlanDto;

@WebServlet("/plan/periodplan-add.do")
public class AddPeriodPlan extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// TODO 로그인 연동 후 삭제
//		HttpSession session = req.getSession();
//		String memberSeq = (String) session.getAttribute("auth");
		
		// TODO 로그인 연동 전 임시
		req.setAttribute("memberSeq", 4);
		req.setAttribute("type", "기간기반");

		req.getRequestDispatcher("/WEB-INF/views/plan/periodplan-add.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		PlanService service = new PlanService();
	    
	    PlanDto dto = PlanDto.builder()
	    	    .type(req.getParameter("type"))
	    	    .title(req.getParameter("title"))
	    	    .subject(req.getParameter("subject"))
	    	    .description(req.getParameter("description"))
	    	    .startDate(Date.valueOf(req.getParameter("startDate")))
	    	    .endDate(Date.valueOf(req.getParameter("endDate")))
	    	    .memberSeq(req.getParameter("memberSeq"))
	    	    .build();

	    int result = service.add(dto);
	    
	    if (result == 1) {
	        resp.sendRedirect("/teamtwo/plan/list.do");
	    } else {
	        req.setAttribute("error", "등록 실패");
	        req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
	    }
	}
}