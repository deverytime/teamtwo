package com.deverytime.model;

import java.util.ArrayList;
import java.util.HashMap;

import com.deverytime.library.BasicDao;

public class PlanDao extends BasicDao {
	
	public ArrayList<PlanDto> list(int memberSeq) {
		
		try {
			String sql = "select * from plan where memberSeq = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, memberSeq);   // memberSeq가 int면
			rs = pstat.executeQuery();
			
			ArrayList<PlanDto> list = new ArrayList<PlanDto>();
			while (rs.next()) {
				
				// 레코드 1줄 > dto 1개
				PlanDto dto = PlanDto.builder()
					.seq(rs.getString("seq"))
					.title(rs.getString("title"))
					.subject(rs.getString("subject"))
					.description(rs.getString("description"))
					.type(rs.getString("type"))
					.regDate(rs.getDate("regdate"))
					.progressStatus(rs.getString("progressStatus"))
					.type(rs.getString("type"))
					.build();
				
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
	
	
	public PlanDto add() {
		try {
			String sql = "select id from member where seq = 1";
			
			rs = stat.executeQuery(sql);
			
			if (rs.next()) {
				PlanDto dto = PlanDto.builder()
					.seq(rs.getString("seq"))
					.build();
				return dto;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
	        closeAll();
		}
		
		return null;
	}
	

}




