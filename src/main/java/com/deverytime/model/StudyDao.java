package com.deverytime.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.deverytime.library.BasicDao;

public class StudyDao extends BasicDao{

	public ArrayList<StudyDto> list(HashMap<String, String> map) {
		
		try {
			
			String where = "";
			
			if(map.get("search").equals("y")) {
				
			}
			
			String sql = "";
			
			sql = String.format("select * from (select a.*, rownum as rnum from vwStudy a %s) where rnum between %s and %s"
							,where
							,map.get("begin")
							,map.get("end"));
			
			rs = stat.executeQuery(sql);
			
			ArrayList<StudyDto> list = new ArrayList<StudyDto>();
			
			while(rs.next()) {
				StudyDto dto = new StudyDto();
				
				dto.setSeq(rs.getString("seq"));
				dto.setName(rs.getString("name"));
				dto.setDescription(rs.getString("description"));
				dto.setCapacity(rs.getString("capacity"));
				dto.setStatus(rs.getString("status"));
				dto.setCreateDate(rs.getString("createDate"));
				
				list.add(dto);
			}
			
			return list;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			closeAll();
			
		}
		
		return null;
		
	}

	
	
}
