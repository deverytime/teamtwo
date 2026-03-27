package com.deverytime.study;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deverytime.model.MemberDto;
import com.deverytime.model.StudyDto;
import com.deverytime.model.StudyScheduleDto;

@WebServlet(value = "/study/studyschedule-add.do")
public class AddStudySchdule extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
		String seq = req.getParameter("seq");
		req.setAttribute("seq", seq);
		
		req.getRequestDispatcher("/WEB-INF/views/study/studyschedule-add.jsp").forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = req.getSession();
		
		Object auth = session.getAttribute("auth");
		
		//로그인 체크 로직
		if(auth == null) {
			resp.getWriter().print("<script>alert('로그인이 필요한 서비스입니다.');history.back();</script>");
			resp.getWriter().close();
			return;
		}
		
		String seq = req.getParameter("seq");
		
		String title = req.getParameter("title");
		String content =  req.getParameter("content");
		String startDate =  req.getParameter("startDate");
		String endDate =  req.getParameter("endDate");
		
		StudyScheduleService service = new StudyScheduleService();
		
		StudyScheduleDto dto = new StudyScheduleDto();
		dto.setTitle(title);
		dto.setContent(content);
		dto.setStartDate(startDate);
		dto.setEndDate(endDate);
		
		System.out.println(seq);
		
		int result = service.add(dto, seq);
		
		if(result > 0) {
			resp.sendRedirect("/teamtwo/study/studyschedule-list.do?seq=" + seq);
		} else {
			resp.getWriter().print("<script>alert('failed');history.back();</script>");
			resp.getWriter().close();
		}
		
	}
	
}
