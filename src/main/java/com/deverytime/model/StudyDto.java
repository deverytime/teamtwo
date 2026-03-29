package com.deverytime.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StudyDto {

	private String seq;
	private String name;
	private String description;
	private String capacity;
	private String status;
	private String createDate;
	
	private String scheduleCount; //일정 개수
	private String headCount; //참여 인원 수
	private String isMember; //study-list.jsp에서 쓸 'y' 또는 'n' 저장용
	
}
