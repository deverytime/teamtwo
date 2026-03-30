package com.deverytime.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminCommentsDto {
	private String seq;               // 댓글 번호
	private String content;           // 댓글 내용
	private String requestBoardSeq;   // 요청게시글 번호 (FK)

}