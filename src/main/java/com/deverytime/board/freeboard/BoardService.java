package com.deverytime.board.freeboard;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.deverytime.model.BoardDao;
import com.deverytime.model.BoardDto;
import com.deverytime.model.CommentDao;
import com.deverytime.model.CommentDto;
import com.deverytime.paging.PagingUtil;

public class BoardService {

	// 게시판에서 주제(카테고리)를 보여주기 위한 매핑
	private static final Map<String, String> CATEGORY_MAP = new HashMap<>();
	static {
		CATEGORY_MAP.put("1", "잡담");
		CATEGORY_MAP.put("2", "개발");
		CATEGORY_MAP.put("3", "에러");
		CATEGORY_MAP.put("4", "인사");
		CATEGORY_MAP.put("5", "유머");
		CATEGORY_MAP.put("6", "자랑");
		CATEGORY_MAP.put("7", "정보");
		CATEGORY_MAP.put("8", "나눔");
		CATEGORY_MAP.put("9", "토론");
		CATEGORY_MAP.put("10", "공지");
	}

	// 주제 한글 처리
	private String getCategoryName(String categoryKey) {
		return CATEGORY_MAP.get(categoryKey);
	}

	// 카테고리 보내주기
	public Map<String, String> getCategoryMap() {
		return CATEGORY_MAP;
	}

	public ArrayList<BoardDto> list(BoardDto param) {

		BoardDao dao = new BoardDao();

		// 페이징 자동 계산
		String pageStr = param.getPageStr();
		int nowPage = PagingUtil.parseNowPage(pageStr);
		int pageSize = param.getPageSize(); // 15개씩 보여줌
		int startRow = (nowPage - 1) * pageSize; // db결과에서 얼마나 가져올지
		param.setStartRow(startRow);

		// 목록 조회
		ArrayList<BoardDto> list = dao.list(param);

		for (BoardDto dto : list) {

			// 주제 한글 처리
			dto.setCategory(getCategoryName(dto.getCategory()));

			// 날짜 변환
			String regDate = dto.getRegDate();
			LocalDate postDate = LocalDate.parse(regDate.substring(0, 10)); // 저장된 String date로
			LocalDate today = LocalDate.now();

			if (postDate.equals(today)) {
				// 시간처리
				dto.setRegDate(regDate.substring(11));

			} else {
				// 날짜처리
				dto.setRegDate(regDate.substring(0, 10));

			}

		}

		return list;
	}

	public int add(BoardDto dto) {

		BoardDao dao = new BoardDao();

		// DB에 인서트하기전에 id로 memberSeq를 알아오기
		String memberSeq = dao.getMemberSeqById(dto.getId());

		dto.setMemberSeq(memberSeq);

		int result = dao.add(dto);

		return result;
	}

	public BoardDto view(BoardDto dto) {

		BoardDao dao = new BoardDao();

		dto = dao.view(dto);

		// 카테고리 한글 처리
		dto.setCategory(getCategoryName(dto.getCategory()));
		
		// 댓글 처리
		CommentDao cDao = new CommentDao();
		// 글번호를 통해 댓글 가져오기
		ArrayList<CommentDto> comments = cDao.listByPostSeq(dto.getSeq());
		dto.setComments(comments);

		return dto;
	}

	public void increaseReadCount(String seq) {

		BoardDao dao = new BoardDao();

		dao.increaseReadCount(seq);

	}

	public int recommend(BoardDto dto) {

		BoardDao dao = new BoardDao();

		// DB에 인서트하기전에 id로 memberSeq를 알아오기
		String memberSeq = dao.getMemberSeqById(dto.getId());

		dto.setMemberSeq(memberSeq);

		int result = dao.recommend(dto);

		return result;
	}

	public int report(BoardDto dto) {

		BoardDao dao = new BoardDao();

		// DB에 인서트하기전에 id로 memberSeq를 알아오기
		String memberSeq = dao.getMemberSeqById(dto.getId());

		dto.setMemberSeq(memberSeq);

		int result = dao.report(dto);

		return result;
	}

	// 수정을 위한 게시글 정보를 가져옴
	public BoardDto getPostBySeq(BoardDto dto) {

		BoardDao dao = new BoardDao();

		dto = dao.getPostBySeq(dto);

		return dto;
	}

	public int edit(BoardDto dto) {

		BoardDao dao = new BoardDao();

		int result = dao.edit(dto);

		return result;

	}

	public int del(String seq) {

		BoardDao dao = new BoardDao();

		int result = dao.del(seq);

		return result;

	}

	// 총 개수 조회 (List.java에서 호출)
	public int getTotalCount(BoardDto param) {
		BoardDao dao = new BoardDao();
		return dao.getTotalCount(param);
	}

}
