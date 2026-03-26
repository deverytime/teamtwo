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

		// 3. 비밀번호 일치 (로그인 성공)
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

	// -------------------------------------------------------------
	// 아이디 찾기 1단계: 이메일 존재 검사
	// -------------------------------------------------------------
	public int checkEmail(HttpServletRequest req) {
		String email = req.getParameter("email");
		MemberDao dao = new MemberDao();
		return dao.checkEmail(email);
	}

	// -------------------------------------------------------------
	// 아이디 찾기 2단계: 인증번호 메일 발송
	// -------------------------------------------------------------
	public int sendAuthMail(HttpServletRequest req) {
		String email = req.getParameter("email");
		String name = req.getParameter("name");
		MemberDao dao = new MemberDao();

		String id = dao.findId(name, email);

		if (id != null) {
			// 무작위로 6자리 인증번호 생성 및 세션 저장
			String authCode = String.format("%06d", (int) (Math.random() * 1000000));
			req.getSession().setAttribute("findIdAuthCode", authCode);

			// 메일 전송
			String subject = "[deverytime] 아이디 찾기 인증번호 안내";
			String content = "<h3>요청하신 인증번호입니다.</h3><h1>" + authCode + "</h1><p>화면에 위 번호를 입력해주세요.</p>";
			com.deverytime.library.MailUtil.sendMail(email, subject, content);
			return 1; // 성공
		}
		return 0; // 실패
	}

	// -------------------------------------------------------------
	// 아이디 찾기 3단계: 인증번호 확인 및 아이디 메일 발송
	// -------------------------------------------------------------
	public int verifyAuthCode(HttpServletRequest req) {
		String inputCode = req.getParameter("authCode");
		String sessionCode = (String) req.getSession().getAttribute("findIdAuthCode");

		if (sessionCode != null && sessionCode.equals(inputCode)) {
			String email = req.getParameter("email");
			String name = req.getParameter("name");
			MemberDao dao = new MemberDao();
			String id = dao.findId(name, email);

			// 아이디가 포함된 메일 전송
			String subject = "[deverytime] 아이디 찾기 결과 안내";
			String content = "<h3>" + name + "님의 아이디입니다.</h3><h1>" + id + "</h1><p>로그인 페이지를 이용해주세요.</p>";
			com.deverytime.library.MailUtil.sendMail(email, subject, content);

			// 모든 작업이 끝난 세션 파기
			req.getSession().removeAttribute("findIdAuthCode");
			return 1; // 성공
		}
		return 0; // 실패
	}

	// -------------------------------------------------------------
	// 비밀번호 찾기 1단계: 아이디 존재 검사
	// -------------------------------------------------------------
	public int checkIdForPw(HttpServletRequest req) {
		String id = req.getParameter("id");
		MemberDao dao = new MemberDao();
		// 기존에 만들어둔 checkId 재사용 (있으면 1, 없으면 0 반환)
		return dao.checkId(id);
	}

	// -------------------------------------------------------------
	// 비밀번호 찾기 2단계: 아이디+이메일 확인 및 인증번호 발송
	// -------------------------------------------------------------
	public int sendAuthMailForPw(HttpServletRequest req) {
		String id = req.getParameter("id");
		String email = req.getParameter("email");
		MemberDao dao = new MemberDao();

		if (dao.checkIdAndEmail(id, email) > 0) {
			String authCode = String.format("%06d", (int) (Math.random() * 1000000));
			req.getSession().setAttribute("findPwAuthCode", authCode);

			String subject = "[deverytime] 비밀번호 찾기 인증번호 안내";
			String content = "<h3>요청하신 인증번호입니다.</h3><h1>" + authCode + "</h1><p>화면에 위 번호를 입력해주세요.</p>";
			com.deverytime.library.MailUtil.sendMail(email, subject, content);
			return 1;
		}
		return 0;
	}

	// -------------------------------------------------------------
	// 비밀번호 찾기 3단계: 인증 통과 시 임시비밀번호 발급 및 DB 변경
	// -------------------------------------------------------------
	public int verifyAuthCodeAndSendPw(HttpServletRequest req) {
		String inputCode = req.getParameter("authCode");
		String sessionCode = (String) req.getSession().getAttribute("findPwAuthCode");

		if (sessionCode != null && sessionCode.equals(inputCode)) {
			String id = req.getParameter("id");
			String email = req.getParameter("email");
			MemberDao dao = new MemberDao();

			// 8자리 무작위 임시 비밀번호 생성
			// 비밀번호 생성 조건(영문, 숫자, 특수문자 포함 8자리 이상)에 부합하도록 ! 추가
			String tempPw = java.util.UUID.randomUUID().toString().substring(0, 8) + "!";

			// DB의 비밀번호를 임시 비밀번호로 덮어쓰기
			if (dao.updatePw(id, tempPw) > 0) {
				String subject = "[deverytime] 임시 비밀번호 발급 안내";
				String content = "<h3>" + id + "님의 임시 비밀번호입니다.</h3><h1>" + tempPw
						+ "</h1><p>로그인 후 반드시 비밀번호를 변경해주세요.</p>";
				com.deverytime.library.MailUtil.sendMail(email, subject, content);

				req.getSession().removeAttribute("findPwAuthCode"); // 세션 청소
				return 1;
			}
		}
		return 0;
	}

}