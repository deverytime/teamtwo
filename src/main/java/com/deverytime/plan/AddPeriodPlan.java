package com.deverytime.plan;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deverytime.model.MemberDto;
import com.deverytime.model.PlanDto;

@WebServlet("/plan/periodplan-add.do")
public class AddPeriodPlan extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		
		// 세션에서 memberDto 가져오기
		MemberDto auth = (MemberDto) session.getAttribute("authDto");
		
		// 로그인안했으면 로그인 페이지로
		if (auth == null) {
		    resp.sendRedirect("/teamtwo/user/login.do");
		    return;
		}
		// dto 에 저장된 memberSeq 가져오기
		String memberSeq = auth.getSeq();
		
		req.setAttribute("memberSeq", memberSeq);
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