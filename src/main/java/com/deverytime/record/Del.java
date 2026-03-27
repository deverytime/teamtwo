package com.deverytime.record;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deverytime.model.MemberDto;
import com.deverytime.model.RecordDto;

@WebServlet("/record/del.do")
public class Del extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		
		HttpSession session = req.getSession();
		RecordService service = new RecordService();
		
		// 삭제할 학습계획 번호
		String seq = req.getParameter("seq");
		
		// 세션에서 memberDto 가져오기
		MemberDto auth = (MemberDto) session.getAttribute("authDto");
		
		// 로그인안했으면 로그인 페이지로
		if (auth == null) {
		    resp.sendRedirect("/teamtwo/user/login.do");
		    return;
		}
		// dto 에 저장된 memberSeq 가져오기
		String memberSeq = auth.getSeq();

		// 방어 코드
		if (seq == null || seq.trim().equals("")) {
			resp.setContentType("text/html; charset=UTF-8");
			resp.getWriter().print("<script>alert('잘못된 요청입니다.'); location.href='/teamtwo/plan/list.do';</script>");
			resp.getWriter().close();
			return;
		}

		RecordDto dto = RecordDto.builder()
			.seq(seq)
			.memberSeq(memberSeq)
			.build();
		
		// 삭제 실행 + planSeq 받기
		String planSeq = service.del(dto);

		resp.setContentType("text/html; charset=UTF-8");

		if (planSeq != null) {
		    resp.getWriter().print(
		        "<script>alert('학습기록을 삭제했습니다.'); "
		      + "location.href='/teamtwo/plan/view.do?seq=" + planSeq + "';</script>"
		    );
		} else {
		    resp.getWriter().print(
		        "<script>alert('삭제에 실패했습니다.'); "
		      + "location.href='/teamtwo/record/view.do?seq=" + seq + "';</script>"
		    );
		}
		
//		
//		// 삭제 실행
//		int result = service.del(dto);
//
//		resp.setContentType("text/html; charset=UTF-8");
//
//		if (result == 1) {
//			resp.getWriter().print("<script>alert('학습기록을 삭제했습니다.'); location.href='/teamtwo/plan/list.do';</script>");
//		} else {
//			resp.getWriter().print("<script>alert('삭제에 실패했습니다.'); location.href='/teamtwo/record/view.do?seq=" + seq + "';</script>");
//		}
//
//		resp.getWriter().close();
	}
}