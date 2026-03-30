package com.deverytime.board;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deverytime.model.FileDto;

@WebServlet(value = "/board/download.do")
public class Download extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 1. 요청 파라미터에서 파일seq
		String fileSeq = req.getParameter("file"); // JSP에서 ?file=3 이런 식으로 넘어옴

		// 2. DB에서 파일 정보 조회 (fileSeq로 files 테이블에서 단건 조회)
		BoardService service = new BoardService();
		FileDto dto = service.getFile(fileSeq); // null이면 파일 정보 없음

		// 3. DB에 해당 파일 정보가 없으면 404 에러
		if (dto == null) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND); // "파일을 찾을 수 없습니다"
			return; // 여기서 메서드 종료
		}

		// 4. 웹 애플리케이션 루트의 /uploads 폴더 실제 물리경로 구하기
		String uploadPath = getServletContext().getRealPath("/uploads");
		// 예:
		// /Users/사용자명/Desktop/study/project.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/teamtwo/uploads

		// 5. 실제 파일 객체 생성 (디렉토리 + 파일명 조합)
		File file = new File(uploadPath, dto.getName());

		// 6. 실제 파일이 서버에 존재하지 않으면 404 에러
		if (!file.exists()) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		// 7. 다운로드 시 보여줄 원본파일명 가져오기 (test.jpg)
		String originName = dto.getOriginName();

		// 8. 브라우저별 한글 파일명 깨짐 방지 처리
		String userAgent = req.getHeader("User-Agent");
		boolean ie = userAgent != null && (userAgent.contains("MSIE") || userAgent.contains("Trident"));
		// IE/Edge는 URL 인코딩, 크롬/파폭은 ISO-8859-1 방식

		// 9. 최종 다운로드 파일명 결정
		String downloadName;
		if (ie) {
			// IE: UTF-8 인코딩 후 +를 %20으로 변경
			downloadName = URLEncoder.encode(originName, "UTF-8").replaceAll("\\+", "%20");
		} else {
			// 크롬/파폭: UTF-8 바이트 → ISO-8859-1 문자셋 변환
			downloadName = new String(originName.getBytes("UTF-8"), "ISO-8859-1");
		}

		// 10. 응답 초기화하고 다운로드 헤더 설정
		resp.reset(); // 이전 헤더/바디 초기화
		resp.setContentType("application/octet-stream"); // 바이너리 파일 타입
		resp.setHeader("Content-Disposition", "attachment; filename=\"" + downloadName + "\"");
		// "파일을 다운로드창에 표시" 지시
		resp.setHeader("Content-Transfer-Encoding", "binary"); // 바이너리 전송
		resp.setContentLengthLong(file.length()); // 파일 크기 (바이트)

		// 11. 파일 → 브라우저 스트림 복사 (try-with-resources로 자동 close)
		try (FileInputStream fis = new FileInputStream(file); // 파일 읽기 스트림
				OutputStream out = resp.getOutputStream()) { // 브라우저 응답 쓰기 스트림

			byte[] buffer = new byte[4096]; // 4KB 버퍼로 효율적 읽기/쓰기
			int bytesRead;

			// 12. 파일 끝(EOF)까지 4KB씩 읽어서 브라우저로 전송
			while ((bytesRead = fis.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead); // 읽은 만큼만 쓰기 (버퍼 전체 아님)
			}

			out.flush(); // 출력 버퍼 강제 플러시
		}
	}
}