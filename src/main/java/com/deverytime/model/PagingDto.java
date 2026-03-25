package com.deverytime.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PagingDto {
	private int nowPage;
    private int pageSize;
    private int blockSize;
    private int totalCount;
    private int totalPage;
    private int begin;
    private int end;

    public PagingDto() {
        this(1, 10, 10);
    }
    
    public PagingDto(int nowPage, int pageSize, int blockSize) {
        this.nowPage = nowPage;
        this.pageSize = pageSize;
        this.blockSize = blockSize;
    }

    public void calculate() {
        this.begin = ((nowPage - 1) * pageSize) + 1;
        this.end = begin + pageSize - 1;
        this.totalPage = (int)Math.ceil(totalCount * 1.0 / pageSize);
    }

    public int getNowPage() { return nowPage; }
    public void setNowPage(int nowPage) { this.nowPage = nowPage; }

    public int getPageSize() { return pageSize; }
    public void setPageSize(int pageSize) { this.pageSize = pageSize; }

    public int getBlockSize() { return blockSize; }
    public void setBlockSize(int blockSize) { this.blockSize = blockSize; }

    public int getTotalCount() { return totalCount; }
    public void setTotalCount(int totalCount) { this.totalCount = totalCount; }

    public int getTotalPage() { return totalPage; }

    public int getBegin() { return begin; }
    public int getEnd() { return end; }
}
