package com.deverytime.plan;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deverytime.model.MemberDto;

@WebServlet("/plan/goal-check.do")
public class Goal extends HttpServlet {

	 @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        MemberDto auth = (MemberDto) session.getAttribute("authDto");

        if (auth == null) {
            resp.sendRedirect("/teamtwo/user/login.do");
            return;
        }

        String goalSeq = req.getParameter("goalSeq");
        String planSeq = req.getParameter("planSeq");

        PlanService service = new PlanService();
        int result = service.checkGoal(goalSeq, planSeq, auth.getSeq());

        if (result == 1) {
            resp.sendRedirect("/teamtwo/plan/view.do?seq=" + planSeq);
        } else {
        	resp.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html; charset=UTF-8");
            resp.getWriter().print("<script>alert('체크할 수 없는 목표입니다.'); location.href='/teamtwo/plan/view.do?seq=" + planSeq + "';</script>");
            resp.getWriter().close();
        }
    }
}