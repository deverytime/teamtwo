package com.deverytime.board.freeboard;

import java.util.ArrayList;

import com.deverytime.model.BoardDao;
import com.deverytime.model.BoardDto;

public class BoardService {

	public ArrayList<BoardDto> list() {
		
		BoardDao dao = new BoardDao();
		
		
		return dao.list();
	}
	
}
