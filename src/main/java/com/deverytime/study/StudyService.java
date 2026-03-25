package com.deverytime.study;

import java.util.ArrayList;
import java.util.HashMap;

import com.deverytime.model.StudyDao;
import com.deverytime.model.StudyDto;

public class StudyService {
	
	public ArrayList<StudyDto> list(HashMap<String, String> map){
		
		StudyDao dao = new StudyDao();
		
		ArrayList<StudyDto> list = dao.list(map);
		
		for(StudyDto dto : list) {
			
			String name = dto.getName();
			
			name = name.replace("<", "&lt;").replace(">", "&rt;");
			
			if(name.length() > 15) {
				name = name.substring(0, 15) + "...";
			}
			
			dto.setName(name);
			
		}
		
		return list;
		
	}
	
}
