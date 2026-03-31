package com.deverytime.record;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deverytime.model.MemberDto;
import com.deverytime.model.RecordDto;

@WebServlet("/record/edit.do")
public class Edit extends HttpServlet {

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RecordService service = new RecordService();
        HttpSession session = req.getSession();

        // 세션에서 memberDto 가져오기
        MemberDto auth = (MemberDto) session.getAttribute("authDto");

        // 로그인 안했으면 로그인 페이지로
        if (auth == null) {
            resp.sendRedirect("/teamtwo/user/login.do");
            return;
        }

        // dto 에 저장된 memberSeq 가져오기
        String memberSeq = auth.getSeq();

        // 학습기록 seq
        String seq = req.getParameter("seq");

        // 학습기록 단건 조회
        RecordDto dto = service.get(seq, memberSeq);

        // 본인 기록 아닐 때 처리
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        if (dto == null) {
            resp.getWriter().print("<script>alert('권한이 없습니다.');history.back();</script>");
            return;
        }
        
    	dto.setMinProgress(service.getLatestProgress(dto.getPlanSeq(), dto.getSeq()));

        req.setAttribute("dto", dto);

        // jsp 에 담기
        req.setAttribute("dto", dto);

        req.getRequestDispatcher("/WEB-INF/views/record/edit.jsp").forward(req, resp);
    }
	
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        
     // 세션에서 memberDto 가져오기
        MemberDto auth = (MemberDto) session.getAttribute("authDto");

        // 로그인 안했으면 로그인 페이지로
        if (auth == null) {
            resp.sendRedirect("/teamtwo/user/login.do");
            return;
        }

        // dto 에 저장된 memberSeq 가져오기
        String memberSeq = auth.getSeq();
        
        
        RecordService service = new RecordService();

        RecordDto dto = RecordDto.builder()
                .seq(req.getParameter("seq"))
                .studyDate(Date.valueOf(req.getParameter("studyDate")))
                .time(Integer.parseInt(req.getParameter("time")))
                .progress(Integer.parseInt(req.getParameter("progress")))
                .recordStatus(req.getParameter("recordStatus"))
                .content(req.getParameter("content"))
                .memo(req.getParameter("memo"))
                .updateDate(new Date(System.currentTimeMillis()))
                .planSeq(req.getParameter("planSeq"))
                .memberSeq(memberSeq)
                .build();
        
        int result = service.edit(dto);

        if (result == 1) {
            resp.sendRedirect(req.getContextPath() + "/plan/view.do?seq=" + dto.getPlanSeq());
        } else {
            req.setAttribute("dto", dto);
            req.setAttribute("error", "수정 실패");
            req.getRequestDispatcher("/WEB-INF/views/record/edit.jsp").forward(req, resp);
        }
    }

}