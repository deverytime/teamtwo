package com.deverytime.study;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deverytime.model.MemberDto;
import com.deverytime.model.StudyDto;

@WebServlet(value = "/study/study-add.do")
public class AddStudy extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.getRequestDispatcher("/WEB-INF/views/study/study-add.jsp").forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		
		HttpSession session = req.getSession();
		
		String id = session.getAttribute("auth").toString();
		
		String name = req.getParameter("name");
		String description =  req.getParameter("description");
		String capacity =  req.getParameter("capacity");
		
		try {
			
		    int cap = Integer.parseInt(capacity);

		    if (cap >= 5 && cap <= 20) {
		    	
		    } else {
		    	resp.getWriter().print("<script>alert('failed');history.back();</script>");
				resp.getWriter().close();
		    }

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		StudyService service = new StudyService();
		
		MemberDto mdto = service.getMember(id);
		
		StudyDto dto = new StudyDto();
		
		dto.setName(name);
		dto.setDescription(description);
		dto.setCapacity(capacity);
		
		int result = service.add(dto, mdto);
		
		if(result > 0) {
			resp.sendRedirect("/teamtwo/study/study-list.do");
		} else {
			resp.getWriter().print("<script>alert('failed');history.back();</script>");
			resp.getWriter().close();
		}
		
		
	}
	
}
