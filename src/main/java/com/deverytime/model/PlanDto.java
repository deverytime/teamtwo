package com.deverytime.model;



import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
	@Builder.Default
	private List<GoalDto> goals = new ArrayList<>(); // 완료기반 학습 계획용
	
	// 학습계획 상세정보 용 변수
	private long daysFromStart;
	private long daysToEnd;
	// lombok 이 초기화 무시 안하게 어노테이션 추가
	@Builder.Default
	private List<RecordDto> records = new ArrayList<>();
	private long recordCount;
	private int maxTime;
}
