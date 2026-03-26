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
				where = String.format("where %s like '%%%s%%'"
						, map.get("word"));
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
				
				dto.setScheduleCount(rs.getString("scheduleCount"));
				dto.setHeadCount(rs.getString("headCount"));
				
				
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

	public int getTotalCount(HashMap<String, String> map) {
		
		try {
			
			String where = "";
			
			if(map.get("search").equals("y")) {
				where = String.format("where name like '%%%s%%'", map.get("word"));
			}
			
			String sql = "select count(*) as cnt from study " + where;
			
			rs = stat.executeQuery(sql);
			
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

	public StudyDto get(String seq) {
		
		try {
			
			String sql = "select * from vwStudy where seq = ?";
			
			pstat = conn.prepareStatement(sql);
			
			pstat.setString(1, seq);
			
			rs = pstat.executeQuery();
			
			StudyDto dto = new StudyDto();
			
			
			if(rs.next()) {
				
				dto.setSeq(rs.getString("seq"));
				dto.setName(rs.getString("name"));
				dto.setDescription(rs.getString("description"));
				dto.setCapacity(rs.getString("capacity"));
				dto.setStatus(rs.getString("status"));
				dto.setCreateDate(rs.getString("createDate"));
				
				dto.setScheduleCount(rs.getString("scheduleCount"));
				dto.setHeadCount(rs.getString("headCount"));
				
			}
			
			return dto;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		
		return null;
		
	}

	public ArrayList<MemberDto> memberlist(String seq, HashMap<String, String> map) {
		
		try {
			
			String sql = String.format("select * from (select a.*, rownum as rnum from vwStudyMember a where studySeq = ?) where rnum between %s and %s"
					,map.get("begin")
					,map.get("end"));
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			rs = pstat.executeQuery();
			
			ArrayList<MemberDto> list = new ArrayList<MemberDto>();
			
			while(rs.next()) {
				MemberDto dto = new MemberDto();
				
				dto.setSeq(rs.getString("seq"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setEmail(rs.getString("email"));
				dto.setRegdate(rs.getString("regdate"));
				
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

	public int getTotalCountM(String seq) {
		
		try {
			
			String sql = "select count(*) as cnt from vwStudyMember where studySeq = ?";
			
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

	public int add(StudyDto dto, MemberDto mdto) {
		
		int result = 0;
		
		try {
			
			conn.setAutoCommit(false);
			
			String sql = "insert into study (seq, name, description, capacity, status, createDate) values (studySeq.nextVal, ?, ?, ?, default, default)";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dto.getName());
			pstat.setString(2, dto.getDescription());
			pstat.setString(3, dto.getCapacity());
			int result1 = pstat.executeUpdate();
			
			sql = "insert into study_member (seq, memberseq, studyseq, regdate, type) values (studymemberSeq.nextVal, ?, studySeq.currVal, default, 1)";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, mdto.getSeq());
			int result2 = pstat.executeUpdate();
			
			if (result1 == 1 && result2 == 1) {
	            conn.commit();
	            result = 1;
	        } else {
	            conn.rollback();
	            result = 0;
	        }
			
			return result;
			
			
		} catch (Exception e) {
			try {
	            if (conn != null) conn.rollback();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        e.printStackTrace();
	    } finally {
	        try {
	            conn.setAutoCommit(true);
	        } catch (Exception e) {}
	        closeAll();
	    }
		
		return result;
	}

	public MemberDto getMember(String id) {
		
		try {
	
			String sql = "select * from Member where id = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, id);
			rs = pstat.executeQuery();
			
			if(rs.next()) {
				MemberDto dto = new MemberDto();
				dto.setSeq(rs.getString("seq"));
				
				return dto;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		
		return null;
		
	}

	public ArrayList<StudyDto> mylist(HashMap<String, String> map, MemberDto mdto) {
		
		try {
			
			String where = "";
			
			if(map.get("search").equals("y")) {
				where = String.format("where %s like '%%%s%%'"
						, map.get("word"));
			}
			
			String sql = "";
			
			sql = String.format("select * from (select a.*, rownum as rnum from vwStudyByMseq a where memberSeq = ?) where rnum between %s and %s"
							,map.get("begin")
							,map.get("end"));
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, mdto.getSeq());
			rs = pstat.executeQuery();
			
			ArrayList<StudyDto> list = new ArrayList<StudyDto>();
			
			while(rs.next()) {
				StudyDto dto = new StudyDto();
				
				dto.setSeq(rs.getString("seq"));
				dto.setName(rs.getString("name"));
				dto.setDescription(rs.getString("description"));
				dto.setCapacity(rs.getString("capacity"));
				dto.setStatus(rs.getString("status"));
				dto.setCreateDate(rs.getString("createDate"));
				
				dto.setScheduleCount(rs.getString("scheduleCount"));
				dto.setHeadCount(rs.getString("headCount"));
				
				
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

	public int regStudy(StudyDto dto, MemberDto mdto) {
		
		try {
			
			String sql = "INSERT INTO study_member (seq, memberSeq, studySeq, regdate, type) "
					+ "SELECT studymemberSeq.nextVal, ?, ?, sysdate, 0 FROM dual "
					+ "WHERE NOT EXISTS (SELECT 1 FROM study_member WHERE memberSeq = ? AND studySeq = ?)";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, mdto.getSeq());
			pstat.setString(2, dto.getSeq());
			pstat.setString(3, mdto.getSeq());
			pstat.setString(4, dto.getSeq());
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		
		return 0;
		
	}

	public ArrayList<MemberDto> getStudyMember(StudyDto dto) {
		
		try {
			
			String sql = "select * from study_member where studySeq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dto.getSeq());
			rs = pstat.executeQuery();
			
			ArrayList<MemberDto> list = new ArrayList<MemberDto>();
			
			while(rs.next()) {
				MemberDto mdto = new MemberDto();
				mdto.setSeq(rs.getString("memberSeq"));
				
				list.add(mdto);
			}
			
			return list;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		
		return null;
		
	}

	public int unregStudy(MemberDto dto) {
		
		try {
			
			String sql = "delete from study_member where memberseq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dto.getSeq());
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		
		return 0;
	}

	
	
}
