package com.deverytime.comment;

import com.deverytime.model.CommentDao;
import com.deverytime.model.CommentDto;

public class CommentService {

	public int add(CommentDto cDto) {
		
		CommentDao dao = new CommentDao();

		// DB에 인서트하기전에 id로 memberSeq를 알아오기
		String memberSeq = dao.getMemberSeqById(cDto.getId());

		cDto.setMemberSeq(memberSeq);

		int result = dao.add(cDto);

		return result;
		
	}

	public int del(String seq) {
		
		CommentDao dao = new CommentDao();
		
		int result = dao.del(seq);
		
		return result;
	}

}
