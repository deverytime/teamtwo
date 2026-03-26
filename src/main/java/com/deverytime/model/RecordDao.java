package com.deverytime.model;

import java.util.ArrayList;
import java.util.List;

import com.deverytime.library.BasicDao;

public class RecordDao extends BasicDao{

	public List<RecordDto> getRecordsByPlan(String planSeq, int cnt) {
		
		try {
			String sql = "select * from ("
	                   + "    select * from record"
	                   + "    where planSeq = ?"
	                   + "    order by seq desc"
	                   + ") where rownum <= ?";
			
			pstat = conn.prepareStatement(sql);
	        pstat.setString(1, planSeq);
	        pstat.setInt(2, cnt);

	        rs = pstat.executeQuery();

	        List<RecordDto> list = new ArrayList<>();

	        while (rs.next()) {
	            RecordDto dto = RecordDto.builder()
	                .seq(rs.getString("seq"))
	                .studyDate(rs.getDate("studyDate"))
	                .content(rs.getString("content"))
	                .progress(rs.getInt("progress"))
	                .memo(rs.getString("memo"))
	                .recordStatus(rs.getString("recordStatus"))
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
	
}
