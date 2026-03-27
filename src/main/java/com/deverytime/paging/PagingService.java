package com.deverytime.paging;

import java.util.HashMap;
import com.deverytime.model.PagingDto;

/**
 * 페이징 계산 및 페이지바 생성 서비스
 * 
 * @author 팀원
 */
public class PagingService {

	/**
	 * 페이징 정보 계산
	 * 
	 * @param page       현재 페이지 문자열
	 * @param totalCount 전체 게시글 수
	 * @param pageSize   페이지당 게시글 수
	 * @return 페이징 정보 HashMap
	 */
	public HashMap<String, String> getPaging(String page, int totalCount, int pageSize) {
		HashMap<String, String> map = new HashMap<>();

		// 1. 현재 페이지 파싱 (기본값: 1)
		int nowPage = PagingUtil.parseNowPage(page);

		// 2. PagingDto 생성 및 계산
		PagingDto paging = new PagingDto(nowPage, pageSize, 10); // blockSize=10
		paging.setTotalCount(totalCount);
		paging.calculate();

		// 3. 페이지바에 필요한 정보 저장
		map.put("begin", String.valueOf(paging.getBegin()));
		map.put("end", String.valueOf(paging.getEnd()));
		map.put("nowPage", String.valueOf(paging.getNowPage()));
		map.put("totalCount", String.valueOf(paging.getTotalCount()));
		map.put("totalPage", String.valueOf(paging.getTotalPage()));
		map.put("pageSize", String.valueOf(paging.getPageSize()));

		return map;
	}

	/**
	 * HTML 페이지바 생성
	 * 
	 * @param map     페이징 정보
	 * @param baseUrl 기본 URL (예: "list.do")
	 * @param keys    검색 파라미터 키들 (category, searchType, keyword 등)
	 * @return 페이지바 HTML 문자열
	 */
	public String getPagebar(HashMap<String, String> map, String baseUrl, String... keys) {
		PagingDto paging = new PagingDto();
		paging.setNowPage(Integer.parseInt(map.get("nowPage")));
		paging.setTotalCount(Integer.parseInt(map.get("totalCount")));
		paging.setPageSize(Integer.parseInt(map.get("pageSize")));
		paging.calculate();

		String query = PagingUtil.makeQueryString(map, keys); // 검색 조건 쿼리스트링

		return PagingUtil.makePagebar(paging, baseUrl, query);
	}
}