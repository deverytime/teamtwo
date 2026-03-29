package com.deverytime.study;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deverytime.model.MemberDto;
import com.deverytime.model.StudyDto;
import com.deverytime.model.StudyScheduleDto;

@WebServlet(value = "/study/studyschedule-list.do")
public class ListStudySchedule extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = req.getSession();
		
		Object auth = session.getAttribute("authDto");
		
		//로그인 체크 로직
		if(auth == null) {
			resp.getWriter().print("<script>alert('로그인이 필요한 서비스입니다.');history.back();</script>");
			resp.getWriter().close();
			return;
		}
		
		//로그인 상태라면 데이터 추출
		MemberDto mdto = (MemberDto)auth;
		String seq = req.getParameter("seq");
		
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		String page = req.getParameter("page");
		
		int nowPage = 0;		//현재 페이지 번호
		int totalCount = 0; 	//총 스터디 일정 수
		int pageSize = 5; 		//한페이지에서 보여줄 스터디 일정 수
		int totalPage = 0;      //총 페이지 수
		int begin = 0;			//페이징 시작 위치
		int end = 0;			//페이징 끝 위치
		int n = 0; 				//페이지 바의 페이지 번호
		int loop = 0 ;			//페이지 바의 루프변수
		int blockSize = 5; 	//페이지 바의 페이지 개수
		
		if(page == null || Objects.equals(page, "")) {
			nowPage = 1;
		} else {
			nowPage = Integer.parseInt(page);
		}
		
		begin = ((nowPage - 1) * pageSize) + 1;
		end = begin + pageSize - 1;
		
		
		map.put("begin", begin + "");
		map.put("end", end + "");
		map.put("nowPage", nowPage + "");
		
		
		StudyScheduleService service = new StudyScheduleService();
		
		ArrayList<StudyScheduleDto> schlist = new ArrayList<StudyScheduleDto>();
		
		schlist = service.schlist(map, seq);
	
		totalCount = service.getTotalCountSCH(map, seq);
		
		totalPage = (int)Math.ceil((double)totalCount / pageSize); 
		
		map.put("totalCount", totalCount + "");
		map.put("totalPage", totalPage + "");
		
		String pagebar = "";
		
		loop = 1; //루프 변수
		n = ((nowPage - 1) / blockSize) * blockSize + 1; //시작 페이지 번호
		
		//이전 10페이지
		if(n == 1) {
			pagebar += String.format("<a href='#!'>[이전 %d페이지]</a>", blockSize);
		} else {
			pagebar += String.format("<a href='/teamtwo/study/studyschedule-list.do?page=%d'>[이전 %d페이지]</a>", n-1, blockSize);
		}
		
		while(!(loop > blockSize || n > totalPage)) {
			
			if(n ==  nowPage) {
				pagebar += String.format("<a href='#!' style='color: tomato;'>%d</a>", n);
			} else {
				pagebar += String.format("<a href='/teamtwo/study/studyschedule-list.do?page=%d&seq=" + seq + "'>%d</a>", n, n);
			}
			
			loop++;
			n++;
		}
		
		//다음 10페이지
		if(n >= totalPage) {
			pagebar += String.format("<a href='#!'>[다음 %d페이지]</a>", blockSize);
		} else {
			pagebar += String.format("<a href='/teamtwo/study/studyschedule-list.do?page=%d'>[다음 %d페이지]</a>", n, blockSize);
		}	
		
		//달력을 위한 페이징 처리가 안된 list
		ArrayList<StudyScheduleDto> calendarList = service.clist(seq);
		
		req.setAttribute("pagebar", pagebar);
		req.setAttribute("schlist", schlist);
		req.setAttribute("calendarList", calendarList);
		req.setAttribute("map", map);
		req.setAttribute("seq", seq);
		
		req.getRequestDispatcher("/WEB-INF/views/study/studyschedule-list.jsp").forward(req, resp);
		
	}
	
}
