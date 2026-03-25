package com.deverytime.study;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.deverytime.model.StudyDao;
import com.deverytime.model.StudyDto;

public class StudyService {
	
	public ArrayList<StudyDto> list(HashMap<String, String> map){
		
		StudyDao dao = new StudyDao();
		
		ArrayList<StudyDto> list = dao.list(map);
		
		for(StudyDto dto : list) {
			
			String createDate = dto.getCreateDate();
			
			createDate = createDate.substring(0, 10);
			
			dto.setCreateDate(createDate);
			
			String name = dto.getName();
			
			String desc = dto.getDescription();
			
			name = name.replace("<", "&lt;").replace(">", "&rt;");
			
			if(name.length() > 15) {
				name = name.substring(0, 15) + "...";
			}
			
			dto.setName(name);
			
			if(desc.length() > 30) {
				desc = desc.substring(0, 30) + "...";
			}
			
			dto.setDescription(desc);
			
		}
		
		
		return list;
		
	}

	public int getTotalCount(HashMap<String, String> map) {
		
		StudyDao dao = new StudyDao();
		
		return dao.getTotalCount(map);
		
	}
	
}
