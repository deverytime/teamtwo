package com.deverytime.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.deverytime.library.BasicDao;

public class BoardDao extends BasicDao {

	public ArrayList<BoardDto> list(BoardDto param) {

		try {
			
			// 게시판타입/주제
			String board = param.getBoardType();
			String category = param.getCategory();
			
			
			// String.format피하기
			StringBuilder sql = new StringBuilder();
			sql.append("select * from vwPost where boardType = ?");
			
			// preparedStatement용 파라미터 리스트 ? 개수에 맞게 setString
			ArrayList<String> params = new ArrayList<>();
			params.add(board);
			
			if(category!=null) {
				sql.append(" and category = ?");
				params.add(category);
			}
			
			sql.append(" order by seq desc");

			pstat = conn.prepareStatement(sql.toString());
			for(int i=0; i<params.size(); i++) {
				pstat.setString(i+1, params.get(i));
			}
			
			rs = pstat.executeQuery();

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

		return new ArrayList<>();
	}

	// 글쓰기
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
//		finally {
//			closeAll();
//		}

		return null;
	}

	public BoardDto view(BoardDto dto) {

		try {
			
			String sql = "select * from vwPost ";
			String board = "";
			String seq = String.format("seq = %s", dto.getSeq());

			// 보드타입으로 어떤 게시판인지 판별
			if (dto.getBoardType().equals("1")) {
				board = "where boardType=1 and ";
			}

			sql = sql + board + seq;

			rs = stat.executeQuery(sql);

			if (rs.next()) {
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setReadCount(rs.getString("readCount"));
				dto.setRegDate(rs.getString("regDate"));
				dto.setRecommend(rs.getString("recommend"));
				dto.setReport(rs.getString("report"));
				dto.setMemberSeq(rs.getString("memberSeq"));
				dto.setBoardType(rs.getString("boardType"));
				dto.setCategory(rs.getString("category"));
				dto.setNickname(rs.getString("nickname"));
				dto.setNextSeq(rs.getString("nextSeq"));
				dto.setPrevSeq(rs.getString("prevSeq"));
				dto.setId(rs.getString("id"));
			}

			return dto;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}

		return null;
	}

	public void increaseReadCount(String seq) {

		try {

			String sql = "update post set readCount = readCount + 1 where seq = ?";

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);

			pstat.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} // db 연결 아직 닫으면 안됨 다른 작업 있음

	}

	public int recommend(BoardDto dto) {

		int result = 0;

		try {

			// insert는 where절을 못쓰니 values대신 select로 직접 값 넣기
			String sql = "insert into post_recommend (seq, regdate, postseq, memberseq) select postRecommendSeq.nextval, sysdate, ?, ? from dual where not exists (select * from post_recommend where postseq=? and memberseq=?)";

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dto.getSeq());
			pstat.setString(2, dto.getMemberSeq());
			pstat.setString(3, dto.getSeq());
			pstat.setString(4, dto.getMemberSeq());

			result = pstat.executeUpdate();

			// 해당 글의 추천수도 증가
			if (result == 1) {
				String updateSql = "UPDATE POST SET RECOMMEND = RECOMMEND + 1 WHERE SEQ = ?";
				pstat = conn.prepareStatement(updateSql);
				pstat.setString(1, dto.getSeq());
				pstat.executeUpdate();
			}

			return result;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}

		return result;
	}

	public int report(BoardDto dto) {

		int result = 0;

		try {

			// insert는 where절을 못쓰니 values대신 select로 직접 값 넣기
			String sql = "insert into post_report (seq, regdate, postseq, memberseq, reportReasonSeq) select postReportSeq.nextval, sysdate, ?, ?, ? from dual where not exists (select * from post_report where postseq=? and memberseq=?)";

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dto.getSeq());
			pstat.setString(2, dto.getMemberSeq());
			pstat.setString(3, dto.getReasonSeq());
			pstat.setString(4, dto.getSeq());
			pstat.setString(5, dto.getMemberSeq());

			result = pstat.executeUpdate();

			// 해당 글의 추천수도 증가
			if (result == 1) {
				String updateSql = "UPDATE POST SET REPORT = REPORT + 1 WHERE SEQ = ?";
				pstat = conn.prepareStatement(updateSql);
				pstat.setString(1, dto.getSeq());
				pstat.executeUpdate();
			}

			return result;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}

		return result;
	}

	// 수정하기에서 가져가야할 정보 처리
	public BoardDto getPostBySeq(BoardDto dto) {

		try {

			String sql = "select * from vwPost where seq=?";

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dto.getSeq());

			rs = pstat.executeQuery();

			if (rs.next()) {
				dto.setCategory(rs.getString("category"));
				dto.setBoardType(rs.getString("boardType"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
			}

			return dto;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}

		return null;

	}

	public int edit(BoardDto dto) {

		try {
			
			String sql = "update post set postCategorySeq = ?, title = ?, content = ? where seq = ?";

			pstat = conn.prepareStatement(sql);

			pstat.setString(1, dto.getCategory());
			pstat.setString(2, dto.getTitle());
			pstat.setString(3, dto.getContent());
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
			
			String sql = "delete from post where seq = ?";
			 
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
