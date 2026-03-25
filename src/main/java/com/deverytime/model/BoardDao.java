package com.deverytime.model;

import java.util.ArrayList;

import com.deverytime.library.BasicDao;

public class BoardDao extends BasicDao {

	public ArrayList<BoardDto> list() {

		try {

			String sql = "select * from vwPost";

			rs = stat.executeQuery(sql);

			ArrayList<BoardDto> list = new ArrayList<>();

			while (rs.next()) {
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

	public int add(BoardDto dto) {

		try {

			String sql = "insert into POST (SEQ, TITLE, CONTENT, READCOUNT, REGDATE, RECOMMEND, REPORT, MEMBERSEQ, BOARDSEQ, POSTCATEGORYSEQ) values (POSTSEQ.nextval, ?, ?, default, default, default, default, ?, ?, ?)";

			pstat = conn.prepareStatement(sql);

			pstat.setString(1, dto.getTitle());
			pstat.setString(2, dto.getContent());
			pstat.setString(3, dto.getMemberSeq());
			pstat.setString(4, dto.getBoardType());
			pstat.setString(5, dto.getCategory());

			int result = pstat.executeUpdate();

			return result;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}

		return 0;
	}

	// id를 가지고 memberseq를 반환하는 메서드
	public String getMemberSeqById(String id) {

		try {

			String sql = "select seq from member where id=?";
			pstat = conn.prepareStatement(sql);

			pstat.setString(1, id);

			rs = pstat.executeQuery();

			if (rs.next()) {
				return rs.getString("seq");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// add에서 conn을 쓸예정이니 add에서 닫아줌
//		} finally {
//			closeAll();
//		}

		return null;
	}

}
