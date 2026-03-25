// 닉네임 중복 확인
// 작성자: 황윤재(26.03.25)
package com.deverytime.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.deverytime.model.MemberDao;

@WebServlet("/user/checknick.do")
public class CheckNick extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nickname = req.getParameter("nickname");
		
		MemberDao dao = new MemberDao();
		int result = dao.checkNick(nickname);
		
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().print("{\"result\": " + result + "}");
		resp.getWriter().close();
	}
}