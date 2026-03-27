package com.deverytime.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deverytime.model.AdminDao;
import com.deverytime.model.MemberDto;
import com.deverytime.paging.PagingService;

public class AdminService {

	public void loadDashboardStats(HttpServletRequest req) {
		AdminDao dao = new AdminDao();
		Map<String, Integer> stats = dao.getDashboardStats();
		
		// JSP에서 쓰기 편하게 request에 담아줌
		req.setAttribute("stats", stats);
	}

	public HashMap<String, Object> getMemberList(String page, String type, String word) {

		HashMap<String, Object> result = new HashMap<>();

		AdminDao dao = new AdminDao();
		PagingService pagingService = new PagingService();

		if (type == null || type.trim().equals("")) {
			type = "all";
		}

		if (word == null) {
			word = "";
		}

		int totalCount = dao.getMemberCount(type, word);

		HashMap<String, String> pagingMap = pagingService.getPaging(page, totalCount, 10);
		pagingMap.put("type", type);
		pagingMap.put("word", word);

		int begin = Integer.parseInt(pagingMap.get("begin"));
		int end = Integer.parseInt(pagingMap.get("end"));

		List<MemberDto> list = dao.getMemberList(begin, end, type, word);

		String pagebar = pagingService.getPagebar(
			pagingMap,
			"/teamtwo/admin/member-list.do",
			"type", "word"
		);

		result.put("list", list);
		result.put("pagebar", pagebar);
		result.put("type", type);
		result.put("word", word);
		result.put("totalCount", totalCount);

		return result;
	}
}