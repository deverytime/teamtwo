package com.deverytime.study;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deverytime.model.StudyScheduleDto;
import com.deverytime.model.StudyTodoDto;

@WebServlet(value = "/study/todo-add.do")
public class AddTodo extends HttpServlet{

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
		req.setAttribute("seq", seq);
		
		req.getRequestDispatcher("/WEB-INF/views/study/todo-add.jsp").forward(req, resp);
		
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
		String content =  req.getParameter("content");
		
		StudyTodoService service = new StudyTodoService();
		
		StudyTodoDto dto = new StudyTodoDto();
		dto.setTitle(title);
		dto.setContent(content);
		
		int result = service.add(dto, seq);
		
		if(result > 0) {
			resp.sendRedirect("/teamtwo/study/studyschedule-view.do?seq=" + seq);
		} else {
			resp.getWriter().print("<script>alert('failed');history.back();</script>");
			resp.getWriter().close();
		}
		
	}
	
}
