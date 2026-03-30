package com.deverytime.admin;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deverytime.model.MemberDto;
import com.deverytime.model.RecordDto;
import com.deverytime.record.RecordService;

@WebServlet("/admin/board-list.do")
public class ListBoard extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		AdminService service = new AdminService();
		String page = req.getParameter("page");
		String type = req.getParameter("type");
		String word = req.getParameter("word");

		HashMap<String, Object> result = service.getBoardList(page, type, word);

		req.setAttribute("list", result.get("list"));
		req.setAttribute("pagebar", result.get("pagebar"));
		req.setAttribute("type", result.get("type"));
		req.setAttribute("word", result.get("word"));
		req.setAttribute("totalCount", result.get("totalCount"));
		
		System.out.println("list: " + (result.get("list")).toString());
		System.out.println("pagebar: " + (result.get("pagebar")).toString());
		System.out.println("type: " + result.get("type"));
		System.out.println("word: " + result.get("word"));
		System.out.println("totalCount: " + result.get("totalCount"));

		req.getRequestDispatcher("/WEB-INF/views/admin/board-list.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		
		AdminService service = new AdminService();
		
		// 삭제할 게시글 번호
		String seq = req.getParameter("seq");

		// 방어 코드
		if (seq == null || seq.trim().equals("")) {
			resp.setContentType("text/html; charset=UTF-8");
			resp.getWriter().print("<script>alert('잘못된 요청입니다.'); location.href='/teamtwo/admin/admin.do';</script>");
			resp.getWriter().close();
			return;
		}
		
		// 삭제 실행 
		int result = service.delPost(seq);

		resp.setContentType("text/html; charset=UTF-8");

		if (result == 1) {
		    resp.getWriter().print(
		        "<script>alert('게시글을 삭제했습니다.'); "
		      + "location.href='/teamtwo/admin/board-list.do';</script>"
		    );
		} else {
		    resp.getWriter().print(
		        "<script>alert('게시글 삭제에 실패했습니다.'); "
		      + "location.href='/teamtwo/admin/board-list.do';</script>"
		    );
		}
	}
}
