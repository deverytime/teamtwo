package com.deverytime.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StudyTodoDto {

	private String seq;
	private String title;
	private String content;
	private String status;
	private String studyScheduleSeq;
	
}
