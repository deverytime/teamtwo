package com.deverytime.model;

import lombok.Data;

@Data
public class UserDto {
	// 필수 데이터
	private String seq;			// 회원 고유 번호 (PK)
	private String id;			// 아이디
	private String pw;			// 비밀번호
	private String name;		// 이름 (아이디 찾기, 마이페이지용)
	private String nickname;	// 닉네임
	private String email;		// 이메일
	private String pic;			// 프로필 사진 파일명 (미등록시 기본값 처리 필요)
	
	// 시스템 관리용 데이터
	private String lv;			// 권한 레벨 (예: 1=일반회원, 2=관리자)
	private String status;		// 계정 상태 (정상, 탈퇴, 정지 등 - MEM-02의 5회 실패 정지용)
	private String regdate;		// 가입일
}