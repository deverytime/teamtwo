package com.deverytime.model;

import java.sql.ResultSet;
import java.util.HashMap;
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
}