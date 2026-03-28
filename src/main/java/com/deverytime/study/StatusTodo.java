package com.deverytime.study;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deverytime.model.StudyTodoDao;

@WebServlet(value = "/study/todo-status.do")
public class StatusTodo extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String seq = req.getParameter("seq");
	    String status = req.getParameter("status");
	    
	    //System.out.println("전달받은 seq: " + seq); // 콘솔 확인용
	    //System.out.println("전달받은 status: " + status); // 콘솔 확인용
	    
	    StudyTodoService service = new StudyTodoService();
	    
	    // update todo set status = ? where seq = ? 실행
	    int result =  service.updateStatus(seq, status);

	    // JSON 응답 보내기
	    resp.setContentType("application/json");
	    PrintWriter out = resp.getWriter();
	    out.print("{\"result\": " + result + "}");
	    out.close();
	}
	
}
