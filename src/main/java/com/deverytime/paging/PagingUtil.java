package com.deverytime.paging;

import java.util.Map;

import com.deverytime.model.PagingDto;

public class PagingUtil {

	 public static int parseNowPage(String page) {
        if (page == null || page.trim().equals("")) {
            return 1;
        }

        try {
            return Integer.parseInt(page);
        } catch (Exception e) {
            return 1;
        }
    }

    public static String makePagebar(PagingDto paging, String baseUrl, String queryString) {

        int nowPage = paging.getNowPage();
        int totalPage = paging.getTotalPage();
        int blockSize = paging.getBlockSize();

        int loop = 1;
        int n = ((nowPage - 1) / blockSize) * blockSize + 1;

        StringBuilder pagebar = new StringBuilder();

        pagebar.append("<div class='join'>");

        // 이전
        if (n <= 1) {
            pagebar.append("<a class='join-item btn btn-sm btn-disabled'>이전</a>");
        } else {
            pagebar.append(String.format(
                "<a class='join-item btn btn-sm' href='%s?page=%d%s'>이전</a>",
                baseUrl, n - 1, queryString
            ));
        }

        // 페이지 번호
        while (!(loop > blockSize || n > totalPage)) {
            if (n == nowPage) {
                pagebar.append(String.format(
                    "<a class='join-item btn btn-sm btn-active'>%d</a>", n
                ));
            } else {
                pagebar.append(String.format(
                    "<a class='join-item btn btn-sm' href='%s?page=%d%s'>%d</a>",
                    baseUrl, n, queryString, n
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
                "<a class='join-item btn btn-sm' href='%s?page=%d%s'>다음</a>",
                baseUrl, n, queryString
            ));
        }

        pagebar.append("</div>");

        return pagebar.toString();
    }

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
