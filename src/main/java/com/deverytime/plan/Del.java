package com.deverytime.plan;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/plan/del.do")
public class Del extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		
		HttpSession session = req.getSession();
		PlanService service = new PlanService();
		
		// 삭제할 학습계획 번호
		String seq = req.getParameter("seq");
		
		// TODO 로그인 연동 후 수정
		// 원래는 session 의 authDto 에서 memberSeq 꺼내기
		String memberSeq = "4";

		// 방어 코드
		if (seq == null || seq.trim().equals("")) {
			resp.setContentType("text/html; charset=UTF-8");
			resp.getWriter().print("<script>alert('잘못된 요청입니다.'); location.href='/teamtwo/plan/list.do';</script>");
			resp.getWriter().close();
			return;
		}

		// 삭제 실행
		int result = service.del(seq, memberSeq);

		resp.setContentType("text/html; charset=UTF-8");

		if (result == 1) {
			resp.getWriter().print("<script>alert('학습계획을 삭제했습니다.'); location.href='/teamtwo/plan/list.do';</script>");
		} else {
			resp.getWriter().print("<script>alert('삭제에 실패했습니다.'); location.href='/teamtwo/plan/view.do?seq=" + seq + "';</script>");
		}

		resp.getWriter().close();
	}
}