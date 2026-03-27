package com.deverytime.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


// TODO 임시 Dto 이름, RequestDto로 바꿔도 문제 없으면 바꿈
// 관리자 회원 요청 목록과 관련된 Dto 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminReqDto {
	private String seq;
	private String title;		// 요청 제목
	private int subject;		// 문의(0) / 요청(1)
	private String content;		// 요청 내용
	private Date regDate;
	private int status;			// 미완료(0) / 완료(1)
	private String memberSeq;
	
	// 요청게시판 요청 상세조회에 추가로 필요한 변수
	private String nickname;		// 요청한 회원 닉네임
	private String commentsContent;	// 요청상세에 달리는 답글내용
	private String requestBoardSeq;	// 요청게시글 번호
	private int readCount;

}
