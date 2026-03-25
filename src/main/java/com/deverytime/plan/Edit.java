package com.deverytime.plan;

import java.io.IOException;

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
		if (dto == null) {
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
}
