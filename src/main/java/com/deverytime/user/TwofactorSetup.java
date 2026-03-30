package com.deverytime.user;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deverytime.model.MemberDao;
import com.deverytime.model.MemberDto;

@WebServlet("/user/twofactor-setup.do")
public class TwofactorSetup extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();

		// 1. 진짜 세션(auth)도 없고, 임시 세션(tempAuthId)도 없으면 불법 침입으로 간주하고 내쫓음
		if (session.getAttribute("auth") == null && session.getAttribute("tempAuthId") == null) {
			resp.sendRedirect("/teamtwo/user/login.do");
			return;
		}

		// 2. 만약 로그인 과정에서 넘어온 유저(tempAuthId만 있는 상태)라면?
		if (session.getAttribute("tempAuthId") != null && session.getAttribute("auth") == null) {
			// 화면(JSP)에서 ${authDto.email}을 쓸 수 있도록, DB에서 정보를 잠깐 꺼내서 request에 담아줌
			String tempId = (String) session.getAttribute("tempAuthId");
			MemberDao dao = new MemberDao();
			MemberDto tempDto = dao.getMember(tempId);
			req.setAttribute("authDto", tempDto);
		}

		req.getRequestDispatcher("/WEB-INF/views/user/twofactor-setup.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json; charset=UTF-8");
		PrintWriter out = resp.getWriter();

		String action = req.getParameter("action");
		// 서비스 객체 호출
		MemberService service = new MemberService();

		if ("sendAuth".equals(action)) {
			// 1. 이메일 발송
			String code = service.sendTwoFactorAuthMail(req);

			if (code != null) {
				// 6자리 인증번호를 프론트엔드로 보내지 않음, 서버 세션에 숨김
				req.getSession().setAttribute("serverTwoFactorCode", code);
				out.print("{\"result\": 1}");
			} else {
				out.print("{\"result\": 0}");
			}

		} else if ("verify".equals(action)) {
			// ==========================================
			// 프론트에서 넘어온 인증번호 채점
			// ==========================================
			String inputCode = req.getParameter("code");
			String mode = req.getParameter("mode");
			String serverCode = (String) req.getSession().getAttribute("serverTwoFactorCode");

			// 정답이 일치하는가?
			if (serverCode != null && serverCode.equals(inputCode)) {

				// 만약 로그인하려고 2차 인증을 한 거라면 -> 임시 세션을 진짜 세션으로 승급
				if ("login".equals(mode)) {
					String tempId = (String) req.getSession().getAttribute("tempAuthId");
					if (tempId != null) {
						MemberDao dao = new MemberDao();
						MemberDto mdto = dao.getMember(tempId);

						req.getSession().setAttribute("auth", mdto.getId());
						req.getSession().setAttribute("authDto", mdto);
						req.getSession().removeAttribute("tempAuthId"); // 임시 출입증 폐기
					}
				}

				req.getSession().removeAttribute("serverTwoFactorCode"); // 다 쓴 정답지 폐기
				out.print("{\"result\": 1}"); // 정답일 경우
			} else {
				out.print("{\"result\": 0}"); // 틀렸을 경우
			}

		} else if ("enable".equals(action)) {
			// 2차 인증 활성화
			int result = service.toggleTwoFactor(req, 1);
			out.print("{\"result\": " + result + "}");
		}

		out.close();
	}
}