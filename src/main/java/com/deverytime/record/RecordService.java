package com.deverytime.record;

import com.deverytime.model.RecordDao;
import com.deverytime.model.RecordDto;

public class RecordService {

	public int add(RecordDto dto) {
		RecordDao dao = new RecordDao();
		
		return dao.add(dto);
	}

	public RecordDto get(String seq, String memberSeq) {
		RecordDao dao = new RecordDao();
		
		return dao.get(seq, memberSeq);
	}

	public int edit(RecordDto dto) {
		RecordDao dao = new RecordDao();
		
		return dao.edit(dto);
	}

	public String del(RecordDto dto) {
		RecordDao dao = new RecordDao();
		
		String planSeq = dao.getPlanSeq(dto.getSeq());
		
		int result = dao.del(dto);
		
		 if (result == 1) {
	        return planSeq;
	    }
		
		return null;
	}

	public RecordDto getDetailInfo(String seq, String memberSeq) {
		RecordDao dao = new RecordDao();
		
		return dao.getDetailInfo(seq, memberSeq);
	}

	public int getLatestProgress(String planSeq, String seq) {
		RecordDao dao = new RecordDao();
		
		return dao.getLatestProgress(planSeq, seq);
	}
}
