package com.deverytime.record;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deverytime.model.MemberDto;
import com.deverytime.model.RecordDto;

@WebServlet("/record/view.do")
public class View extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        RecordService service = new RecordService();

        MemberDto auth = (MemberDto) session.getAttribute("authDto");
        if (auth == null) {
            resp.sendRedirect("/teamtwo/user/login.do");
            return;
        }

        String seq = req.getParameter("seq");
        String memberSeq = auth.getSeq();

        RecordDto dto = service.getDetailInfo(seq, memberSeq);

        if (dto == null) {
            resp.getWriter().print("<script>alert('권한이 없습니다.');history.back();</script>");
            resp.getWriter().close();
            return;
        }

        req.setAttribute("dto", dto);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/record/view.jsp");
        dispatcher.forward(req, resp);
    }
}