package com.deverytime.study;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deverytime.model.StudyDto;

@WebServlet(value = "/study/study-del.do")
public class DelStudy extends HttpServlet{

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
		
		StudyService service = new StudyService();
		
		int result = service.del(seq);
		
		PrintWriter writer = resp.getWriter();
		
		if(result > 0) {
			writer.print("<script>");
			writer.print("alert('삭제 완료!');");
			writer.print("location.href='/teamtwo/study/studyschedule-list.do';");
			writer.print("</script>");
		} else {
			writer.print("<script>");
			writer.print("alert('삭제 실패!');");
			writer.print("history.back();");
			writer.print("</script>");
		}
		
		writer.close();
		
	}
	
}
