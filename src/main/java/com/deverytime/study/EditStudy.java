package com.deverytime.study;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deverytime.model.MemberDto;
import com.deverytime.model.StudyDto;
import com.deverytime.model.StudyTodoDto;

@WebServlet(value = "/study/study-edit.do")
public class EditStudy extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String seq = req.getParameter("seq");
		
		StudyService service = new StudyService();
		StudyDto dto = service.get(seq);
		int totalCountM = service.getTotalCountM(seq);
		
		req.setAttribute("dto", dto);
		req.setAttribute("totalCountM", totalCountM);
		
		req.getRequestDispatcher("/WEB-INF/views/study/study-edit.jsp").forward(req, resp);
		
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
		
		String id = auth.toString();
		
		String seq = req.getParameter("seq");
		String name = req.getParameter("name");
		String description = req.getParameter("description");
		String capacity = req.getParameter("capacity");
		
		StudyService service = new StudyService();
		
		int totalCountM = service.getTotalCountM(seq);
		
		try {
			
		    int cap = Integer.parseInt(capacity);
		    
		    if(totalCountM > cap) {
				resp.getWriter().print("<script>alert('현재 인원수 보다 적게 설정할 수 없습니다.');history.back();</script>");
				resp.getWriter().close();
				return;
			}

		    if (cap >= 5 && cap <= 20) {
		    	
		    } else {
		    	resp.getWriter().print("<script>alert('인원수는 5명에서 20명 사이여야 합니다.');history.back();</script>");
				resp.getWriter().close();
				return;
		    }

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		StudyDto dto = new StudyDto();
		
		dto.setSeq(seq);
		dto.setName(name);
		dto.setDescription(description);
		dto.setCapacity(capacity);
		
		int result = service.edit(dto);
		
		PrintWriter writer = resp.getWriter();
		
		if(result > 0) {
			writer.print("<script>");
			writer.print("alert('수정 완료!');");
			writer.print("location.href='/teamtwo/study/study-view.do?seq=" + seq + "';");
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
