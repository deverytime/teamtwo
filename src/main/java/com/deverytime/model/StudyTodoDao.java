package com.deverytime.model;

import java.util.ArrayList;
import java.util.HashMap;

import com.deverytime.library.BasicDao;

public class StudyTodoDao extends BasicDao{

	public ArrayList<StudyTodoDto> todolist(HashMap<String, String> map, String seq) {
		
		try {
			
			String sql = String.format("select * from (select a.*, rownum as rnum from todo a where studyScheduleSeq = ?) where rnum between %s and %s"
					,map.get("begin")
					,map.get("end"));
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			rs = pstat.executeQuery();
			
			ArrayList<StudyTodoDto> list = new ArrayList<StudyTodoDto>();
			
			while(rs.next()) {
				StudyTodoDto dto = new StudyTodoDto();
				
				dto.setSeq(rs.getString("seq"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setStatus(rs.getString("status"));
				dto.setStudyScheduleSeq(rs.getString("studyScheduleSeq"));
			
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

	public int getTotalCountTD(String seq) {
		
		try {
			
			String sql = "select count(*) as cnt from todo where studyScheduleSeq = ?";
			
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

	public int add(StudyTodoDto dto, String seq) {
		
		try {
			
			String sql = "insert into todo (seq, title, content, status, studyScheduleSeq) values (todoSeq.nextVal, ?, ?, default, ?)";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dto.getTitle());
			pstat.setString(2, dto.getContent());
			pstat.setString(3, seq);
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return 0;
		
	}

	public int updateStatus(String seq, String status) {
		
		try {
			
			String sql = "update todo set status = ? where seq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, status);
			pstat.setString(2, seq);
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return 0;
		
	}

	public StudyTodoDto get(String seq) {
		
		try {
			
			String sql = "select * from todo where seq = ?";
			
			pstat = conn.prepareStatement(sql);
			
			pstat.setString(1, seq);
			
			rs = pstat.executeQuery();	
			
			if(rs.next()) {
				StudyTodoDto dto = new StudyTodoDto();
				
				dto.setSeq(rs.getString("seq"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setStatus(rs.getString("status"));
				dto.setStudyScheduleSeq(rs.getString("studyScheduleSeq"));
				
				return dto;
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		
		return null;
		
	}

	public int edit(StudyTodoDto dto) {
		
		try {
			
			String sql = "update todo set title = ?, content = ?, status = ? where seq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dto.getTitle());
			pstat.setString(2, dto.getContent());
			pstat.setString(3, dto.getStatus());
			pstat.setString(4, dto.getSeq());
			
			int result = pstat.executeUpdate();
			
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
			
			String sql = "delete from todo where seq = ?";
			
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

