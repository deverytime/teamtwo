package com.deverytime.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.deverytime.library.BasicDao;

public class CommentDao extends BasicDao {

	public ArrayList<CommentDto> listByPostSeq(String seq) {

		try {

			String sql = "select * from vwComments where postSeq = ? order by regDate asc";

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);// 글번호

			rs = pstat.executeQuery();

			ArrayList<CommentDto> list = new ArrayList<>();

			while (rs.next()) {
				CommentDto dto = new CommentDto();
				dto.setContent(rs.getString("content"));
				dto.setNickname(rs.getString("nickname"));
				dto.setPostSeq(rs.getString("postSeq")); // 여긴 댓글의 글번호
				dto.setRegDate(rs.getString("regDate"));
				dto.setSeq(rs.getString("seq")); // 댓글번호
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

	// id를 가지고 memberseq를 반환하는 메서드 
	// boardDao에도 있으나 rs pstat conn문제 때문에 따로 써야
	public String getMemberSeqById(String id) {

		try {

			String sql = "select seq from member where id=?";
			PreparedStatement ps = conn.prepareStatement(sql); // pstat 대신 로컬변수!

			ps.setString(1, id);
			ResultSet r = ps.executeQuery(); // rs 대신 로컬변수!

			if (r.next()) {

				String result = r.getString("seq");
				r.close();
				ps.close();
				return result;
			}
			r.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// add에서 conn을 쓸예정이니 add에서 닫아줌
//			finally {
//				closeAll();
//			}

		return null;
	}

	public int add(CommentDto cDto) {
		
		try {
			
			String sql="insert into comments (SEQ, REGDATE, CONTENT, POSTSEQ, MEMBERSEQ) values (COMMENTSSEQ.nextval, default, ?, ?, ?)";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, cDto.getContent());
			pstat.setString(2, cDto.getPostSeq());
			pstat.setString(3, cDto.getMemberSeq());
			
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
			
			String sql = "delete from comments where seq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			int result = pstat.executeUpdate();
			
			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		
		return 0;
	}

}
