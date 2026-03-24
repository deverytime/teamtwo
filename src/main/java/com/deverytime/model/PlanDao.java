package com.deverytime.model;

import com.deverytime.library.BasicDao;

public class PlanDao extends BasicDao {
	
	public PlanDto add() {
		try {
			String sql = "select id from member where seq = 1";
			
			rs = stat.executeQuery(sql);
			
			if (rs.next()) {
				PlanDto dto = PlanDto.builder()
					.id(rs.getString("id"))
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




