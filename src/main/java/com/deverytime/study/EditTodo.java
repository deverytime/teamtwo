package com.deverytime.study;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deverytime.model.StudyScheduleDto;
import com.deverytime.model.StudyTodoDto;

@WebServlet(value = "/study/todo-edit.do")
public class EditTodo extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String seq = req.getParameter("seq");
		String scheduleSeq = req.getParameter("scheduleSeq");
		
		StudyTodoService service = new StudyTodoService();
		StudyTodoDto dto = service.get(seq);
		
		req.setAttribute("dto", dto);
		req.setAttribute("scheduleSeq", scheduleSeq);
		
		
		req.getRequestDispatcher("/WEB-INF/views/study/todo-edit.jsp").forward(req, resp);
		
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
			resp.getWriter().print("<script>alert('로그인이 필요한 서비스입니다.');history.back();</script>");
			resp.getWriter().close();
			return;
		}
		
		String scheduleSeq = req.getParameter("scheduleSeq");
		
		String seq = req.getParameter("seq");
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		String status= req.getParameter("status");
		
		//checkbox on이면 value값 1, off라서 안넘어오면 null
		if(status == null) {
			status = "0";
		}
		
		StudyTodoService service = new StudyTodoService();
		
		StudyTodoDto dto = new StudyTodoDto();
		
		dto.setSeq(seq);
		dto.setTitle(title);
		dto.setContent(content);
		dto.setStatus(status);
		
		int result = service.edit(dto);
		
		PrintWriter writer = resp.getWriter();
		
		if(result > 0) {
			writer.print("<script>");
			writer.print("alert('수정 완료!');");
			writer.print("location.href='/teamtwo/study/studyschedule-view.do?seq=" + scheduleSeq + "';");
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
