package com.deverytime.record;

import com.deverytime.model.RecordDao;
import com.deverytime.model.RecordDto;

public class RecordService {

	public int add(RecordDto dto) {
		RecordDao dao = new RecordDao();
		
		return dao.add(dto);
	}

}
