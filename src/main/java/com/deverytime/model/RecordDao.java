package com.deverytime.model;

import java.util.ArrayList;
import java.util.List;

import com.deverytime.library.BasicDao;

public class RecordDao extends BasicDao {

	public List<RecordDto> getRecordsByPlan(String planSeq, int cnt) {
		
		try {
			String sql = "select * from ("
	                   + "    select * from record"
	                   + "    where planSeq = ?"
	                   + "    order by seq desc"
	                   + ") where rownum <= ?";
			
			pstat = conn.prepareStatement(sql);
	        pstat.setString(1, planSeq);
	        pstat.setInt(2, cnt);

	        rs = pstat.executeQuery();

	        List<RecordDto> list = new ArrayList<>();

	        while (rs.next()) {
	            RecordDto dto = RecordDto.builder()
	                .seq(rs.getString("seq"))
	                .studyDate(rs.getDate("studyDate"))
	                .content(rs.getString("content"))
	                .progress(rs.getInt("progress"))
	                .memo(rs.getString("memo"))
	                .recordStatus(rs.getString("recordStatus"))
	                .build();

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

	public int getRecordCountByPlan(String planSeq) {
		
		try {
			String sql = "SELECT COUNT(*) FROM record WHERE planSeq = ?";

	        pstat = conn.prepareStatement(sql);
	        pstat.setString(1, planSeq);

	        rs = pstat.executeQuery();

	        if (rs.next()) {
	            return rs.getInt(1);
	        }

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		
		return 0;
	}

	public int add(RecordDto dto) {
		
		try {
			String sql = "INSERT INTO record (seq, studyDate, content, progress, memo, regdate, updateDate, recordStatus, planSeq) "
			           + "VALUES (recordSeq.nextval, ?, ?, ?, ?, sysdate, sysdate, ?, ?)";

			pstat = conn.prepareStatement(sql);

			pstat.setDate(1, dto.getStudyDate());
			pstat.setString(2, dto.getContent());
			pstat.setInt(3, dto.getProgress());
			pstat.setString(4, dto.getMemo());
			pstat.setString(5, dto.getRecordStatus());
			pstat.setString(6, dto.getPlanSeq());

			return pstat.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		
		return -1;
	}
	
	public RecordDto get(String seq, String memberSeq) {

	    try {
	        String sql = "select r.* "
	                   + "from record r "
	                   + "inner join plan p on r.planSeq = p.seq "
	                   + "where r.seq = ? and p.memberSeq = ?";

	        pstat = conn.prepareStatement(sql);
	        pstat.setString(1, seq);
	        pstat.setString(2, memberSeq);

	        rs = pstat.executeQuery();

	        if (rs.next()) {
	            return RecordDto.builder()
	                    .seq(rs.getString("seq"))
	                    .studyDate(rs.getDate("studyDate"))
	                    .content(rs.getString("content"))
	                    .progress(rs.getInt("progress"))
	                    .memo(rs.getString("memo"))
	                    .regdate(rs.getDate("regdate"))
	                    .updateDate(rs.getDate("updateDate"))
	                    .recordStatus(rs.getString("recordStatus"))
	                    .planSeq(rs.getString("planSeq"))
	                    .build();
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	public int edit(RecordDto dto) {

	    try {
	        String sql = "update record set "
	                   + "studyDate = ?, "
	                   + "content = ?, "
	                   + "progress = ?, "
	                   + "memo = ?, "
	                   + "updateDate = ?, "
	                   + "recordStatus = ? "
	                   + "where seq = ? "
	                   + "and planSeq in (select seq from plan where memberSeq = ?)";

	        pstat = conn.prepareStatement(sql);
	        pstat.setDate(1, dto.getStudyDate());
	        pstat.setString(2, dto.getContent());
	        pstat.setInt(3, dto.getProgress());
	        pstat.setString(4, dto.getMemo());
	        pstat.setDate(5, dto.getUpdateDate());
	        pstat.setString(6, dto.getRecordStatus());
	        pstat.setString(7, dto.getSeq());
	        pstat.setString(8, dto.getMemberSeq());

	        return pstat.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return 0;
	}
	
}
