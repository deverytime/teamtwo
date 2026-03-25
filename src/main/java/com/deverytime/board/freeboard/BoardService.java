package com.deverytime.board.freeboard;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.deverytime.model.BoardDao;
import com.deverytime.model.BoardDto;

public class BoardService {
	
	// 게시판에서 주제(카테고리)를 보여주기 위한 매핑
	private static final Map<String, String> CATEGORY_MAP = new HashMap<>();
	static {
		CATEGORY_MAP.put("1", "잡담");
		CATEGORY_MAP.put("2", "개발");
		CATEGORY_MAP.put("3", "에러");
		CATEGORY_MAP.put("4", "인사");
		CATEGORY_MAP.put("5", "유머");
		CATEGORY_MAP.put("6", "자랑");
		CATEGORY_MAP.put("7", "정보");
		CATEGORY_MAP.put("8", "나눔");
		CATEGORY_MAP.put("9", "토론");
		CATEGORY_MAP.put("10", "공지");
	}
	
	

	public ArrayList<BoardDto> list() {

		BoardDao dao = new BoardDao();
		ArrayList<BoardDto> list = dao.list();
		
		for(BoardDto dto : list) {
			
			// 주제 한글 처리
			String categoryName = CATEGORY_MAP.get(dto.getCategory());
			
			dto.setCategory(categoryName);
			
			// 날짜 변환
			String regDate = dto.getRegDate();
			LocalDate postDate = LocalDate.parse(regDate.substring(0, 10)); //저장된 String date로
			LocalDate today = LocalDate.now();
			
			if(postDate.equals(today)) {
				// 시간처리
				dto.setRegDate(regDate.substring(11));
				
			} else {
				// 날짜처리
				dto.setRegDate(regDate.substring(0,10));
				
			}
			
		}
		
		
		return list;
	}
	
}
