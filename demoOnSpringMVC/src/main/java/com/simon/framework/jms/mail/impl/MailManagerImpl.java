/**
 * Title: MailManagerImpl.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.jms.mail.impl;

import java.text.MessageFormat;

import org.apache.commons.mail.HtmlEmail;

import com.simon.framework.config.AppConfiguration;
import com.simon.framework.exception.ServiceMailException;
import com.simon.framework.jms.mail.Mail;
import com.simon.framework.jms.mail.MailManager;
import com.simon.framework.jms.mail.MailTemplate;
import com.simon.framework.jms.mail.MailTemplateKey;
import com.simon.framework.util.FreeMarkerUtil;

/**
 * 
 * @ClassName: MailManagerImpl <br>
 * @Description: Mail管理实现类 <br>
 */
public class MailManagerImpl implements MailManager {

	/**
	 * 
	 * <p>
	 * Discription:[新建一个Mail对象]
	 * </p>
	 * 
	 * @param isHtmlBody
	 * @return
	 * @throws ServiceMailException
	 */
	public Mail newMailInstance(boolean isHtmlBody) throws ServiceMailException {
		return new MailImpl(isHtmlBody);
	}

	/**
	 * 
	 * <p>
	 * Discription:[发送邮件]
	 * </p>
	 * 
	 * @param mail
	 * @throws ServiceMailException
	 */
	public void sendMail(Mail mail) throws ServiceMailException {
		try {
			if (mail == null)
				return;
			MailImpl mailImpl = (MailImpl) mail;
			HtmlEmail htmlEmail = mailImpl.getMail();

			System.out.println("Send mail: " + htmlEmail.getSubject());
			htmlEmail.send();
			mailImpl.clear();
		} catch (Exception e) {
			throw new ServiceMailException(MessageFormat.format("发送邮件失败原因为[{0}]", e.getMessage()));
		}
	}

	/**
	 * 
	 * <p>
	 * Discription:[发送邮件]
	 * </p>
	 * 
	 * @param mail
	 * @param template
	 * @throws ServiceMailException
	 */
	public void sendMail(Mail mail, MailTemplate template) throws ServiceMailException {
		try {
			if (mail == null || template == null || template.getBody() == null)
				return;
			MailImpl mailImpl = (MailImpl) mail;

			String subject = FreeMarkerUtil.template2String(template.getSubject(), mailImpl.getContentMap(), false);
			String msg = FreeMarkerUtil.template2String(template.getBody(), mailImpl.getContentMap(), false);
			mailImpl.setSubject(subject);
			mailImpl.setMsg(msg);

			System.out.println("Send mail: " + subject);
			mailImpl.getMail().send();
			mailImpl.clear();
		} catch (Exception e) {
			throw new ServiceMailException(MessageFormat.format("发送邮件失败原因为[{0}]", e.getMessage()));
		}
	}

	/**
	 * 
	 * <p>
	 * Discription:[发送邮件]
	 * </p>
	 * 
	 * @param mail
	 * @param vmTemplate
	 * @throws ServiceMailException
	 */
	public void sendMail(Mail mail, MailTemplateKey vmTemplate) throws ServiceMailException {
		MailTemplate template = getTemplateByName(vmTemplate.toString());
		this.sendMail(mail, template);
	}

	/**
	 * 
	 * <p>
	 * Discription:[通过名字获得Template的对象]
	 * </p>
	 * 
	 * @param name
	 * @return
	 */
	private MailTemplate getTemplateByName(String name) {
		AppConfiguration config = AppConfiguration.getInstance();
		return config.getMailTemplate(name);
	}
}
