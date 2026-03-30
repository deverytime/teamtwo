package com.deverytime.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminMemberDto {	
	// 관리자페이지 회원 상세 기본 정보
	private String seq;			// 회원 고유 번호 (PK)
	private String name;		// 이름
	private String id;			// 아이디
	private String nickname;	// 닉네임
	private String email;		// 이메일
	private Date regDate;		// 사이트 가입일
	
	// 숫자(Number) 타입으로 설계된 관리용 데이터들
	private int status;			// 계정 상태 (0:정상, 1:잠김, 2:탈퇴)
	private int failCount;		// 로그인 실패 횟수(기본값: 0)
	// 관리자페이지 회원 상세 통계정보
	private int totalPosts;		// 총 작성 게시글 수
	private int totalComments;	// 총 작성 댓글 수
	private int totalStudies;	// 총 참여 스터디 수
}
