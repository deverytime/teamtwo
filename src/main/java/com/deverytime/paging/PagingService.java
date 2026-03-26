package com.deverytime.paging;

import java.util.HashMap;

import com.deverytime.model.PagingDto;

public class PagingService {

	public HashMap<String, String> getPaging(String page, int totalCount, int pageSize) {
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		int nowPage = PagingUtil.parseNowPage(page);

		PagingDto paging = new PagingDto(nowPage, pageSize, 10);
		paging.setTotalCount(totalCount);
		paging.calculate();

		map.put("begin", String.valueOf(paging.getBegin()));
		map.put("end", String.valueOf(paging.getEnd()));
		map.put("nowPage", String.valueOf(paging.getNowPage()));
		map.put("totalCount", String.valueOf(paging.getTotalCount()));
		map.put("totalPage", String.valueOf(paging.getTotalPage()));
		map.put("pageSize", String.valueOf(paging.getPageSize()));

		return map;
	}

	public String getPagebar(HashMap<String, String> map, String baseUrl, String... keys) {

		PagingDto paging = new PagingDto();
		paging.setNowPage(Integer.parseInt(map.get("nowPage")));
		paging.setTotalCount(Integer.parseInt(map.get("totalCount")));
		paging.setPageSize(Integer.parseInt(map.get("pageSize")));
		paging.calculate();

		String query = PagingUtil.makeQueryString(map, keys);

		return PagingUtil.makePagebar(paging, baseUrl, query);
	}
}