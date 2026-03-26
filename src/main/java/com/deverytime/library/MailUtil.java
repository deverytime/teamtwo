package com.deverytime.library;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {
	
	// 어디서든 MailUtil.sendMail(...)을 부르면 메일이 전송되는 메서드
	public static boolean sendMail(String toEmail, String subject, String content) {
		
		// 1. 발송할 구글 계정 정보
		final String user = "mia322x@gmail.com"; 
		final String password = "xxvrmgxqbtgekoew";

		// 2. 구글 SMTP 서버 세팅
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		// 3. 인증
		Session session = Session.getDefaultInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});

		try {
			// 4. 메일 세팅
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user, "deverytime 관리자", "UTF-8"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			message.setSubject(subject);
			message.setContent(content, "text/html; charset=utf-8");

			// 5. 발송
			Transport.send(message); 
			System.out.println("메일 전송 성공: " + toEmail);
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("메일 전송 실패!");
		}
		
		return false;
	}
}