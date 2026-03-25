package com.deverytime.model;

import java.util.ArrayList;
import java.util.HashMap;

public class PlanService {
	
	
	public ArrayList<PlanDto> list (HashMap<String, String> map) {
		PlanDao dao = new PlanDao();
		
		return dao.list(map);
	}
	
	public PlanDto add() {
		PlanDao dao = new PlanDao();
		
		return dao.add();
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

}
