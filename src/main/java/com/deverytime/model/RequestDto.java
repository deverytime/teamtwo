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
public class RequestDto {
    private String seq;
    private String title;
    private int subject;
    private String content;
    private int readCount;
    private Date regDate;
    private int status;
    private String memberSeq;
}
