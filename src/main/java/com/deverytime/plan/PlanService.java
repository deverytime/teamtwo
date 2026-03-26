package com.deverytime.plan;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.deverytime.model.PlanDao;
import com.deverytime.model.PlanDto;
import com.deverytime.model.RecordDao;
import com.deverytime.model.RecordDto;

public class PlanService {
	
	
	public ArrayList<PlanDto> list (HashMap<String, String> map) {
		PlanDao dao = new PlanDao();
		
		return dao.list(map);
	}
	
	public int add(PlanDto dto) {
		PlanDao dao = new PlanDao();
		
		if (dto.getType().equals("기간기반")) {
			return dao.addPeriod(dto);
		} else if (dto.getType().equals("완료기반")) {
			return dao.addCompletion(dto);
		} 
		
		return -1;
	}

	public HashMap<String, String> getPaging(String page, String title, String type, int memberSeq) {
		
		PlanDao dao = new PlanDao();
		HashMap<String, String> map = new HashMap<>();

	    int nowPage = 1;
	    int pageSize = 10;
	    int begin, end;

	    if (page != null && !page.equals("")) {
	        nowPage = Integer.parseInt(page);
	    }

	    begin = ((nowPage - 1) * pageSize) + 1;
	    end = begin + pageSize - 1;

	    map.put("title", title);
	    map.put("type", type);
	    map.put("begin", String.valueOf(begin));
	    map.put("end", String.valueOf(end));
	    map.put("nowPage", String.valueOf(nowPage));
	    map.put("memberSeq", String.valueOf(memberSeq));

	    // 총 개수
	    int totalCount = dao.getTotalCount(map);
	    int totalPage = (int) Math.ceil(totalCount * 1.0 / pageSize);

	    map.put("totalCount", String.valueOf(totalCount));
	    map.put("totalPage", String.valueOf(totalPage));

	    return map;
	}

	public String getPagebar(HashMap<String, String> map) {

	    int nowPage = Integer.parseInt(map.get("nowPage"));
	    int totalPage = Integer.parseInt(map.get("totalPage"));

	    int blockSize = 10;
	    int loop = 1;
	    int n = ((nowPage - 1) / blockSize) * blockSize + 1;

	    String title = map.get("title");
	    String type = map.get("type");

	    StringBuilder pagebar = new StringBuilder();

	    // 검색조건 유지용 쿼리스트링
	    String query = "";

	    if (title != null && !title.equals("")) {
	        query += "&title=" + title;
	    }

	    if (type != null && !type.equals("")) {
	        query += "&type=" + type;
	    }

	    // 시작 (join wrapper)
	    pagebar.append("<div class='join'>");

	    // 이전
	    if (n <= 1) {
	        pagebar.append("<a class='join-item btn btn-sm btn-disabled'>이전</a>");
	    } else {
	        pagebar.append(String.format(
	                "<a class='join-item btn btn-sm' href='/teamtwo/plan/list.do?page=%d%s'>이전</a>",
	                n - 1, query
	        ));
	    }

	    // 페이지 번호
	    while (!(loop > blockSize || n > totalPage)) {

	        if (n == nowPage) {
	            pagebar.append(String.format(
	                    "<a class='join-item btn btn-sm btn-active'>%d</a>",
	                    n
	            ));
	        } else {
	            pagebar.append(String.format(
	                    "<a class='join-item btn btn-sm' href='/teamtwo/plan/list.do?page=%d%s'>%d</a>",
	                    n, query, n
	            ));
	        }

	        loop++;
	        n++;
	    }

	    // 다음
	    if (n > totalPage) {
	        pagebar.append("<a class='join-item btn btn-sm btn-disabled'>다음</a>");
	    } else {
	        pagebar.append(String.format(
	                "<a class='join-item btn btn-sm' href='/teamtwo/plan/list.do?page=%d%s'>다음</a>",
	                n, query
	        ));
	    }

	    // 끝
	    pagebar.append("</div>");

	    return pagebar.toString();
	}

	public PlanDto get(String seq, String memberSeq) {
		PlanDao dao = new PlanDao();
		
		return dao.get(seq, memberSeq);
	}
	
	public PlanDto getDetailInfo(String seq, String memberSeq, int cnt) {
		PlanDao dao = new PlanDao();
		RecordDao recordDao = new RecordDao();
		
		PlanDto planDto = dao.get(seq, memberSeq);
		
		// 상세정보 용 변수 넣어주기
		LocalDate today = LocalDate.now();

		// 시작일 처리 (없으면 regDate 사용)
		LocalDate startDate = null;
		if (planDto.getStartDate() != null) {
		    startDate = planDto.getStartDate().toLocalDate();
		} else if (planDto.getRegDate() != null) {
		    startDate = planDto.getRegDate().toLocalDate();
		}

		// 시작일 있을 때만 계산
		if (startDate != null) {
		    long daysFromStart = ChronoUnit.DAYS.between(startDate, today);
		    planDto.setDaysFromStart(daysFromStart);
		}

		// 종료일 있을 때만 계산
		if (planDto.getEndDate() != null) {
		    LocalDate endDate = planDto.getEndDate().toLocalDate();
		    long daysToEnd = ChronoUnit.DAYS.between(today, endDate);
		    planDto.setDaysToEnd(daysToEnd);
		}
		
		// 하단 학습기록 목록 가져오기
		List<RecordDto> records = recordDao.getRecordsByPlan(seq, cnt);
		planDto.setRecords(records);
		
		return planDto;
	}

	public int edit(PlanDto dto) {
		PlanDao dao = new PlanDao();
		
		if (dto.getType().equals("기간기반")) {
			return dao.editPeriod(dto);
		} else if (dto.getType().equals("완료기반")) {
			return dao.editCompletion(dto);
		} 
		
		return -1;
	}

	public int del(PlanDto dto) {
		PlanDao dao = new PlanDao();
		
		return dao.del(dto);
	}

}
