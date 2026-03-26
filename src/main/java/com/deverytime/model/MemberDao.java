package com.deverytime.model;

import com.deverytime.library.BasicDao;

public class MemberDao extends BasicDao {

	public int register(MemberDto dto) {
		try {
			// status(0=정상), twoFactor(0=비활성화), type(0=회원) 고정값 삽입
			String sql = "insert into member (seq, name, id, pw, pic, nickname, email, status, twoFactor, type) "
					+ "values (memberSeq.nextVal, ?, ?, ?, ?, ?, ?, 0, 0, 0)";

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dto.getName());
			pstat.setString(2, dto.getId());
			pstat.setString(3, dto.getPw());
			pstat.setString(4, dto.getPic());
			pstat.setString(5, dto.getNickname());
			pstat.setString(6, dto.getEmail());

			return pstat.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	// 1. 아이디 중복 검사
	public int checkId(String id) {
		try {
			String sql = "select count(*) as cnt from member where id = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, id);
			rs = pstat.executeQuery();

			if (rs.next()) {
				return rs.getInt("cnt"); // 1(중복) 또는 0(사용가능) 반환
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1; // 에러가 나면 안전하게 가입을 막기 위해 1을 반환
	}

	// 2. 닉네임 중복 검사
	public int checkNick(String nickname) {
		try {
			String sql = "select count(*) as cnt from member where nickname = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, nickname);
			rs = pstat.executeQuery();

			if (rs.next()) {
				return rs.getInt("cnt");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	// 3. 이메일 중복 검사
	public int checkEmail(String email) {
		try {
			String sql = "select count(*) as cnt from member where email = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, email);
			rs = pstat.executeQuery();

			if (rs.next()) {
				return rs.getInt("cnt");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	// 4. 회원 정보 통째로 가져오기 (아이디로 검색)
	public MemberDto getMember(String id) {
		try {
			// 실패 횟수까지 모두 가져옴
			String sql = "select * from member where id = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, id);
			rs = pstat.executeQuery();

			if (rs.next()) {
				MemberDto dto = new MemberDto();
				dto.setSeq(rs.getString("seq"));
				dto.setId(rs.getString("id"));
				dto.setPw(rs.getString("pw"));
				dto.setName(rs.getString("name"));
				dto.setNickname(rs.getString("nickname"));
				dto.setEmail(rs.getString("email")); 
				dto.setPic(rs.getString("pic"));
				dto.setStatus(rs.getInt("status"));
				dto.setFailCount(rs.getInt("failCount"));
				dto.setTwoFactor(rs.getInt("twoFactor"));
				dto.setType(rs.getInt("type"));
				return dto;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; // 없는 아이디면 null 반환
	}

	// 5. 로그인 실패 시 failCount 증가 (5회 이상이면 status=1 잠금 처리)
	public void addFailCount(String id) {
		try {
			String sql = "update member set failCount = failCount + 1, "
					+ "status = case when failCount + 1 >= 5 then 1 else status end " + "where id = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, id);
			pstat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 6. 로그인 성공 시 failCount 0으로 초기화
	public void resetFailCount(String id) {
		try {
			String sql = "update member set failCount = 0 where id = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, id);
			pstat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 7. 이메일 존재 여부 확인 (아이디 찾기 1단계)
	public int findEmail(String email) {
		try {
			String sql = "select count(*) as cnt from member where email = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, email);
			rs = pstat.executeQuery();
			if (rs.next())
				return rs.getInt("cnt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	// 8. 이름+이메일로 아이디 찾기 (아이디 찾기 2, 3단계)
	public String findId(String name, String email) {
		try {
			String sql = "select id from member where name = ? and email = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, name);
			pstat.setString(2, email);
			rs = pstat.executeQuery();
			if (rs.next())
				return rs.getString("id"); // 일치하면 아이디 반환
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; // 정보 불일치 시 null
	}

	// 9. 아이디 + 이메일 일치 여부 확인 (비밀번호 찾기 2단계)
	public int checkIdAndEmail(String id, String email) {
		try {
			String sql = "select count(*) as cnt from member where id = ? and email = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, id);
			pstat.setString(2, email);
			rs = pstat.executeQuery();
			if (rs.next())
				return rs.getInt("cnt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	// 10. 임시 비밀번호로 업데이트 (비밀번호 찾기 3단계)
	public int updatePw(String id, String tempPw) {
		try {
			String sql = "update member set pw = ? where id = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, tempPw);
			pstat.setString(2, id);
			return pstat.executeUpdate(); // 성공하면 1 반환
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}


	// 11. 회원 정보 수정 (닉네임, 이메일, 프로필 사진 변경)
	public int updateMember(String id, String nickname, String email, String pic) {
		try {
			String sql = "update member set nickname = ?, email = ?, pic = ? where id = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, nickname);
			pstat.setString(2, email);
			pstat.setString(3, pic);
			pstat.setString(4, id);
			return pstat.executeUpdate(); // 성공 시 1 반환
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

}