package com.deverytime.board.freeboard;

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

@WebServlet(value = "/board/freeboard/download.do")
public class Download extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String fileSeq = req.getParameter("file"); // 파일 seq

        BoardService service = new BoardService();
        FileDto dto = service.getFile(fileSeq);

        if (dto == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String uploadPath = getServletContext().getRealPath("/uploads");
        File file = new File(uploadPath, dto.getName());

        if (!file.exists()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String originName = dto.getOriginName();
        String userAgent = req.getHeader("User-Agent");
        boolean ie = userAgent != null &&
                (userAgent.contains("MSIE") || userAgent.contains("Trident"));

        String downloadName;
        if (ie) {
            downloadName = URLEncoder.encode(originName, "UTF-8").replaceAll("\\+", "%20");
        } else {
            downloadName = new String(originName.getBytes("UTF-8"), "ISO-8859-1");
        }

        resp.reset();
        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + downloadName + "\"");
        resp.setHeader("Content-Transfer-Encoding", "binary");
        resp.setContentLengthLong(file.length());

        try (FileInputStream fis = new FileInputStream(file);
             OutputStream out = resp.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            out.flush();
        }
    }
}