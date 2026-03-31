package com.deverytime.plan;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deverytime.model.MemberDto;
import com.deverytime.model.PlanDto;

@WebServlet("/plan/view.do")
public class View extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PlanService service = new PlanService();
		HttpSession session = req.getSession();
		String cntParam = req.getParameter("cnt");
		
		// 세션에서 memberDto 가져오기
		MemberDto auth = (MemberDto) session.getAttribute("authDto");
		
		// 로그인안했으면 로그인 페이지로
		if (auth == null) {
		    resp.sendRedirect("/teamtwo/user/login.do");
		    return;
		}
		// dto 에 저장된 memberSeq 가져오기
		String memberSeq = auth.getSeq();
		
		String seq = req.getParameter("seq");
		
		
		// 학습계획에 대한 학습기록들 cnt 만큼 가져오기
		// cnt 없는 경우 기본값 5
		int cnt = 5;
		if (cntParam != null && !cntParam.equals("")) {
		    cnt = Integer.parseInt(cntParam);
		}
		
		PlanDto dto = service.getDetailInfo(seq, memberSeq, cnt);
		resp.setCharacterEncoding("UTF-8");
		// 내 학습계획이 아니면 뒤로가기
		resp.setContentType("text/html; charset=UTF-8");
		if (dto == null) {
		    resp.getWriter().print("<script>alert('권한이 없습니다.');history.back();</script>");
		    return;
		}
		
		req.setAttribute("dto", dto);
		req.setAttribute("cnt", cnt);

		req.getRequestDispatcher("/WEB-INF/views/plan/view.jsp").forward(req, resp);
	}
}