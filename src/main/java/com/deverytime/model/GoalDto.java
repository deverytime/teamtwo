package com.deverytime.model;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoalDto {

	private String seq;       // PK
	private String name;      // 목표 내용
	private int isDone;       // 0/1
	private Date doneDate;    // 완료일
	private String planSeq;   // FK (plan 연결)
}