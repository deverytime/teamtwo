package com.deverytime.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StudyScheduleDto {

	private String seq;
	private String title;
	private String content;
	private String startDate;
	private String endDate;
	private String studySeq;
	
}
