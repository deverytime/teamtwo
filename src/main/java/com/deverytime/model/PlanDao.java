package com.deverytime.model;

import java.util.ArrayList;
import java.util.HashMap;

import com.deverytime.library.BasicDao;

public class PlanDao extends BasicDao {
	
	public ArrayList<PlanDto> list(HashMap<String, String> map) {
	    
	    ArrayList<PlanDto> list = new ArrayList<>();

	    try {
	        StringBuilder sql = new StringBuilder();

	        // 페이징 여부 판단
	        boolean isPaging = map.get("begin") != null && map.get("end") != null;

	        if (isPaging) {
	            sql.append("select * from (");
	            sql.append("  select a.*, rownum as rnum from (");
	        }

	        sql.append("select seq, title, subject, description, type, regdate, progressStatus, memberSeq ");
	        sql.append("from plan ");
	        sql.append("where memberSeq = ? ");

	        if (map.get("title") != null && !map.get("title").equals("")) {
	            sql.append("and title like ? ");
	        }

	        if (map.get("type") != null && !map.get("type").equals("")) {
	            sql.append("and progressStatus = ? ");
	        }

	        sql.append("order by seq desc ");

	        if (isPaging) {
	            sql.append("  ) a ");
	            sql.append(") where rnum between ? and ?");
	        }

	        pstat = conn.prepareStatement(sql.toString());

	        int index = 1;

	        // memberSeq
	        pstat.setInt(index++, Integer.parseInt(map.get("memberSeq")));

	        // title
	        if (map.get("title") != null && !map.get("title").equals("")) {
	            pstat.setString(index++, "%" + map.get("title") + "%");
	        }

	        // type
	        if (map.get("type") != null && !map.get("type").equals("")) {
	            pstat.setString(index++, map.get("type"));
	        }

	        // 페이징
	        if (isPaging) {
	            pstat.setInt(index++, Integer.parseInt(map.get("begin")));
	            pstat.setInt(index++, Integer.parseInt(map.get("end")));
	        }

	        rs = pstat.executeQuery();

	        while (rs.next()) {
	            PlanDto dto = PlanDto.builder()
	                    .seq(rs.getString("seq"))
	                    .title(rs.getString("title"))
	                    .subject(rs.getString("subject"))
	                    .description(rs.getString("description"))
	                    .type(rs.getString("type"))
	                    .regDate(rs.getDate("regdate"))
	                    .progressStatus(rs.getString("progressStatus"))
	                    .build();

	            list.add(dto);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        closeAll();
	    }

	    return list;
	}
	
	public int addPeriod(PlanDto dto) {
		try {
			String sql = "INSERT INTO plan (seq, title, subject, description, type, startdate, enddate, regdate, updatedate, progressStatus, memberSeq) "
					+ "VALUES (planSeq.nextval, ?, ?, ?, ?, ?, ?, sysdate, sysdate, DEFAULT, ?)";

			pstat = conn.prepareStatement(sql);

			pstat.setString(1, dto.getTitle());
			pstat.setString(2, dto.getSubject());
			pstat.setString(3, dto.getDescription());
			pstat.setString(4, dto.getType());
			pstat.setDate(5, dto.getStartDate());
			pstat.setDate(6, dto.getEndDate());
			pstat.setString(7, dto.getMemberSeq());
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
	        closeAll();
		}
		
		return 0;
	}

	public int getTotalCount(HashMap<String, String> map) {
		
		 try {
	        StringBuilder sql = new StringBuilder();
	        sql.append("select count(*) as cnt ");
	        sql.append("from plan ");
	        sql.append("where memberSeq = ? ");

	        if (map.get("title") != null && !map.get("title").equals("")) {
	            sql.append("and title like ? ");
	        }

	        if (map.get("type") != null && !map.get("type").equals("")) {
	            sql.append("and type = ? ");
	        }

	        pstat = conn.prepareStatement(sql.toString());

	        int index = 1;
	        pstat.setInt(index++, Integer.parseInt(map.get("memberSeq")));

	        if (map.get("title") != null && !map.get("title").equals("")) {
	            pstat.setString(index++, "%" + map.get("title") + "%");
	        }

	        if (map.get("type") != null && !map.get("type").equals("")) {
	            pstat.setString(index++, map.get("type"));
	        }

	        rs = pstat.executeQuery();

	        if (rs.next()) {
	            return rs.getInt("cnt");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        closeAll();
	    }

		    return 0;
		}
	

	public int addCompletion(PlanDto dto) {
		
		try {
			String sql = "INSERT INTO plan (seq, title, subject, description, type, regdate, updatedate, progressStatus, memberSeq) "
			           + "VALUES (planSeq.nextval, ?, ?, ?, ?, sysdate, sysdate, DEFAULT, ?)";
			
			pstat = conn.prepareStatement(sql);

			pstat.setString(1, dto.getTitle());
			pstat.setString(2, dto.getSubject());
			pstat.setString(3, dto.getDescription());
			pstat.setString(4, dto.getType());
			pstat.setString(5, dto.getMemberSeq());
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
	        closeAll();
		}
		
		return 0;
	}

	public PlanDto get(String seq, String memberSeq) {
		
		try {
			String sql = "select * from plan where seq = ? and memberSeq = ?";
			
			pstat = conn.prepareStatement(sql);

			pstat.setString(1, seq);
			pstat.setString(2, memberSeq);
			
			rs = pstat.executeQuery();
			
			if (rs.next()) {
				PlanDto dto = PlanDto.builder()
					.seq(rs.getString("seq"))
	                .title(rs.getString("title"))
	                .subject(rs.getString("subject"))
	                .description(rs.getString("description"))
	                .type(rs.getString("type"))
	                .startDate(rs.getDate("startDate"))
	                .endDate(rs.getDate("endDate"))
	                .progressStatus(rs.getString("progressStatus"))
	                .memberSeq(rs.getString("memberSeq"))
	                .build();
				
				return dto;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		
		return null;
	}
}

