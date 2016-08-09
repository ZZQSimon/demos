/**
 * Title: AppContextInitListener.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.adapter.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.simon.framework.config.AppConfiguration;
import com.simon.framework.util.LogUtil;

/**
 * @ClassName: AppContextInitListener <br>
 * @Description: load app config xml. <br>
 */
public class AppContextInitListener implements ServletContextListener, HttpSessionListener {

	private static final Log log = LogFactory.getLog(AppContextInitListener.class);

	private static final String ETC_FILE = "etc_file";

	public AppContextInitListener() {

	}

	public void contextDestroyed(final ServletContextEvent event) {

		AppConfiguration.getInstance().clear();
	}

	public void contextInitialized(final ServletContextEvent event) {
		final ServletContext context = event.getServletContext();
		final String configFile = context.getInitParameter(ETC_FILE);

		try {
			AppConfiguration.init(context.getRealPath(configFile));
		} catch (final Exception e) {
			// event.getServletContext().log("ConfigurationException: ", e);
			LogUtil.error(log, "load app config file error!");
		}

	}

	public void sessionCreated(final HttpSessionEvent event) {
	}

	public void sessionDestroyed(final HttpSessionEvent event) {
		final HttpSession session = event.getSession();
		session.invalidate();
	}

}
