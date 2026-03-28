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

	public int updateStatus(String seq, String status) {
		
		StudyTodoDao dao = new StudyTodoDao();
		
		return dao.updateStatus(seq, status);
	}

	public StudyTodoDto get(String seq) {
		
		StudyTodoDao dao = new StudyTodoDao();
		
		return dao.get(seq);
	}

	public int edit(StudyTodoDto dto) {
		
		StudyTodoDao dao = new StudyTodoDao();
		
		return dao.edit(dto);
	}
	
}
