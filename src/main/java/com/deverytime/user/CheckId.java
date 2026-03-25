package com.deverytime.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.deverytime.model.MemberDao;

@WebServlet("/user/checkid.do")
public class CheckId extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		
		MemberDao dao = new MemberDao();
		int result = dao.checkId(id); // 1 or 0
		
		// Ajax 응답 설정 (JSON 형태)
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().print("{\"result\": " + result + "}");
		resp.getWriter().close();
	}
}