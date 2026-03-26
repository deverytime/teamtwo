package com.deverytime.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordDto {
	private String seq;           // 번호
    private Date studyDate;       // 실제 공부한 날짜
    private String content;       // 내용
    private int progress;         // 진행률
    private String memo;          // 기록 메모 (선택)
    private Date regdate;         // 작성일
    private Date updateDate;      // 수정일
    private String recordStatus;  // 기록상태 (완료, 진행중)
    private String planSeq;       // 계획번호
    
    private String memberSeq;
}
