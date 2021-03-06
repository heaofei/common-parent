package com.uetty.common.tool.core.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 使用spring的库（别人的，稍微修改了下）
 * @author vince
 */
public class SpringEmailSender {

	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(SpringEmailSender.class);

	private String username = "";

	private String password = "";

	private String smtpServer = "";

	private String smtpPort = "";

	private String title = "";

	private String fromName = "";

	private List<String> tos;

	private String content;

	private List<Attachment> atts;

	public SpringEmailSender() {
	}

	public void sendMail() throws MessagingException, Exception {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(this.smtpServer);
		mailSender.setPort(Integer.valueOf(this.smtpPort).intValue());
		mailSender.setUsername(this.username);
		mailSender.setPassword(this.password);

		MimeMessage msg = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true, "Utf-8");

		helper.setFrom(mailSender.getUsername(), this.fromName);

		helper.setSubject(this.title);
		helper.setText(this.content, true);

		InternetAddress[] sendTo = new InternetAddress[this.tos.size()];
		for (int i = 0; i < this.tos.size(); i++) {
			String mail = (String) this.tos.get(i);
			if ((mail != null) && (!"".equals(mail.trim()))) {

				sendTo[i] = new InternetAddress(mail);
			}
		}
		msg.setRecipients(MimeMessage.RecipientType.TO, sendTo);

		for (final Attachment item : this.atts) {
			helper.addAttachment(MimeUtility.encodeWord(item.getName()), new InputStreamSource() {
				public InputStream getInputStream() throws IOException {
					return new ByteArrayInputStream(item.getContent());
				}
			});
		}

		mailSender.send(msg);
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getTos() {
		return this.tos;
	}

	public void setTos(List<String> tos) {
		this.tos = tos;
	}

	public String getFromName() {
		return this.fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setAtt(List<Attachment> atts) {
		this.atts = atts;
	}
	
	public static class Attachment {
		private String name;
		private byte[] content;
		public Attachment(String name, byte[] bytes) {
			this.name = name;
			this.content = bytes;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public byte[] getContent() {
			return content;
		}
		public void setContent(byte[] bytes) {
			this.content = bytes;
		}
	}
}
