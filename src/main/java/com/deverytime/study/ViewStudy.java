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

@WebServlet(value = "/study/study-view.do")
public class ViewStudy extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				
		HttpSession session = req.getSession();
		
		Object auth = session.getAttribute("authDto");
		
		MemberDto mdto = null;
		
		if(auth != null) {
			mdto = (MemberDto)auth;
		}
		
		String seq = req.getParameter("seq");
		
		StudyService service = new StudyService();
			
		StudyDto dto = service.get(seq);
		
		ArrayList<MemberDto> mlist = new ArrayList<MemberDto>();	
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		String page = req.getParameter("page");
		
		int nowPage = 0;		//현재 페이지 번호
		int totalCount = 0; 	//총 스터디 수
		int pageSize = 5; 		//한페이지에서 보여줄 스터디 수
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
		
		//해당 스터디에 참가중인 회원들 리스트
		mlist = service.memberList(seq, map);
		
		totalCount = service.getTotalCountM(seq);
		
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
			pagebar += String.format("<a href='/teamtwo/study/study-view.do?seq=%s&page=%d'>[이전 %d페이지]</a>", seq, n-1, blockSize);
		}
		
		while(!(loop > blockSize || n > totalPage)) {
			
			if(n ==  nowPage) {
				pagebar += String.format("<a href='#!' style='color: tomato;'>%d</a>", n);
			} else {
				pagebar += String.format("<a href='/teamtwo/study/study-view.do?seq=%s&page=%d'>%d</a>", seq, n, n);
			}
			
			loop++;
			n++;
		}
		
		//다음 10페이지
		if(n >= totalPage) {
			pagebar += String.format("<a href='#!'>[다음 %d페이지]</a>", blockSize);
		} else {
			pagebar += String.format("<a href='/teamtwo/study/study-view.do?seq=%s&page=%d'>[다음 %d페이지]</a>", seq, n, blockSize);
		}
		
		int result = 0;
		boolean isManager = false;
		boolean isMember = false;
		
		if(auth != null) {
			//이 회원이 이 스터디의 스터디장인지?
			result = service.isManager(mdto, dto); //맞다면 1반환
			
			if(result > 0) {
				isManager = true;
			} else {
				isManager = false;
			}
			
			//이 회원이 이 스터디의 멤버인지?(스터디장인 경우도 포함됨)
			result = service.isMember(mdto, dto);

			if(result > 0) {
				isMember = true;
			} else {
				isMember = false;
			}
			
		}
		
		
		req.setAttribute("totalCount", totalCount);
		req.setAttribute("pagebar", pagebar);
		req.setAttribute("dto", dto);
		req.setAttribute("mlist", mlist);
		req.setAttribute("isManager", isManager);
		req.setAttribute("isMember", isMember);
		
		req.getRequestDispatcher("/WEB-INF/views/study/study-view.jsp").forward(req, resp);
		
	}
	
}
