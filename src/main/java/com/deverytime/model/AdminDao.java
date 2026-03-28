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
				dto.setEmail(rs.getString("email"));
				dto.setStatus(rs.getInt("status"));
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
					sql.append("where id like ? or name like ? or nickname like ? ");
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

	public List<StudyDto> getStudyList(int begin, int end, String type, String word) {

		try {
			StringBuilder sql = new StringBuilder();

			sql.append("select * from (");
			sql.append("    select rownum as rnum, t.* from (");
			sql.append("        select ");
			sql.append("            s.seq, s.name, s.description, ");
			sql.append("            s.capacity, s.status, s.createDate ");
			sql.append("        from study s ");

			boolean hasWord = word != null && !word.trim().equals("");

			if (hasWord) {
				if ("name".equals(type)) {
					sql.append("where s.name like ? ");
				} else if ("description".equals(type)) {
					sql.append("where s.description like ? ");
				} else if ("all".equals(type)) {
					sql.append("where s.name like ? ");
					sql.append("   or s.description like ? ");
				}
			}

			sql.append("        order by s.seq desc ");
			sql.append("    ) t ");
			sql.append(") where rnum between ? and ?");

			pstat = conn.prepareStatement(sql.toString());

			int index = 1;

			if (hasWord) {
				String searchWord = "%" + word + "%";

				if ("all".equals(type)) {
					pstat.setString(index++, searchWord);
					pstat.setString(index++, searchWord);
				} else if ("name".equals(type) || "description".equals(type)) {
					pstat.setString(index++, searchWord);
				}
			}

			pstat.setInt(index++, begin);
			pstat.setInt(index++, end);

			rs = pstat.executeQuery();

			List<StudyDto> list = new ArrayList<>();

			while (rs.next()) {
				StudyDto dto = new StudyDto();

				dto.setSeq(rs.getString("seq"));
				dto.setName(rs.getString("name"));
				dto.setDescription(rs.getString("description"));
				dto.setCapacity(rs.getString("capacity"));
				dto.setStatus(rs.getString("status"));
				dto.setCreateDate(rs.getString("createDate"));

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
	
	public int getStudyCount(String type, String word) {

		try {
			StringBuilder sql = new StringBuilder();

			sql.append("select count(*) as cnt ");
			sql.append("from study s ");

			boolean hasWord = word != null && !word.trim().equals("");

			if (hasWord) {
				if ("name".equals(type)) {
					sql.append("where s.name like ? ");
				} else if ("description".equals(type)) {
					sql.append("where s.description like ? ");
				} else if ("all".equals(type)) {
					sql.append("where s.name like ? ");
					sql.append("   or s.description like ? ");
				}
			}

			pstat = conn.prepareStatement(sql.toString());

			if (hasWord) {
				String searchWord = "%" + word + "%";

				if ("all".equals(type)) {
					pstat.setString(1, searchWord);
					pstat.setString(2, searchWord);
				} else if ("name".equals(type) || "description".equals(type)) {
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
	
	public List<AdminReqDto> getRequestList(int begin, int end, String type, String word, String subject, String status) {

		try {
			StringBuilder sql = new StringBuilder();

			sql.append("select * from (");
			sql.append("    select rownum as rnum, t.* from (");
			sql.append("        select ");
			sql.append("            rb.seq, rb.title, rb.subject, rb.content, ");
			sql.append("            rb.regDate, rb.status, rb.memberSeq ");
			sql.append("        from request_board rb ");
			sql.append("        where 1=1 ");

			boolean hasWord = word != null && !word.trim().equals("");
			boolean hasSubject = subject != null && !subject.trim().equals("");
			boolean hasStatus = status != null && !status.trim().equals("");

			// 필터
			if (hasSubject) {
				sql.append("and rb.subject = ? ");
			}

			if (hasStatus) {
				sql.append("and rb.status = ? ");
			}

			// 검색
			if (hasWord) {
			    if ("title".equals(type)) {
			        sql.append("and rb.title like ? ");
			    } else if ("content".equals(type)) {
			        sql.append("and rb.content like ? ");
			    } else {
			        sql.append("and (rb.title like ? or rb.content like ?) ");
			    }
			}

			sql.append("        order by rb.seq desc ");
			sql.append("    ) t ");
			sql.append(") where rnum between ? and ?");

			pstat = conn.prepareStatement(sql.toString());

			int index = 1;

			if (hasSubject) {
				pstat.setString(index++, subject);
			}

			if (hasStatus) {
				pstat.setString(index++, status);
			}

			if (hasWord) {
				String searchWord = "%" + word + "%";

				if ("all".equals(type)) {
					pstat.setString(index++, searchWord);
					pstat.setString(index++, searchWord);
				} else if ("title".equals(type) || "content".equals(type)) {
					pstat.setString(index++, searchWord);
				}
			}

			pstat.setInt(index++, begin);
			pstat.setInt(index++, end);

			rs = pstat.executeQuery();

			List<AdminReqDto> list = new ArrayList<>();

			while (rs.next()) {
				AdminReqDto dto = new AdminReqDto();

				dto.setSeq(rs.getString("seq"));
				dto.setTitle(rs.getString("title"));
				dto.setSubject(Integer.parseInt(rs.getString("subject")));
				dto.setContent(rs.getString("content"));
				dto.setRegDate(rs.getDate("regDate"));
				dto.setStatus(rs.getInt("status"));
				dto.setMemberSeq(rs.getString("memberSeq"));

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

	public int getRequestCount(String type, String word, String subject, String status) {

		try {
			StringBuilder sql = new StringBuilder();

			sql.append("select count(*) as cnt ");
			sql.append("from request_board rb ");
			sql.append("where 1=1 ");

			boolean hasWord = word != null && !word.trim().equals("");
			boolean hasSubject = subject != null && !subject.trim().equals("");
			boolean hasStatus = status != null && !status.trim().equals("");

			// 필터
			if (hasSubject) {
				sql.append("and rb.subject = ? ");
			}

			if (hasStatus) {
				sql.append("and rb.status = ? ");
			}

			// 검색
			if (hasWord) {
				if ("title".equals(type)) {
					sql.append("and rb.title like ? ");
				} else if ("content".equals(type)) {
					sql.append("and rb.content like ? ");
				} else if ("all".equals(type)) {
					sql.append("and (rb.title like ? or rb.content like ?) ");
				}
			}

			pstat = conn.prepareStatement(sql.toString());

			int index = 1;

			if (hasSubject) {
				pstat.setString(index++, subject);
			}

			if (hasStatus) {
				pstat.setString(index++, status);
			}

			if (hasWord) {
				String searchWord = "%" + word + "%";

				if ("all".equals(type)) {
					pstat.setString(index++, searchWord);
					pstat.setString(index++, searchWord);
				} else if ("title".equals(type) || "content".equals(type)) {
					pstat.setString(index++, searchWord);
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

	public AdminMemberDto getMemberDetailInfo(String seq) {

		try {
			String sql = ""
					+ "select "
					+ "    m.seq, "
					+ "    m.name, "
					+ "    m.id, "
					+ "    m.nickname, "
					+ "    m.email, "
					+ "    m.regDate, "
					+ "    m.status, "
					+ "    m.failCount, "
					+ "    (select count(*) from post p where p.memberSeq = m.seq) as totalPosts, "
					+ "    (select count(*) from comments c where c.memberSeq = m.seq) as totalComments, "
					+ "    (select count(distinct sm.studySeq) "
					+ "        from study_member sm "
					+ "        where sm.memberSeq = m.seq) as totalStudies "
					+ "from member m "
					+ "where m.seq = ?";

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);

			rs = pstat.executeQuery();

			if (rs.next()) {
				return AdminMemberDto.builder()
						.seq(rs.getString("seq"))
						.name(rs.getString("name"))
						.id(rs.getString("id"))
						.nickname(rs.getString("nickname"))
						.email(rs.getString("email"))
						.regDate(rs.getDate("regDate"))
						.status(rs.getInt("status"))
						.failCount(rs.getInt("failCount"))
						.totalPosts(rs.getInt("totalPosts"))
						.totalComments(rs.getInt("totalComments"))
						.totalStudies(rs.getInt("totalStudies"))
						.build();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}	
}