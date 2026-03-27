package com.deverytime.admin;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.deverytime.model.AdminDao;

public class AdminService {

	public void loadDashboardStats(HttpServletRequest req) {
		AdminDao dao = new AdminDao();
		Map<String, Integer> stats = dao.getDashboardStats();
		
		// JSP에서 쓰기 편하게 request에 담아줌
		req.setAttribute("stats", stats);
	}
}