package com.deverytime.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deverytime.model.AdminCommentsDto;
import com.deverytime.model.AdminReqDto;

@WebServlet("/board/request/view.do")
public class ViewRequest extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		AdminService service = new AdminService();

		String seq = req.getParameter("seq");

		AdminReqDto dto = service.getRequestDetailInfo(seq);

		// TODO 확인용, 문제없으면 삭제
		System.out.println("========== Request Detail ==========");
		System.out.println("seq             : " + dto.getSeq());
		System.out.println("title           : " + dto.getTitle());
		System.out.println("content         : " + dto.getContent());
		System.out.println("subject         : " + dto.getSubject());
		System.out.println("status          : " + dto.getStatus());
		System.out.println("regDate         : " + dto.getRegDate());
		System.out.println("memberSeq       : " + dto.getMemberSeq());
		System.out.println("nickname        : " + dto.getNickname());
		System.out.println("readCount       : " + dto.getReadCount());
		System.out.println("====================================");

		// 요청 상세 페이지 하단 댓글내용
		System.out.println("---------- Comments ----------");
		if (dto.getComments() != null && !dto.getComments().isEmpty()) {
			for (AdminCommentsDto comment : dto.getComments()) {
//				System.out.println("comment seq         : " + comment.getSeq());
				System.out.println("comment content     : " + comment.getContent());
//				System.out.println("requestBoardSeq     : " + comment.getRequestBoardSeq());
				System.out.println("------------------------------");
			}
		} else {
			System.out.println("댓글 없음");
		}
		System.out.println("====================================");
		
		req.setAttribute("dto", dto);

		req.getRequestDispatcher("/WEB-INF/views/board/request/view.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		AdminService service = new AdminService();

		String mode = req.getParameter("mode");
		String seq = req.getParameter("seq");

		// request-view.jsp 에 폼 두개 두고 mode 값에 따라 분기
		// 1. 요청 상태 변경인 경우
		// <input type="hidden" name="mode" value="status">
		if ("status".equals(mode)) {
			String status = req.getParameter("status");
			service.updateRequestStatus(seq, status);

		// 2. 댓글 달기인 경우
		// <input type="hidden" name="mode" value="comment">
		} else if ("comment".equals(mode)) {
			String content = req.getParameter("content");
			service.addRequestComment(seq, content);
		}

		resp.sendRedirect("/teamtwo/board/request/view.do?seq=" + seq);
	}
	
}
