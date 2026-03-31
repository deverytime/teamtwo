package com.deverytime.board;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// import com.deverytime.model.BoardDao;
// import com.deverytime.model.BoardDto;
import com.deverytime.model.StudyDao;
import com.deverytime.model.StudyDto;

@WebServlet(value = "/index.do")
public class Index extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// BoardDao boardDao = new BoardDao();
		StudyDao studyDao = new StudyDao();
		
		// 1. 데이터 가져오기 (개수 지정)
		ArrayList<StudyDto> studyList = studyDao.getLatestStudies(8);
		// ArrayList<BoardDto> trendingList = boardDao.getTrendingList(5);
		// ArrayList<BoardDto> freeboardList = boardDao.getLatestFreeboardList(5);
		
		// 2. JSP로 데이터 넘겨주기 (택배 싣기)
		req.setAttribute("studyList", studyList);
		// req.setAttribute("trendingList", trendingList);
		// req.setAttribute("freeboardList", freeboardList);
		
		req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
	}
}