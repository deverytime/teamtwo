package com.deverytime.model;

import java.util.ArrayList;

import com.deverytime.library.BasicDao;

public class StudyScheduleDao extends BasicDao{

	public ArrayList<StudyScheduleDto> scheduleList(String seq) {
		
		try {
			
			String sql = "select * from study_schedule where studySeq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			rs = pstat.executeQuery();
			
			ArrayList<StudyScheduleDto> list = new ArrayList<StudyScheduleDto>();
			
			while(rs.next()) {
				
				StudyScheduleDto dto = new StudyScheduleDto();
				dto.setSeq(rs.getString("seq"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setStartDate(rs.getString("startDate"));
				dto.setEndDate(rs.getString("endDate"));
				dto.setStudySeq(rs.getString("studySeq"));
				
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
