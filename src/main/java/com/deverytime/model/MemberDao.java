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

}