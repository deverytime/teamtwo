package com.deverytime.model;

import java.util.ArrayList;
import java.util.HashMap;

import com.deverytime.library.BasicDao;

public class StudyScheduleDao extends BasicDao{

	public ArrayList<StudyScheduleDto> list(HashMap<String, String> map, String seq) {
		
		try {
			
			String sql = String.format("select * from (select a.*, rownum as rnum from study_schedule a where studySeq = ?) where rnum between %s and %s"
					,map.get("begin")
					,map.get("end"));
			
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
				dto.setStudySeq(rs.getString("seq"));
				
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

	public int getTotalCountSCH(HashMap<String, String> map, String seq) {
		
		try {
			
			String sql = "select count(*) as cnt from study_schedule where studySeq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			rs = pstat.executeQuery();
			
			if(rs.next()) {
				return rs.getInt("cnt");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		
		return 0;
		
	}

	public StudyScheduleDto get(String seq) {
	
		try {
			
			String sql = "select * from study_schedule where seq = ?";
			
			pstat = conn.prepareStatement(sql);
			
			pstat.setString(1, seq);
			
			rs = pstat.executeQuery();	
			
			if(rs.next()) {
				StudyScheduleDto dto = new StudyScheduleDto();
				
				dto.setSeq(rs.getString("seq"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setStartDate(rs.getString("startDate"));
				dto.setEndDate(rs.getString("endDate"));
				dto.setStudySeq(rs.getString("studySeq"));
				
				return dto;
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		
		return null;
		
	}

	public int add(StudyScheduleDto dto, String seq) {
		
		try {
			
			String sql = "insert into study_schedule (seq, title, content, startdate, enddate, studySeq) values (studyScheduleSeq.nextVal, ?, ?, ?, ?, ?)";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dto.getTitle());
			pstat.setString(2, dto.getContent());
			pstat.setString(3, dto.getStartDate());
			pstat.setString(4, dto.getEndDate());
			pstat.setString(5, seq);
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return 0;
		
	}

	public int edit(StudyScheduleDto dto) {
		
		try {
			
			String sql = "update study_schedule set title = ?, content = ?, startdate = ?, enddate = ? where seq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dto.getTitle());
			pstat.setString(2, dto.getContent());
			pstat.setString(3, dto.getStartDate());
			pstat.setString(4, dto.getEndDate());
			pstat.setString(5, dto.getSeq());
			
			System.out.println(dto.toString());
			
			int result = pstat.executeUpdate();
			System.out.println(result);
			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		
		return 0;
		
	}

	public int del(String seq) {
		
		try {
			
			String sql = "delete from study_schedule where seq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		
		return 0;
	}
		

}
