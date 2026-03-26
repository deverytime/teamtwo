package com.deverytime.plan;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deverytime.model.MemberDto;
import com.deverytime.model.PlanDto;



@WebServlet("/plan/edit.do")
public class Edit extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PlanService service = new PlanService();
		HttpSession session = req.getSession();
		
		// 세션에서 memberDto 가져오기
		MemberDto auth = (MemberDto) session.getAttribute("authDto");
		
		// 로그인안했으면 로그인 페이지로
		if (auth == null) {
		    resp.sendRedirect("/teamtwo/user/login.do");
		    return;
		}
		// dto 에 저장된 memberSeq 가져오기
		String memberSeq = auth.getSeq();
		
		// 학습계획 seq
		String seq = req.getParameter("seq");
		
		// 학습계획 단건 조회
		PlanDto dto = service.get(seq, memberSeq);
		
		// 본인 학습계획 아닐때 처리
		resp.setContentType("text/html; charset=UTF-8");
		if (dto == null) {
			resp.getWriter().print("<script>alert('권한이 없습니다.');history.back();</script>");
			return;
		}
		
		// planDto jsp 에 담기
		req.setAttribute("dto", dto);
		
		// 학습계획 유형에 따른 화면(jsp) 분기
		if (dto.getType().equals("기간기반")) {
			req.getRequestDispatcher("/WEB-INF/views/plan/period-edit.jsp").forward(req, resp);
		} else if (dto.getType().equals("완료기반")) {
			req.getRequestDispatcher("/WEB-INF/views/plan/completion-edit.jsp").forward(req, resp);
		}  else {
		    // 비정상 데이터
		    resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
		    return;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	    req.setCharacterEncoding("UTF-8");

		PlanService service = new PlanService();
		
		// 완료기반은 endDate 없으므로 오류 안나게 처리
	    String endDateStr = req.getParameter("endDate");
	    Date endDate = null;
	    
	    if (endDateStr != null) {
	    	endDate = Date.valueOf(endDateStr);
	    }
		
		PlanDto dto = PlanDto.builder()
			.seq(req.getParameter("seq"))
			.title(req.getParameter("title"))
			.type(req.getParameter("type"))
			.subject(req.getParameter("subject"))
			.description(req.getParameter("description"))
			.progressStatus(req.getParameter("progressStatus"))
			.startDate(Date.valueOf(req.getParameter("startDate")))
			.endDate(endDate)
			.updateDate(new Date(System.currentTimeMillis()))
			.memberSeq(req.getParameter("memberSeq"))
			.build();
		
		int result = service.edit(dto);
		
		if (result == 1) {
			resp.sendRedirect(req.getContextPath() + "/plan/view.do?seq=" + dto.getSeq());
		} else {
			req.setAttribute("dto", dto); // 입력값 다시 넘김
			
			// jsp 에서 error 들어있으면 alert 나오게 처리해야 함 (js로)
			req.setAttribute("error", "수정 실패");
			
			if (dto.getType().equals("기간기반")) {
				req.getRequestDispatcher("/WEB-INF/views/plan/period-edit.jsp").forward(req, resp);
			} else {
				req.getRequestDispatcher("/WEB-INF/views/plan/completion-edit.jsp").forward(req, resp);
			}
		}
		
	}
}
