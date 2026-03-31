package com.deverytime.board;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deverytime.model.BoardDao;
import com.deverytime.model.BoardDto;
import com.deverytime.model.StudyDao;
import com.deverytime.model.StudyDto;

@WebServlet(value = "/index.do")
public class Index extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 1. 스터디 가져오기
		StudyDao studyDao = new StudyDao();
		ArrayList<StudyDto> studyList = studyDao.getLatestStudies(8);

		// 2. 인기글 가져오기 (문 열고 -> 가져오고 -> 문 닫힘)
		BoardDao boardDao1 = new BoardDao();
		ArrayList<BoardDto> trendingList = boardDao1.getTrendingList(4); 

		// 3. 자유게시판 가져오기 (새로운 BoardDao 객체를 만들어서 문을 새로 열어줌)
		BoardDao boardDao2 = new BoardDao();
		ArrayList<BoardDto> freeboardList = boardDao2.getLatestFreeboardList(4);

		// 4. JSP로 택배 싣기
		req.setAttribute("studyList", studyList);
		req.setAttribute("trendingList", trendingList);
		req.setAttribute("freeboardList", freeboardList);

		req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
	}
}