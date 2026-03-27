package com.deverytime.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deverytime.library.BasicDao;

public class AdminDao extends BasicDao {

	// 관리자 메인 대시보드 통계 가져오기
	public Map<String, Integer> getDashboardStats() {
		Map<String, Integer> stats = new HashMap<>();

		try {
			// 1. 총 회원 수 (탈퇴 회원 제외하려면 where status != 2 추가)
			String sql1 = "select count(*) as cnt from member";
			pstat = conn.prepareStatement(sql1);
			rs = pstat.executeQuery();
			if (rs.next()) stats.put("totalMember", rs.getInt("cnt"));

			// 2. 총 게시글 수
			String sql2 = "select count(*) as cnt from post";
			pstat = conn.prepareStatement(sql2);
			rs = pstat.executeQuery();
			if (rs.next()) stats.put("totalPost", rs.getInt("cnt"));

			// 3. 진행 중 스터디 (모집중 0)
			String sql3 = "select count(*) as cnt from study where status = 0";
			pstat = conn.prepareStatement(sql3);
			rs = pstat.executeQuery();
			if (rs.next()) stats.put("activeStudy", rs.getInt("cnt"));

			// 4. 게시판 요청 관리 (전체, 미완료 0, 완료 1)
			String sql4 = "select count(*) as total, "
						+ "count(case when status = 0 then 1 end) as pending, "
						+ "count(case when status = 1 then 1 end) as completed "
						+ "from request_board";
			pstat = conn.prepareStatement(sql4);
			rs = pstat.executeQuery();
			if (rs.next()) {
				stats.put("reqTotal", rs.getInt("total"));
				stats.put("reqPending", rs.getInt("pending"));
				stats.put("reqCompleted", rs.getInt("completed"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stats;
	}

	// 회원 목록 조회
	public List<MemberDto> getMemberList(int begin, int end, String type, String word) {

		try {
			StringBuilder sql = new StringBuilder();

			sql.append("select * from (");
			sql.append("    select rownum as rnum, m.* from (");
			sql.append("        select * from member ");

			boolean hasWord = word != null && !word.trim().equals("");

			if (hasWord) {
				if ("id".equals(type)) {
					sql.append("where id like ? ");
				} else if ("name".equals(type)) {
					sql.append("where name like ? ");
				} else if ("nickname".equals(type)) {
					sql.append("where nickname like ? ");
				} else if ("all".equals(type)) {
					sql.append("where id like ? or name like ? or nickname like ? ");
				}
			}

			sql.append("        order by seq desc ");
			sql.append("    ) m ");
			sql.append(") where rnum between ? and ?");

			pstat = conn.prepareStatement(sql.toString());

			int index = 1;

			if (hasWord) {
				String searchWord = "%" + word + "%";

				if ("all".equals(type)) {
					pstat.setString(index++, searchWord);
					pstat.setString(index++, searchWord);
					pstat.setString(index++, searchWord);
				} else if ("id".equals(type) || "name".equals(type) || "nickname".equals(type)) {
					pstat.setString(index++, searchWord);
				}
			}

			pstat.setInt(index++, begin);
			pstat.setInt(index++, end);

			rs = pstat.executeQuery();

			List<MemberDto> list = new ArrayList<>();

			while (rs.next()) {
				MemberDto dto = new MemberDto();
				dto.setSeq(rs.getString("seq"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setNickname(rs.getString("nickname"));
				dto.setRegdate(rs.getString("regdate"));

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
	
	public int getMemberCount(String type, String word) {

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select count(*) as cnt ");
			sql.append("from member ");

			boolean hasWord = word != null && !word.trim().equals("");

			if (hasWord) {
				if ("id".equals(type)) {
					sql.append("where id like ? ");
				} else if ("name".equals(type)) {
					sql.append("where name like ? ");
				} else if ("nickname".equals(type)) {
					sql.append("where nickname like ? ");
				} else if ("all".equals(type)) {
					sql.append("where id like ? or name like ? or nick like ? ");
				}
			}

			pstat = conn.prepareStatement(sql.toString());

			if (hasWord) {
				String searchWord = "%" + word + "%";

				if ("all".equals(type)) {
					pstat.setString(1, searchWord);
					pstat.setString(2, searchWord);
					pstat.setString(3, searchWord);
				} else if ("id".equals(type) || "name".equals(type) || "nickname".equals(type)) {
					pstat.setString(1, searchWord);
				}
			}

			rs = pstat.executeQuery();

			if (rs.next()) {
				return rs.getInt("cnt");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}
	
	public List<BoardDto> getBoardList(int begin, int end, String type, String word) {

		try {
			StringBuilder sql = new StringBuilder();

			sql.append("select * from (");
			sql.append("    select rownum as rnum, t.* from (");
			sql.append("        select ");
			sql.append("            b.seq, b.title, b.content, ");
			sql.append("            b.readCount, b.regDate, b.recommend, b.report, ");
			sql.append("            b.memberSeq, m.id, m.nickname ");
			sql.append("        from post b ");
			sql.append("        inner join member m on b.memberSeq = m.seq ");

			boolean hasWord = word != null && !word.trim().equals("");

			if (hasWord) {
				if ("title".equals(type)) {
					sql.append("where b.title like ? ");
				} else if ("content".equals(type)) {
					sql.append("where b.content like ? ");
				} else if ("nickname".equals(type)) {
					sql.append("where m.nickname like ? ");
				} else if ("all".equals(type)) {
					sql.append("where b.title like ? ");
					sql.append("   or b.content like ? ");
					sql.append("   or m.nickname like ? ");
				}
			}

			sql.append("        order by b.seq desc ");
			sql.append("    ) t ");
			sql.append(") where rnum between ? and ?");

			pstat = conn.prepareStatement(sql.toString());

			int index = 1;

			if (hasWord) {
				String searchWord = "%" + word + "%";

				if ("all".equals(type)) {
					pstat.setString(index++, searchWord);
					pstat.setString(index++, searchWord);
					pstat.setString(index++, searchWord);
				} else if ("title".equals(type) || "content".equals(type) || "nickname".equals(type)) {
					pstat.setString(index++, searchWord);
				}
			}

			pstat.setInt(index++, begin);
			pstat.setInt(index++, end);

			rs = pstat.executeQuery();

			List<BoardDto> list = new ArrayList<>();

			while (rs.next()) {
				BoardDto dto = new BoardDto();
				dto.setSeq(rs.getString("seq"));				
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setReadCount(rs.getString("readCount"));
				dto.setRegDate(rs.getString("regDate"));
				dto.setRecommend(rs.getString("recommend"));
				dto.setReport(rs.getString("report"));
				dto.setMemberSeq(rs.getString("memberSeq"));
				dto.setId(rs.getString("id"));
				dto.setNickname(rs.getString("nickname"));

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
	
	public int getBoardCount(String type, String word) {

		try {
			StringBuilder sql = new StringBuilder();

			sql.append("select count(*) as cnt ");
			sql.append("from post b ");
			sql.append("inner join member m on b.memberSeq = m.seq ");

			boolean hasWord = word != null && !word.trim().equals("");

			if (hasWord) {
				if ("title".equals(type)) {
					sql.append("where b.title like ? ");
				} else if ("content".equals(type)) {
					sql.append("where b.content like ? ");
				} else if ("nickname".equals(type)) {
					sql.append("where m.nickname like ? ");
				} else if ("all".equals(type)) {
					sql.append("where b.title like ? ");
					sql.append("   or b.content like ? ");
					sql.append("   or m.nickname like ? ");
				}
			}

			pstat = conn.prepareStatement(sql.toString());

			if (hasWord) {
				String searchWord = "%" + word + "%";

				if ("all".equals(type)) {
					pstat.setString(1, searchWord);
					pstat.setString(2, searchWord);
					pstat.setString(3, searchWord);
				} else if ("title".equals(type) || "content".equals(type) || "nickname".equals(type)) {
					pstat.setString(1, searchWord);
				}
			}

			rs = pstat.executeQuery();

			if (rs.next()) {
				return rs.getInt("cnt");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}
	
	
}