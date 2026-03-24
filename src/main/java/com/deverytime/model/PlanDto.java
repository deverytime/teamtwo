package com.deverytime.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanDto {
	private String seq;
	private String title;
	private String subject;
	private String description;
	private String type;
	private Date startDate;
	private Date endDate;
	private Date regDate;
	private Date updateDate;
	private String progressStatus;
	private String memberSeq;
}
