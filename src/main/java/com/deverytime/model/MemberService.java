package com.deverytime.model;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class MemberService {

	public int register(HttpServletRequest req) {
		try {
			// 1. 프로필 사진이 저장될 실제 경로
			String path = req.getServletContext().getRealPath("/asset/pic");

			// 2. 최대 업로드 크기 제한 (10MB)
			int size = 1024 * 1024 * 10;

			// 3. 파일 업로드 실행
			MultipartRequest multi = new MultipartRequest(req, path, size, "UTF-8", new DefaultFileRenamePolicy());

			// 4. 입력 폼에서 데이터 꺼내기
			String id = multi.getParameter("id");
			String pw = multi.getParameter("pw");
			String name = multi.getParameter("name");
			String nickname = multi.getParameter("nickname");
			String email = multi.getParameter("email");
			String pic = multi.getFilesystemName("pic");

			// DDL의 DEFAULT 값인 'pic.png'
			if (pic == null || pic.trim().equals("")) {
				pic = "pic.png";
			}

			// 5. DTO에 포장
			MemberDto dto = new MemberDto();
			dto.setId(id);
			dto.setPw(pw);
			dto.setName(name);
			dto.setNickname(nickname);
			dto.setEmail(email);
			dto.setPic(pic);

			// 6. DAO 호출
			MemberDao dao = new MemberDao();
			return dao.register(dto);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	// -------------------------------------------------------------
	// 로그인 검사 로직 (Login 서블릿이 호출)
	// -------------------------------------------------------------
	public int login(HttpServletRequest req) {
		String id = req.getParameter("id");
		String pw = req.getParameter("pw");

		MemberDao dao = new MemberDao();
		MemberDto dto = dao.getMember(id); // DB에서 회원 정보 가져오기

		// 1. 존재하지 않는 아이디
		if (dto == null) {
			return 0;
		}

		// 2. 5회 이상 실패로 계정 잠김 상태
		if (dto.getStatus() == 1) {
			return -1;
		}

		// 3. 비밀번호 일치 (로그인 성공!)
		if (dto.getPw().equals(pw)) {
			dao.resetFailCount(id); // 실패 횟수 0으로 초기화

			// 서블릿(Login.java)에서 authDto 세션을 구울 수 있도록,
			// DB에서 가져온 회원 정보 뭉치(dto)를 request에 임시로 담아서 넘겨줌
			req.setAttribute("memberDto", dto);
			return 1;
		}
		// 4. 비밀번호 불일치
		else {
			dao.addFailCount(id); // 실패 횟수 +1 증가
			// 화면에 "몇 번 틀렸다"고 알려주기 위해 현재 실패 횟수 전달
			req.setAttribute("currentFail", dto.getFailCount() + 1);
			return -2;
		}
	}
}