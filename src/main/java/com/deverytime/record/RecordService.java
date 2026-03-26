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

}
