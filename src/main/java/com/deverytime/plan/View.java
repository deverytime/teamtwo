package com.deverytime.plan;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deverytime.model.PlanDto;

@WebServlet("/plan/view.do")
public class View extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PlanService service = new PlanService();
		
		HttpSession session = req.getSession();
		// TODO login 연동 후 주석해제해야 함
//		String memberSeq = ((MemberDto) session.getAttribute("authDto")).getSeq();
		
		// 임시
		String memberSeq = "4";
		
		String seq = req.getParameter("seq");
		PlanDto dto = service.get(seq, memberSeq);
		
		req.setAttribute("dto", dto);

		req.getRequestDispatcher("/WEB-INF/views/plan/view.jsp").forward(req, resp);
	}
}