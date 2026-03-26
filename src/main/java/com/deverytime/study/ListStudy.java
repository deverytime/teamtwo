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
import javax.websocket.Session;

import com.deverytime.model.StudyDto;

@WebServlet(value = "/study/study-list.do")
public class ListStudy extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
		String word = req.getParameter("word");
		String search = "n"; //목록보기(n), 검색하기(y)
		
		if(word == null || word.trim().equals("")) {
			search = "n";
		} else {
			search = "y";
		}
		
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("word", word);
		map.put("search", search);
		
		
		String page = req.getParameter("page");
		
		int nowPage = 0;		//현재 페이지 번호
		int totalCount = 0; 	//총 스터디 수
		int pageSize = 8; 		//한페이지에서 보여줄 스터디 수
		int totalPage = 0;      //총 페이지 수
		int begin = 0;			//페이징 시작 위치
		int end = 0;			//페이징 끝 위치
		int n = 0; 				//페이지 바의 페이지 번호
		int loop = 0 ;			//페이지 바의 루프변수
		int blockSize = 10; 	//페이지 바의 페이지 개수
		
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
		
		
		StudyService service = new StudyService();
		
		ArrayList<StudyDto> list = new ArrayList<StudyDto>();
		
		list = service.list(map);
		
		
		totalCount = service.getTotalCount(map);
		
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
			pagebar += String.format("<a href='/teamtwo/study/study-list.do?page=%d'>[이전 %d페이지]</a>", n-1, blockSize);
		}
		
		while(!(loop > blockSize || n > totalPage)) {
			
			if(n ==  nowPage) {
				pagebar += String.format("<a href='#!' style='color: tomato;'>%d</a>", n);
			} else {
				pagebar += String.format("<a href='/teamtwo/study/study-list.do?page=%d'>%d</a>", n, n);
			}
			
			loop++;
			n++;
		}
		
		//다음 10페이지
		if(n >= totalPage) {
			pagebar += String.format("<a href='#!'>[다음 %d페이지]</a>", blockSize);
		} else {
			pagebar += String.format("<a href='/teamtwo/study/study-list.do?page=%d'>[다음 %d페이지]</a>", n, blockSize);
		}
		
		req.setAttribute("pagebar", pagebar);
		req.setAttribute("list", list);
		req.setAttribute("map", map);
	
		req.getRequestDispatcher("/WEB-INF/views/study/study-list.jsp").forward(req, resp);
		
	}
	
}
