package com.deverytime.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDto {
	
	private String boardType; // board 타입 1자유 2질문 3자료공유 4학습공유   vw
	private String category;  // 1잡담/2개발/3에러/4인사/5유머/6자랑/7정보/8나눔/9토론/10공지 
	private String seq;
	private String title;
	private String content;
	private String readCount;
	private String regDate;
	private String recommend;
	private String report;
	private String memberSeq; // id때문에 필요
	private String id;
	
	private String nickname; // member와 조인 닉네임 
	
	private String prevSeq; // 이전글
	private String nextSeq; // 다음
	
	private String reasonSeq; //신고사유
	
	// 검색 관련
	private String searchType; //title, content, nickname, title_content
	private String keyword; //검색어
	
	// 페이징 관련
	private String pageStr; //브라우저에서 넘어오는 ?page로 넘어오는 문자열을 저장
	private int startRow;	// 페이징 시작위치
	private int pageSize = 15; // 페이지당 개수

}
