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

@WebServlet(value = "/study/study-reg.do")
public class RegStudy extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
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
		
		//로그인 상태라면 데이터 추출
		String id = auth.toString();
		String seq = req.getParameter("seq");
		//서비스 호출
		StudyService service = new StudyService();
		
		StudyDto dto = service.get(seq);
		
		MemberDto mdto = service.getMember(id);
		
		int result = service.isMember(mdto, dto);
		
		if(result > 0) {
			resp.getWriter().print("<script>alert('이미 참여하고 있는 스터디 입니다.');history.back();</script>");
			resp.getWriter().close();
			return;
		}
		
		if(dto.getStatus().equals("1")) {
			resp.getWriter().print("<script>alert('모집완료된 스터디 입니다.');history.back();</script>");
			resp.getWriter().close();
			return;
		}
		
		req.setAttribute(seq, "seq");
		
		//스터디 등록
		result = service.regStudy(dto, mdto);
		
		if(result > 0) {
			resp.getWriter().print("<script>alert('참여 성공!');location.href='/teamtwo/study/study-view.do?seq=" + seq + "';</script>");
			resp.getWriter().close();
		} else {
			resp.getWriter().print("<script>alert('참여 실패!');history.back();</script>");
			resp.getWriter().close();
		}
		
	}
	
}
