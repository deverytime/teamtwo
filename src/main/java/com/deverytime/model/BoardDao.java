package com.deverytime.model;

import java.util.ArrayList;

import com.deverytime.library.BasicDao;

public class BoardDao extends BasicDao {

	public ArrayList<BoardDto> list() {
		
		try {
			
			String sql = "select * from vwPost";
			
			rs = stat.executeQuery(sql);
			
			ArrayList<BoardDto> list = new ArrayList<>();
			
			
			while(rs.next()) {
				BoardDto dto = new BoardDto();
				dto.setSeq(rs.getString("seq"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setReadCount(rs.getString("readCount"));
				dto.setRegDate(rs.getString("regDate"));
				dto.setRecommend(rs.getString("recommend"));
				dto.setReport(rs.getString("report"));
				dto.setCategory(rs.getString("category"));
				dto.setNickname(rs.getString("nickname"));
				dto.setBoardType(rs.getString("boardType"));
				
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
