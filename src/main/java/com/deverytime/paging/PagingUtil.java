package com.deverytime.paging;

import java.util.Map;
import com.deverytime.model.PagingDto;

/**
 * 페이징 유틸리티 클래스
 */
public class PagingUtil {

	/**
	 * 현재 페이지 안전 파싱
	 * 
	 * @param page 페이지 문자열 (null/빈문자열 → 1 반환)
	 * @return 현재 페이지 번호
	 */
	public static int parseNowPage(String page) {
		if (page == null || page.trim().equals("")) {
			return 1;
		}
		try {
			return Integer.parseInt(page);
		} catch (Exception e) {
			return 1; // 예외시 1페이지
		}
	}

	/**
	 * Tailwind 스타일 페이지바 HTML 생성
	 * 
	 * @param paging      PagingDto 객체
	 * @param baseUrl     기본 URL
	 * @param queryString 검색/카테고리 쿼리스트링
	 * @return 완성된 페이지바 HTML
	 */
	public static String makePagebar(PagingDto paging, String baseUrl, String queryString) {
		int nowPage = paging.getNowPage();
		int totalPage = paging.getTotalPage();
		int blockSize = paging.getBlockSize();

		int loop = 1;
		int n = ((nowPage - 1) / blockSize) * blockSize + 1;

		StringBuilder pagebar = new StringBuilder();

		pagebar.append("<div class='flex justify-center'>");
		pagebar.append("<div class='inline-flex items-center overflow-hidden rounded-lg border border-slate-300 bg-white shadow-sm'>");

		// 이전 버튼
		if (n <= 1) {
			pagebar.append("<span class='px-3 py-2 text-sm text-slate-300 bg-slate-100 border-r border-slate-300 cursor-not-allowed'>이전</span>");
		} else {
			pagebar.append(String.format(
				"<a class='px-3 py-2 text-sm text-slate-700 hover:bg-slate-100 border-r border-slate-300 transition' href='%s?page=%d%s'>이전</a>",
				baseUrl, n - 1, queryString
			));
		}

		// 페이지 번호
		while (!(loop > blockSize || n > totalPage)) {
			if (n == nowPage) {
				pagebar.append(String.format(
					"<span class='px-3 py-2 text-sm font-semibold text-white bg-indigo-500 border-r border-slate-300'>%d</span>",
					n
				));
			} else {
				pagebar.append(String.format(
					"<a class='px-3 py-2 text-sm text-slate-700 hover:bg-slate-100 border-r border-slate-300 transition' href='%s?page=%d%s'>%d</a>",
					baseUrl, n, queryString, n
				));
			}
			loop++;
			n++;
		}

		// 다음 버튼
		if (n > totalPage) {
			pagebar.append("<span class='px-3 py-2 text-sm text-slate-300 bg-slate-100 cursor-not-allowed'>다음</span>");
		} else {
			pagebar.append(String.format(
				"<a class='px-3 py-2 text-sm text-slate-700 hover:bg-slate-100 transition' href='%s?page=%d%s'>다음</a>",
				baseUrl, n, queryString
			));
		}

		pagebar.append("</div>");
		pagebar.append("</div>");

		return pagebar.toString();
	}

	/**
	 * 검색 조건 쿼리스트링 생성
	 * 
	 * @param paramMap 파라미터 맵
	 * @param keys     포함할 파라미터 키들
	 * @return ?category=1&searchType=title&keyword=안녕 형식
	 */
	public static String makeQueryString(Map<String, String> paramMap, String... keys) {
		StringBuilder query = new StringBuilder();
		for (String key : keys) {
			String value = paramMap.get(key);
			if (value != null && !value.trim().equals("")) {
				query.append("&").append(key).append("=").append(value);
			}
		}
		return query.toString();
	}
}