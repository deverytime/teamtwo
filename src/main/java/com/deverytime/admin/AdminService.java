package com.deverytime.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deverytime.model.AdminDao;
import com.deverytime.model.AdminMemberDto;
import com.deverytime.model.AdminReqDto;
import com.deverytime.model.BoardDto;
import com.deverytime.model.MemberDto;
import com.deverytime.model.PagingDto;
import com.deverytime.model.StudyDto;
import com.deverytime.paging.PagingService;
import com.deverytime.paging.PagingUtil;

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

	public HashMap<String, Object> getBoardList(String page, String type, String word) {

		HashMap<String, Object> result = new HashMap<>();

		AdminDao dao = new AdminDao();
		PagingService pagingService = new PagingService();

		if (type == null || type.trim().equals("")) {
			type = "all";
		}

		if (word == null) {
			word = "";
		}

		int totalCount = dao.getBoardCount(type, word);

		HashMap<String, String> pagingMap = pagingService.getPaging(page, totalCount, 10);
		pagingMap.put("type", type);
		pagingMap.put("word", word);

		int begin = Integer.parseInt(pagingMap.get("begin"));
		int end = Integer.parseInt(pagingMap.get("end"));

		List<BoardDto> list = dao.getBoardList(begin, end, type, word);

		String pagebar = pagingService.getPagebar(
			pagingMap,
			"/teamtwo/admin/board-list.do",
			"type", "word"
		);

		result.put("list", list);
		result.put("pagebar", pagebar);
		result.put("type", type);
		result.put("word", word);
		result.put("totalCount", totalCount);

		return result;
	}

	public HashMap<String, Object> getStudyList(String page, String type, String word) {
		
		HashMap<String, Object> result = new HashMap<>();

		AdminDao dao = new AdminDao();
		PagingService pagingService = new PagingService();

		if (type == null || type.trim().equals("")) {
			type = "all";
		}

		if (word == null) {
			word = "";
		}

		int totalCount = dao.getStudyCount(type, word);

		HashMap<String, String> pagingMap = pagingService.getPaging(page, totalCount, 10);
		pagingMap.put("type", type);
		pagingMap.put("word", word);

		int begin = Integer.parseInt(pagingMap.get("begin"));
		int end = Integer.parseInt(pagingMap.get("end"));

		List<StudyDto> list = dao.getStudyList(begin, end, type, word);

		String pagebar = pagingService.getPagebar(
			pagingMap,
			"/teamtwo/admin/study-list.do",
			"type", "word"
		);

		result.put("list", list);
		result.put("pagebar", pagebar);
		result.put("type", type);
		result.put("word", word);
		result.put("totalCount", totalCount);

		return result;
		
		
		
	}

	public HashMap<String, Object> getRequestList(String page, String type, String word, String subject,
			String status) {
		
		HashMap<String, Object> result = new HashMap<>();
		AdminDao dao = new AdminDao();

		// null 처리
		if (type == null) type = "";
		if (word == null) word = "";
		if (subject == null) subject = "";
		if (status == null) status = "";

		// 현재 페이지
		int nowPage = PagingUtil.parseNowPage(page);

		// 한 페이지당 게시물 수
		int pageSize = 10;

		// 블록 크기
		int blockSize = 10;

		// 전체 개수
		int totalCount = dao.getRequestCount(type, word, subject, status);

		// 전체 페이지 수
		int totalPage = (int)Math.ceil((double)totalCount / pageSize);

		// begin, end 계산
		int begin = ((nowPage - 1) * pageSize) + 1;
		int end = begin + pageSize - 1;

		// PagingDto 생성
		PagingDto paging = PagingDto.builder()
				.nowPage(nowPage)
				.pageSize(pageSize)
				.blockSize(blockSize)
				.totalCount(totalCount)
				.totalPage(totalPage)
				.begin(begin)
				.end(end)
				.build();

		// 목록 조회
		List<AdminReqDto> list = dao.getRequestList(begin, end, type, word, subject, status);

		// 페이지바용 파라미터
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("type", type);
		paramMap.put("word", word);
		paramMap.put("subject", subject);
		paramMap.put("status", status);

		String queryString = PagingUtil.makeQueryString(paramMap, "type", "word", "subject", "status");

		String pagebar = PagingUtil.makePagebar(
				paging,
				"/teamtwo/admin/request/list.do",
				queryString
		);

		// result 담기
		result.put("list", list);
		result.put("pagebar", pagebar);
		result.put("type", type);
		result.put("word", word);
		result.put("subject", subject);
		result.put("status", status);
		result.put("totalCount", totalCount);

		return result;
	}

	public AdminMemberDto getMemberDetailInfo(String seq) {
		AdminDao dao = new AdminDao();
		
		
		return dao.getMemberDetailInfo(seq);
	}

	public void updateMemberStatus(String seq, int status) {
		AdminDao dao = new AdminDao();
		
		dao.updateMemberStatus(seq, status);
	}
	
	public AdminReqDto getRequestDetailInfo(String seq) {
		AdminDao dao = new AdminDao();

		AdminReqDto dto = dao.getRequestDetailInfo(seq);
		
		if (dto != null) {
			dto.setComments(dao.getRequestComments(seq));
		}
		
		return dto;
	}
	
	public void updateRequestStatus(String seq, String status) {
		AdminDao dao = new AdminDao();

		dao.updateRequestStatus(seq, status);
	}
	
	public void addRequestComment(String requestBoardSeq, String content) {
		AdminDao dao = new AdminDao();

		dao.addRequestComment(requestBoardSeq, content);
	}
}