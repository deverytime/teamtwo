package com.deverytime.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentDto {
	
	private String seq;
	private String regDate;
	private String content;
	private String postSeq; // 글번호
	private String memberSeq; // 유저번호
	
	private String nickname;
	private String id;
	
}
