package com.deverytime.study;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deverytime.model.StudyScheduleDto;

@WebServlet(value = "/study/studyschedule-edit.do")
public class EditStudySchedule extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8"); 
		resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = req.getSession();
		
		Object auth = session.getAttribute("auth");
		
		//로그인 체크 로직
		if(auth == null) {
			resp.getWriter().print("<script>alert('로그인이 필요한 서비스입니다.');location.href='/teamtwo/index.do';</script>");
			resp.getWriter().close();
			return;
		}
		
		String seq = req.getParameter("seq");
		
		StudyScheduleService service = new StudyScheduleService();
		StudyScheduleDto dto = service.get(seq);
		
		req.setAttribute("dto", dto);
		
		req.getRequestDispatcher("/WEB-INF/views/study/studyschedule-edit.jsp").forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8"); 
		resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = req.getSession();
		
		Object auth = session.getAttribute("auth");
		
		//로그인 체크 로직
		if(auth == null) {
			resp.getWriter().print("<script>alert('로그인이 필요한 서비스입니다.');location.href='/teamtwo/index.do';</script>");
			resp.getWriter().close();
			return;
		}
		
		String seq = req.getParameter("seq");
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		String startDateStr = req.getParameter("startDate");
		String endDateStr = req.getParameter("endDate");
		
		LocalDate startDate = LocalDate.parse(startDateStr);
		LocalDate endDate = LocalDate.parse(endDateStr);
		
		if(startDate.isAfter(endDate)) {
			resp.getWriter().print("<script>alert('시작일은 종료일보다 빨라야 합니다.');history.back();</script>");
			resp.getWriter().close();
			return;
		}
		
		StudyScheduleService service = new StudyScheduleService();
		
		StudyScheduleDto dto = new StudyScheduleDto();
		
		dto.setSeq(seq);
		dto.setTitle(title);
		dto.setContent(content);
		dto.setStartDate(startDateStr);
		dto.setEndDate(endDateStr);
		
		int result = service.edit(dto);
		
		PrintWriter writer = resp.getWriter();
		
		if(result > 0) {
			writer.print("<script>");
			writer.print("alert('수정 완료!');");
			writer.print("location.href='/teamtwo/study/studyschedule-view.do?seq=" + seq + "';");
			writer.print("</script>");
		} else {
			writer.print("<script>");
			writer.print("alert('수정 실패!');");
			writer.print("history.back();");
			writer.print("</script>");
		}
		
		writer.close();
		
	}
	
}
