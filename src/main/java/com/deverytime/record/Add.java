package com.deverytime.record;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deverytime.model.RecordDto;

@WebServlet("/record/add.do")
public class Add extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String planSeq = req.getParameter("seq");
		
		req.setAttribute("planSeq", planSeq);

		req.getRequestDispatcher("/WEB-INF/views/record/add.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		RecordService service = new RecordService();
	    
		String progressStr = req.getParameter("progress");
		String timeStr = req.getParameter("time");
		
		RecordDto dto = RecordDto.builder()
			    .studyDate(Date.valueOf(req.getParameter("studyDate")))
			    .content(req.getParameter("content"))
			    .time(timeStr != null ? Integer.parseInt(timeStr) : 1)
			    .progress(progressStr != null ? Integer.parseInt(progressStr) : 0)
			    .memo(req.getParameter("memo"))
			    .recordStatus(req.getParameter("recordStatus"))
			    .planSeq(req.getParameter("planSeq"))
			    .build();

	    int result = service.add(dto);
	    
	    if (result == 1) {
	        resp.sendRedirect("/teamtwo/plan/view.do?seq=" + dto.getPlanSeq());
	    } else {
	        req.setAttribute("error", "등록 실패");
	        req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
	    }
	}
}