package com.deverytime.study;

import java.util.ArrayList;
import java.util.HashMap;

import com.deverytime.model.StudyTodoDao;
import com.deverytime.model.StudyTodoDto;

public class StudyTodoService {

	public ArrayList<StudyTodoDto> todoList(String seq, HashMap<String, String> map) {
		
		StudyTodoDao dao = new StudyTodoDao();
		
		return dao.todolist(map, seq);
		
	}

	public int getTotalCountTD(String seq) {
		
		StudyTodoDao dao = new StudyTodoDao();
		
		return dao.getTotalCountTD(seq);
		
	}

	public int add(StudyTodoDto dto, String seq) {
		
		StudyTodoDao dao = new StudyTodoDao();
		
		return dao.add(dto, seq);
	}
	
}
