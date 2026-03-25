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
	
	private String nickname; // member와 조인 닉네임 
	
	
	
	

}
