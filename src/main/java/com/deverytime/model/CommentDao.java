package com.deverytime.model;

import java.util.ArrayList;

import com.deverytime.library.BasicDao;

public class CommentDao extends BasicDao{

	public ArrayList<CommentDto> listByPostSeq(String seq) {
		
		try {
			
			String sql = "select * from vwComments where postSeq = ? order by regDate asc";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);//글번호
			
			rs = pstat.executeQuery();
			
			ArrayList<CommentDto> list = new ArrayList<>();
			
			while(rs.next()) {
				CommentDto dto = new CommentDto();
				dto.setContent(rs.getString("content"));
				dto.setNickname(rs.getString("nickname"));
				dto.setPostSeq(rs.getString("postSeq")); //여긴 댓글의 글번호
				dto.setRegDate(rs.getString("regDate"));
				dto.setSeq(rs.getString("seq")); //댓글번호
				dto.setMemberSeq(rs.getString("memberSeq"));
				dto.setId(rs.getString("id"));
				
				list.add(dto);
			}
			
			return list;
		
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		
		return new ArrayList<>();
	}

}
