package com.deverytime.model;

import java.sql.PreparedStatement;
import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.deverytime.library.BasicDao;

public class BoardDao extends BasicDao {

	public ArrayList<BoardDto> list(BoardDto param) {

		try {

			// 게시판타입/주제
			String board = param.getBoardType();
			String category = param.getCategory();

			// 검색
			String searchType = param.getSearchType();
			String keyword = param.getKeyword();

			// 검색어 쿼리 구간
			// String.format 피하기
			StringBuilder sql = new StringBuilder();
			sql.append("select * from vwPost where boardType = ?");

			// preparedStatement용 파라미터 리스트 ? 개수에 맞게 setString
			ArrayList<String> params = new ArrayList<>();
			params.add(board);

			if (category != null && !category.trim().equals("")) {
				sql.append(" and category = ?");
				params.add(category);
			}

			// 검색은 주제 선택보다 뒤에 일어나야함
			// Oracle에서 LIKE 연산자와 파라미터를 결합할 때는 문자열 연결 연산자인 ||를 사용하는 것이 가장 안전하고 일반적
			if (searchType != null && keyword != null && !keyword.trim().isEmpty()) {
				switch (searchType) {
				case "title" -> {
					sql.append(" and title like '%' || ? || '%'");
					params.add(keyword);
				}
				case "content" -> {
					sql.append(" and content like '%' || ? || '%'");
					params.add(keyword);
				}
				case "nickname" -> {
					sql.append(" and nickname like '%' || ? || '%'");
					params.add(keyword);
				}
				case "title_content" -> {
					sql.append(" and title like '%' || ? || '%' or content like '%' || ? || '%'");
					params.add(keyword);
					params.add(keyword);
				}
				}
			}

			// 정렬 및 페이
			sql.append(" order by seq desc").append(" offset ? rows fetch next ? rows only");

			// OFFSET ? ROWS → "이만큼 건너뛰어!" (30개 스킵)
			// FETCH NEXT ? ROWS → "이만큼만 가져와!" (15개 가져오기)
			// 검색어 쿼리 구간 끝

			pstat = conn.prepareStatement(sql.toString());
			for (int i = 0; i < params.size(); i++) {
				pstat.setString(i + 1, params.get(i));
			}

			// 페이징 파라미터 추가
			pstat.setInt(params.size() + 1, param.getStartRow());
			pstat.setInt(params.size() + 2, param.getPageSize());

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
				dto.setCommentCount(rs.getInt("commentCount"));

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

			// 1. 먼저 글번호 구하기
			String seqSql = "select POSTSEQ.nextval as seq from dual";
			stat = conn.createStatement();
			rs = stat.executeQuery(seqSql);

			String seq = null;
			if (rs.next()) {
				seq = rs.getString("seq");
			}

			// 2. insert
			String sql = "insert into POST (SEQ, TITLE, CONTENT, READCOUNT, REGDATE, RECOMMEND, REPORT, MEMBERSEQ, BOARDSEQ, POSTCATEGORYSEQ) "
					+ "values (?, ?, ?, default, default, default, default, ?, ?, ?)";

			pstat = conn.prepareStatement(sql);

			pstat.setString(1, seq);
			pstat.setString(2, dto.getTitle());
			pstat.setString(3, dto.getContent());
			pstat.setString(4, dto.getMemberSeq());
			pstat.setString(5, dto.getBoardType());
			pstat.setString(6, dto.getCategory());

			int result = pstat.executeUpdate();

			// 3. 성공하면 dto에 글번호 저장
			if (result == 1) {
				result = Integer.parseInt(seq);
			}

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

			String sql = "select * from vwPost";
			String board = "";
			String seq = String.format("seq = %s", dto.getSeq());

			// 보드타입으로 어떤 게시판인지 판별
			if (dto.getBoardType() != null) {
				if (dto.getBoardType().equals("1")) {
					board = " where boardType=1 and ";
				} else if (dto.getBoardType().equals("2")) {
					board = " where boardType=2 and ";
				} else if (dto.getBoardType().equals("3")) {
					board = " where boardType=3 and ";
				} else if (dto.getBoardType().equals("4")) {
					board = " where boardType=4 and ";
				}
			}

			if (dto.getBoardType() == null || dto.getBoardType().equals("")) {
				sql = sql + " where " + seq;
			} else {
				sql = sql + board + seq;
			}

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

	// list와 비슷하지만 성능을 위해 분리 // 하는일도 다름
	public int getTotalCount(BoardDto param) {
		try {
			// 1. 검색 조건 동일하게 설정
			String board = param.getBoardType();
			String category = param.getCategory();
			String searchType = param.getSearchType();
			String keyword = param.getKeyword();

			// 2. COUNT(*)
			StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM vwPost WHERE boardType = ?");
			ArrayList<String> params = new ArrayList<>();
			params.add(board);

			// 3. list()와 **똑같은** 조건 적용!
			if (category != null && !category.trim().equals("")) {
				sql.append(" AND category = ?");
				params.add(category);
			}

			if (searchType != null && keyword != null && !keyword.trim().isEmpty()) {
				switch (searchType) {
				case "title" -> {
					sql.append(" AND title LIKE '%' || ? || '%'");
					params.add(keyword);
				}
				case "content" -> {
					sql.append(" AND content LIKE '%' || ? || '%'");
					params.add(keyword);
				}
				case "nickname" -> {
					sql.append(" AND nickname LIKE '%' || ? || '%'");
					params.add(keyword);
				}
				case "title_content" -> {
					sql.append(" AND (title LIKE '%' || ? || '%' OR content LIKE '%' || ? || '%')");
					params.add(keyword);
					params.add(keyword);
				}
				}
			}

			pstat = conn.prepareStatement(sql.toString());
			for (int i = 0; i < params.size(); i++) {
				pstat.setString(i + 1, params.get(i));
			}

			rs = pstat.executeQuery();
			if (rs.next()) {
				return rs.getInt(1); // 총 개수만 반환
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return 0;
	}

	public void addFile(FileDto fileDto) {

		try {

			String sql = "insert into UPLOAD_FILE (SEQ, ORIGINNAME, NAME, PATH, FILESIZE, POSTSEQ) "
					+ "values (uploadFileSeq.nextval, ?, ?, ?, ?, ?)";

			pstat = conn.prepareStatement(sql);

			pstat.setString(1, fileDto.getOriginName());
			pstat.setString(2, fileDto.getName());
			pstat.setString(3, fileDto.getPath());
			pstat.setLong(4, fileDto.getFileSize());
			pstat.setString(5, fileDto.getPostSeq());

			pstat.executeUpdate();

			return;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}

		return;

	}

	public List getFileList(String seq) {
		List<FileDto> list = new ArrayList<FileDto>();

		try {
			String sql = "select * from UPLOAD_FILE where POSTSEQ = ?";

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);

			rs = pstat.executeQuery();

			while (rs.next()) {
				FileDto dto = new FileDto();
				dto.setSeq(rs.getString("seq"));
				dto.setOriginName(rs.getString("ORIGINNAME"));
				dto.setName(rs.getString("NAME"));
				dto.setPath(rs.getString("PATH"));
				dto.setFileSize(rs.getLong("FILESIZE"));
				dto.setPostSeq(rs.getString("POSTSEQ"));

				list.add(dto);
			}

			return list;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}

		return list;
	}

	public FileDto getFile(String fileSeq) {

		try {

			String sql = "select * from upload_file where seq = ?";

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, fileSeq);

			rs = pstat.executeQuery();

			if (rs.next()) {
				FileDto dto = new FileDto();
				dto.setSeq(rs.getString("seq"));
				dto.setName(rs.getString("name")); // 저장명
				dto.setOriginName(rs.getString("originName")); // 원본명
				dto.setFileSize(rs.getLong("fileSize"));
				dto.setPostSeq(rs.getString("postSeq"));

				return dto;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}

		return null;
	}

	public ArrayList<BoardDto> trendingList() {

		try {

			String sql = "select * from vwTrending";

			stat = conn.createStatement();
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

		return new ArrayList<>();
	}

	public BoardDto get(String seq) {

		try {

			String sql = "select * from vwPost a where seq = ?";

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);

			rs = pstat.executeQuery();

			if (rs.next()) {
				BoardDto dto = new BoardDto();

				dto.setSeq(rs.getString("seq"));
				dto.setCategory(rs.getString("category"));
				dto.setContent(rs.getString("content"));
				dto.setId(rs.getString("id"));
				dto.setRegDate(rs.getString("regDate"));
				dto.setReadCount(rs.getString("readcount"));

				return dto;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}

		return null;
	}

	// ==========================================
	// [메인 화면용] 인기글 최신순으로 가져오기
	// ==========================================
	public ArrayList<BoardDto> getTrendingList(int limit) {
		ArrayList<BoardDto> list = new ArrayList<>();
		try {
			String sql = "SELECT * FROM (SELECT * FROM vwTrending ORDER BY recommend DESC, readCount DESC) WHERE ROWNUM <= ?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, limit);
			rs = pstat.executeQuery();

			while (rs.next()) {
				BoardDto dto = new BoardDto();
				dto.setSeq(rs.getString("seq"));
				dto.setTitle(rs.getString("title"));
				dto.setRecommend(rs.getString("recommend"));
				dto.setBoardType(rs.getString("boardType")); // ★ 이거 추가!
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return list;
	}

	// ==========================================
	// [메인 화면용] 자유게시판 최신글 가져오기
	// ==========================================
	public ArrayList<BoardDto> getLatestFreeboardList(int limit) {
		ArrayList<BoardDto> list = new ArrayList<>();
		try {
			// ★ '1' 대신 숫자 1로 안전하게 매칭!
			String sql = "SELECT * FROM (SELECT * FROM vwPost WHERE boardType = 1 ORDER BY seq DESC) WHERE ROWNUM <= ?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, limit);
			rs = pstat.executeQuery();

			while (rs.next()) {
				BoardDto dto = new BoardDto();
				dto.setSeq(rs.getString("seq"));
				dto.setTitle(rs.getString("title"));
				String fullDate = rs.getString("regDate");
				dto.setRegDate(fullDate != null && fullDate.length() >= 10 ? fullDate.substring(0, 10) : fullDate);
				dto.setBoardType(rs.getString("boardType")); // ★ 이거 추가!
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return list;
	}

}
