/**
 * Title: MailManager.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.jms.mail;

import com.simon.framework.exception.ServiceMailException;

/**
 * 
 * @ClassName: MailManager <br>
 * @Description: 邮件管理接口 <br>
 */
public interface MailManager {

	public void sendMail(Mail mail) throws ServiceMailException;

	public void sendMail(Mail mail, MailTemplate template) throws ServiceMailException;

	public void sendMail(Mail mail, MailTemplateKey vmTemplate) throws ServiceMailException;

	public Mail newMailInstance(boolean isHtmlBody) throws ServiceMailException;

}
