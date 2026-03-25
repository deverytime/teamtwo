package com.deverytime.model;

import lombok.Data;

@Data
public class MemberDto {
	private String seq;			// 회원 고유 번호 (PK)
	private String name;		// 이름
	private String id;			// 아이디
	private String pw;			// 비밀번호
	private String pic;			// 프로필 사진 파일명 (DDL 기본값: pic.png)
	private String nickname;	// 닉네임
	private String email;		// 이메일
	
	// 숫자(Number) 타입으로 설계된 관리용 데이터들
	private int status;			// 계정 상태 (0:정상, 1:잠김, 2:탈퇴)
	private int twoFactor;		// 2차 인증 (0:비활성화, 1:활성화)
	private int type;			// 권한 (0:회원, 1:관리자)
}