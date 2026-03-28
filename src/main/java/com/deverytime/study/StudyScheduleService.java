package com.deverytime.study;

import java.util.ArrayList;
import java.util.HashMap;

import com.deverytime.model.StudyDto;
import com.deverytime.model.StudyScheduleDao;
import com.deverytime.model.StudyScheduleDto;
import com.deverytime.model.StudyTodoDao;
import com.deverytime.model.StudyTodoDto;

public class StudyScheduleService {

	public ArrayList<StudyScheduleDto> schlist(HashMap<String, String> map, String seq) {
		
		StudyScheduleDao dao = new StudyScheduleDao();
		
		ArrayList<StudyScheduleDto> list = dao.list(map, seq);
		
		for(StudyScheduleDto dto : list) {
			
			String startDate = dto.getStartDate();
			String endDate = dto.getEndDate();
			
			startDate = startDate.substring(0, 10);
			endDate = endDate.substring(0, 10);
			
			dto.setStartDate(startDate);
			dto.setEndDate(endDate);
		}
		
		return list;
		
	
	}

	public int getTotalCountSCH(HashMap<String, String> map, String seq) {
		
		StudyScheduleDao dao = new StudyScheduleDao();
		
		return dao.getTotalCountSCH(map, seq);
		
	}

	public StudyScheduleDto get(String seq) {
		
		StudyScheduleDao dao = new StudyScheduleDao();
		
		StudyScheduleDto dto =  dao.get(seq);
		
		String startDate = dto.getStartDate();
		String endDate = dto.getEndDate();
		
		startDate = startDate.substring(0, 10);
		endDate = endDate.substring(0, 10);
		
		dto.setStartDate(startDate);
		dto.setEndDate(endDate);
		
		return dto;
		
	}

	public int add(StudyScheduleDto dto, String seq) {
		
		StudyScheduleDao dao = new StudyScheduleDao();
		
		return dao.add(dto, seq);
		
	}

	public int edit(StudyScheduleDto dto) {
	
		StudyScheduleDao dao = new StudyScheduleDao();
		
		return dao.edit(dto);
		
	}

	public int del(String seq) {
		
		StudyScheduleDao dao = new StudyScheduleDao();
		
		return dao.del(seq);
		
	}

	
}
