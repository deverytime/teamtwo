package com.deverytime.plan;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deverytime.model.PlanDto;



@WebServlet("/plan/edit.do")
public class Edit extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PlanService service = new PlanService();
		
		// TODO 로그인 연동 시 삭제
		// 원래는 session의 authDto 에서 memberSeq 가져와야 함
		String memberSeq = "4";
		
		// 학습계획 seq
		String seq = req.getParameter("seq");
		
		// 학습계획 단건 조회
		PlanDto dto = service.get(seq, memberSeq);
		
		// 본인 학습계획 아닐때 처리
		if (dto == null || !dto.getMemberSeq().equals(memberSeq)) {
			resp.getWriter().print("<script>alert('권한이 없습니다.');history.back();</script>");
			resp.getWriter().close();
			return;
		}
				
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
			.startDate(Date.valueOf(req.getParameter("startDate")))
			.endDate(endDate)
			.updateDate(new Date(System.currentTimeMillis()))
			.memberSeq(req.getParameter("memberSeq"))
			.build();
		
		int result = service.edit(dto);
		
		if (result == 1) {
			// TODO 임시 (나중에상세페이지로 가게 바꿔야 함)
			resp.sendRedirect("/plan/view.do?seq=" + dto.getSeq());
//			resp.sendRedirect("/plan/list.do");
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
