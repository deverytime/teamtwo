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
			MultipartRequest multi = new MultipartRequest(
					req, path, size, "UTF-8", new DefaultFileRenamePolicy()
			);

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
}