package com.deverytime.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.deverytime.model.BoardDao;
import com.deverytime.model.BoardDto;

public class AuthFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		// System.out.println("인증 필터 동작");

		// 권한 체크
		// 익명 사용자 > URL 직접 접근 방지
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();

		if (session.getAttribute("auth") == null) {
			
			// System.out.println("익명사용자");
			if (req.getRequestURI().endsWith("add.do") || req.getRequestURI().endsWith("edit.do")
					|| req.getRequestURI().endsWith("del.do")) {
				response.getWriter().print("<script>alert('now allowed');history.back();</script>;");
				response.getWriter().close();
				return;
			}
		} else {
			// System.out.println("인증사용자");

			// 수정하기, 삭제하기 > 본인만
			if (req.getRequestURI().endsWith("edit.do") || req.getRequestURI().endsWith("del.do")) {

				// 현재 글 + 본인 글?
				// 1. 현재 글번호?
				String seq = req.getParameter("seq");

				BoardDao dao = new BoardDao();
				BoardDto dto = dao.get(seq);

				// dto.get(seq) > 현재 글쓴이
				// 세션 아이디
				if (!session.getAttribute("auth").equals(dto.getId())) {
					response.getWriter().print("<script>alert('now allowed');history.back();</script>;");
					response.getWriter().close();
					return;
				}

			}

		}

		chain.doFilter(request, response);

	}
}
