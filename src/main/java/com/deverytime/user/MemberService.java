package com.deverytime.user;

import javax.servlet.http.HttpServletRequest;

import com.deverytime.model.MemberDao;
import com.deverytime.model.MemberDto;
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

			// 서블릿(Login.java)에서 authDto 세션을 생성할 수 있도록,
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

	// -------------------------------------------------------------
	// 마이페이지 공용 자물쇠: 현재 비밀번호 확인 로직
	// -------------------------------------------------------------
	public int checkPassword(HttpServletRequest req) {
		// 1. 현재 세션에 로그인되어 있는 사용자의 아이디를 꺼냄
		String id = (String) req.getSession().getAttribute("auth");
		// 2. 방금 화면에서 사용자가 입력한 비밀번호를 가져옴
		String inputPw = req.getParameter("pw");

		MemberDao dao = new MemberDao();
		MemberDto dto = dao.getMember(id); // DB에서 최신 회원 정보 조회

		// 3. 비밀번호가 일치하면 1, 틀리면 0 반환
		if (dto != null && dto.getPw().equals(inputPw)) {
			return 1;
		}
		return 0;
	}

	// -------------------------------------------------------------
	// 회원 정보 수정 (파일 업로드 포함) 및 세션 동기화 로직
	// -------------------------------------------------------------
	public int editInfo(HttpServletRequest req) {
		try {
			// 1. 프로필 사진 저장 경로 및 세팅
			String path = req.getServletContext().getRealPath("/asset/pic");
			int size = 1024 * 1024 * 10; // 10MB
			com.oreilly.servlet.MultipartRequest multi = new com.oreilly.servlet.MultipartRequest(req, path, size,
					"UTF-8", new com.oreilly.servlet.multipart.DefaultFileRenamePolicy());

			// 2. 파라미터 꺼내기 (req가 아닌 multi에서 꺼내야 함)
			String id = (String) req.getSession().getAttribute("auth");
			String nickname = multi.getParameter("nickname");
			String email = multi.getParameter("email");
			String pic = multi.getFilesystemName("pic"); // 첨부한 새 사진 파일명

			MemberDao dao = new MemberDao();
			MemberDto oldDto = dao.getMember(id); // DB에 있던 기존 내 정보 꺼내기

			// 사용자가 새 사진을 첨부하지 않았다면, 기존 사진 이름을 그대로 유지
			if (pic == null || pic.trim().equals("")) {
				pic = oldDto.getPic();
			}

			// 3. DB 업데이트
			int result = dao.updateMember(id, nickname, email, pic);

			// 4. 세션 동기화
			if (result > 0) {
				MemberDto newDto = dao.getMember(id);
				req.getSession().setAttribute("authDto", newDto);
			}

			return result;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	// 2차 인증 끄고 켜기 (status: 0이면 끄기, 1이면 켜기) + 세션 갱신까지 처리
	public int toggleTwoFactor(HttpServletRequest req, int status) {
		String id = (String) req.getSession().getAttribute("auth");
		MemberDao dao = new MemberDao();

		int result = dao.updateTwoFactor(id, status);
		if (result > 0) {
			req.getSession().setAttribute("authDto", dao.getMember(id)); // 세션 최신화
		}
		return result;
	}

	// -------------------------------------------------------------
	// 2차 인증 이메일 발송 및 난수 생성 (TwofactorSetup 서블릿이 호출)
	// -------------------------------------------------------------
	public String sendTwoFactorAuthMail(HttpServletRequest req) {
		String email = req.getParameter("email");

		// 6자리 랜덤 인증코드 생성
		java.util.Random rnd = new java.util.Random();
		String code = String.valueOf(rnd.nextInt(900000) + 100000);

		String subject = "[deverytime] 2차 인증 번호 안내";
		String content = "<div style='font-family: sans-serif; padding: 20px; border: 1px solid #e2e8f0; border-radius: 10px; max-width: 400px;'>"
				+ "<h2 style='color: #2563eb;'>인증 번호 안내</h2>" + "<p>요청하신 2차 인증 번호입니다.</p>"
				+ "<div style='background-color: #f8fafc; padding: 15px; text-align: center; font-size: 24px; font-weight: bold; letter-spacing: 5px; border-radius: 5px;'>"
				+ code + "</div></div>";

		boolean isSuccess = com.deverytime.library.MailUtil.sendMail(email, subject, content);

		if (isSuccess) {
			return code; // 성공하면 생성된 코드를 반환!
		}
		return null; // 실패하면 null 반환
	}

	// -------------------------------------------------------------
	// 비밀번호 변경 및 세션 파기 (로그아웃) 로직
	// -------------------------------------------------------------
	public int editPassword(HttpServletRequest req) {
		String id = (String) req.getSession().getAttribute("auth");
		String newPw = req.getParameter("pw");

		MemberDao dao = new MemberDao();
		int result = dao.updatePw(id, newPw); // MemberDao의 10번 메서드 재활용

		if (result > 0) {
			// 비밀번호가 바뀌었으니 세션을 파기하고 강제 로그아웃
			req.getSession().invalidate();
		}
		return result;
	}

	// -------------------------------------------------------------
	// 회원 탈퇴 로직 (스터디장 검사 + 소프트 딜리트 + 세션 파기)
	// -------------------------------------------------------------
	public int unregisterMember(HttpServletRequest req) {
		String id = (String) req.getSession().getAttribute("auth");
		MemberDao dao = new MemberDao();

		// 1. 스터디장인지 검사
		if (dao.checkStudyLeader(id) > 0) {
			return -1; // 스터디장이면 탈퇴 불가 암호(-1) 반환
		}

		// 2. 스터디장이 아니면 실제 탈퇴 처리 (비식별화) 진행
		int result = dao.unregister(id);

		if (result > 0) {
			// 3. 탈퇴 성공 시 세션(로그인 기록) 파기
			req.getSession().invalidate();
		}

		return result;
	}

}