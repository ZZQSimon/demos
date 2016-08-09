/**
 * Title: Mail.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.jms.mail;

import java.net.URL;

import com.simon.framework.exception.ServiceMailException;

/**
 * 
 * @ClassName: Mail <br>
 * @Description: 邮件操作对象 <br>
 */
public interface Mail {

	void setSubject(String subject);

	void setFrom(String email) throws ServiceMailException;

	void addTo(String... email) throws ServiceMailException;

	void addTo(String email, String name) throws ServiceMailException;

	void addReplyTo(String email) throws ServiceMailException;

	void addReplyTo(String email, String name) throws ServiceMailException;

	void addCc(String email) throws ServiceMailException;

	void addCc(String email, String name) throws ServiceMailException;

	void addBcc(String email) throws ServiceMailException;

	void addBcc(String email, String name) throws ServiceMailException;

	void addAttache(URL url, String name, String desc) throws ServiceMailException;

	void addAttache(String path, String name, String desc) throws ServiceMailException;

	void addContent(String key, Object o);

	void setMsg(String msg) throws ServiceMailException;

}
