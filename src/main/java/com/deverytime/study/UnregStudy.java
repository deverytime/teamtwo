package com.deverytime.study;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deverytime.model.MemberDto;

@WebServlet(value = "/study/study-unreg.do")
public class UnregStudy extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
		
        String seq = req.getParameter("seq");
        
		HttpSession session = req.getSession();
		
		Object auth = session.getAttribute("authDto");
			
		if(auth == null) {
			resp.getWriter().print("<script>alert('로그인이 필요한 서비스입니다.');history.back();</script>");
			resp.getWriter().close();
			return;
		}
		
		StudyService service = new StudyService();
		
		MemberDto mdto = (MemberDto)auth;
		
		int result = service.unregStudy(mdto, seq);
		
		if(result > 0) {
			resp.getWriter().print("<script>alert('스터디 탈퇴 완료');location.href='/teamtwo/study/study-list.do';</script>");
			resp.getWriter().close();
		} else {
			resp.getWriter().print("<script>alert('failed');history.back();</script>");
			resp.getWriter().close();
		}
		
	}
	
}
