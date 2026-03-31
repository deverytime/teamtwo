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
		String auth = (String) session.getAttribute("auth");

		// 1. 익명 사용자 차단 (기본 로직 유지)
		if (auth == null) {
			if (req.getRequestURI().endsWith("add.do") || req.getRequestURI().endsWith("edit.do")
					|| req.getRequestURI().endsWith("del.do")) {
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().print("<script>alert('정상경로로 들어와 로그인 해주세요.'); location.href='/teamtwo/index.do';</script>");
				return; // 필터 종료
			}
		} else {
			// 2. 수정/삭제 시 본인 확인 로직
			if (req.getRequestURI().endsWith("edit.do") || req.getRequestURI().endsWith("del.do")) {

				String seq = req.getParameter("seq");

				// MultipartRequest 요청인 경우 seq가 null로 들어옴
				if (seq != null) {
					BoardDao dao = new BoardDao();
					BoardDto dto = dao.get(seq);

					// dto가 정상적으로 조회되었을 때만 본인 체크 수행
					if (dto != null) {
						if (!auth.equals(dto.getId())) {
							response.setContentType("text/html; charset=UTF-8");
							response.getWriter().print("<script>alert('정상경로로 들어온 사용자만 글을 수정하거나 삭제할 수 있습니다.'); location.href='/teamtwo/index.do';</script>");
							return; // 필터 종료
						}
					}
				}
				// seq가 null이거나 dto가 null인 경우는 필터를 통과시킴 (Servlet에서 처리 예정)
			}
		}

		chain.doFilter(request, response);

	}
}
