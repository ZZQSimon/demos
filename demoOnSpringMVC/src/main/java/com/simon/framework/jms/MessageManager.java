/**
 * Title: MessageManager.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.jms;

import java.util.Map;
import java.util.Map.Entry;

import com.simon.framework.exception.ServiceMailException;
import com.simon.framework.jms.mail.Mail;
import com.simon.framework.jms.mail.MailManager;
import com.simon.framework.jms.mail.MailTemplateKey;
import com.simon.framework.jms.mail.impl.MailManagerImpl;

/**
 * @ClassName: MessageManager
 * @Description: 发送消息工具类 <br>
 */
public class MessageManager {
	private MailManager mailManager = new MailManagerImpl();

	private static MessageManager messageManager;

	private static MessageManager getMailManagerInstance() {
		if (messageManager == null) {
			synchronized (MessageManager.class) {
				if (messageManager == null) {
					messageManager = new MessageManager();
				}
			}
		}
		return messageManager;
	}

	/**
	 * <p>
	 * Discription:[方法功能中文描述]
	 * </p>
	 * 
	 * @return MailManager mailManager.
	 */
	private MailManager getMailManager() {
		return mailManager;
	}

	/**
	 * 
	 * <p>
	 * Discription:[发送邮件]
	 * </p>
	 * 
	 * @param sendTo
	 *            收件人
	 * @param content
	 *            内容替换列表
	 * @param template
	 *            模版
	 * @param isHtmlBody
	 *            是否HTML类型的额文档内容
	 * @throws ServiceMailException
	 */
	public static void sendMail(String sendTo, Map<String, String> content, MailTemplateKey template,
			boolean isHtmlBody) throws ServiceMailException {
		sendMail(content, template, isHtmlBody);
	}

	/**
	 * 
	 * <p>
	 * Discription:[邮件发送给多人]
	 * </p>
	 * 
	 * @param content
	 *            内容替换列表
	 * @param template
	 *            模版
	 * @param isHtmlBody
	 *            是否HTML类型的额文档内容
	 * @param recieverList
	 *            收件人列表
	 * @throws ServiceMailException
	 */
	public static void sendMail(Map<String, String> content, MailTemplateKey template, boolean isHtmlBody,
			String... receiverList) throws ServiceMailException {
		MailManager mailManagerInMethod = MessageManager.getMailManagerInstance().getMailManager();
		Mail mail = mailManagerInMethod.newMailInstance(isHtmlBody);

		mail.addTo(receiverList);

		for (Entry<String, String> entry : content.entrySet()) {
			mail.addContent(entry.getKey(), entry.getValue());
		}

		mailManagerInMethod.sendMail(mail, template);
	}

}
