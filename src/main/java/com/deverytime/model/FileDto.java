package com.deverytime.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FileDto {
	
	private String seq;
	private String originName;
	private String name;
	private String path;
	private long fileSize;
	private String postSeq;
	
}
