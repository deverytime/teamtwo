package com.deverytime.user;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deverytime.model.MemberDao;

@WebServlet("/user/info-edit.do")
public class EditInfo extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그인 상태가 아니라면 로그인 페이지로 이동
		if (req.getSession().getAttribute("auth") == null) {
			resp.sendRedirect("/teamtwo/user/login.do");
			return;
		}
		req.getRequestDispatcher("/WEB-INF/views/user/info-edit.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json; charset=UTF-8");
		java.io.PrintWriter out = resp.getWriter();

		MemberDao dao = new MemberDao();
		MemberService service = new MemberService();
		int result = 0;

		// 1. 요청 타입 확인 (파일 업로드가 포함된 multipart 요청인지 검사)
		String contentType = req.getContentType();

		if (contentType != null && contentType.startsWith("multipart/form-data")) {
			// [정보 수정 버튼 클릭 시] 파일과 함께 데이터가 넘어옴
			result = service.editInfo(req);

		} else {
			// [중복 확인 버튼 클릭 시] 단순 텍스트만 넘어옴
			String action = req.getParameter("action");
			if ("checkNick".equals(action)) {
				result = dao.checkNick(req.getParameter("nickname"));
			} else if ("checkEmail".equals(action)) {
				result = dao.checkEmail(req.getParameter("email"));
			}
		}

		out.print("{\"result\": " + result + "}");
		out.close();
	}

}