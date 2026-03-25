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
	
}
