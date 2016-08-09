/**
 * Title: BaseController.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.base;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.simon.framework.adapter.interceptor.ThreadLocalResponse;
import com.simon.framework.beans.AjaxDone;
import com.simon.framework.editor.DateEditor;
import com.simon.framework.editor.DoubleEditor;
import com.simon.framework.editor.IntegerEditor;
import com.simon.framework.editor.LongEditor;
import com.simon.framework.enums.AjaxDoneStatusCode;
import com.simon.framework.enums.ExceptionTypeCode;
import com.simon.framework.exception.ServiceAuthorizeException;
import com.simon.framework.exception.ServiceBusinessException;
import com.simon.framework.exception.ServiceCSSException;
import com.simon.framework.exception.ServiceDBException;
import com.simon.framework.exception.ServiceIOException;
import com.simon.framework.exception.ServiceMailException;
import com.simon.framework.exception.ServiceThreadException;
import com.simon.framework.exception.ServiceTimerException;
import com.simon.framework.util.LogUtil;
import com.simon.framework.util.MessageUtil;
import com.simon.framework.util.StringUtils;

/**
 * 
 * @ClassName: BaseController <br>
 * @Description: 基础Controller类 <br>
 * @date 2016-08-09 09:03:53 <br>
 * 
 * @author Simon
 * 
 * @update:[2016-08-09] [Simon][修改备注.新增]
 */
public class BaseController {

	/**
	 * The LOGGER.
	 */
	private static final Log LOG = LogFactory.getLog(BaseController.class);

	/**
	 * The Error Description String : ERROR_STRING_ERROR_OCCURED.
	 */
	private static final String ERROR_STRING_ERROR_OCCURED = "Exception occured : statusCode[{0}], Message[{1}], returnUrl[{2}]";

	/**
	 * The empty string.
	 */
	private static final String EMPTY_STR = "";

	/**
	 * 国际化Messager Source.
	 */
	@Autowired
	protected ResourceBundleMessageSource _res;

	/**
	 * Discription:[返回默认提示.]
	 * 
	 * @param statusCode
	 *            AjaxDoneStatusCode
	 * @param returnUrl
	 *            String
	 * @return AjaxDone
	 */
	protected AjaxDone ajaxDone(final AjaxDoneStatusCode statusCode, final String returnUrl) {
		return new AjaxDone(statusCode.getCode(), statusCode.getDesc(), returnUrl);
	}

	/**
	 * Discription:[方法功能中文描述]
	 * 
	 * @param statusCode
	 *            错误标识码.
	 * @param message
	 *            错误信息.
	 * @param returnUrl
	 *            跳转url.
	 * @return AjaxDone
	 */
	protected AjaxDone ajaxDone(final AjaxDoneStatusCode statusCode, final String message, final String returnUrl) {

		return new AjaxDone(statusCode.getCode(), message, returnUrl);
	}

	/**
	 * <p>
	 * Discription:[返回默认操作失败提示.]
	 * </p>
	 * 
	 * @param message
	 * @return
	 */
	protected AjaxDone ajaxDoneError(final String message, final String returnUrl) {
		return ajaxDone(AjaxDoneStatusCode.CD_FAILED, message, returnUrl);
	}

	/**
	 * Discription:[返回默认"操作失败！"提示.]
	 * 
	 * @param returnUrl
	 *            String
	 * @return AjaxDone
	 */
	protected AjaxDone ajaxDoneError(final String returnUrl) {
		return ajaxDone(AjaxDoneStatusCode.CD_FAILED, returnUrl);
	}

	/**
	 * <p>
	 * Discription:[返回默认“操作成功！”提示]
	 * </p>
	 * 
	 * @param message
	 * @return
	 */
	protected AjaxDone ajaxDoneSuccess(final String returnUrl) {
		return ajaxDone(AjaxDoneStatusCode.CD_SUCCESS, returnUrl);
	}

	/**
	 * 
	 * <p>
	 * Discription:[Ajax返回“操作成功”提示]
	 * </p>
	 * 
	 * @param message
	 * @return
	 */
	protected AjaxDone ajaxDoneSuccess(final String message, final String returnUrl) {
		return ajaxDone(AjaxDoneStatusCode.CD_SUCCESS, message, returnUrl);
	}

	/**
	 * 系统异常统一处理方法
	 * 
	 * @author Simon
	 * @param e
	 * @return String
	 */
	@ExceptionHandler(Throwable.class)
	protected @ResponseBody AjaxDone exception(final Throwable e) {

		// 业务异常
		if (e instanceof ServiceBusinessException) {
			LOG.error(MessageUtil.formmatString(ERROR_STRING_ERROR_OCCURED, ExceptionTypeCode.CD_BUSINESS.getCode(),
					e.getMessage(), EMPTY_STR), e);
			return new AjaxDone(ExceptionTypeCode.CD_BUSINESS.getCode(), e.getMessage(), EMPTY_STR);
			// DB 操作异常
		} else if (e instanceof ServiceDBException || e instanceof SQLException) {
			LOG.error(MessageUtil.formmatString(ERROR_STRING_ERROR_OCCURED, ExceptionTypeCode.CD_DB.getCode(),
					e.getMessage(), EMPTY_STR), e);
			return new AjaxDone(ExceptionTypeCode.CD_DB.getCode(), e.getMessage(), EMPTY_STR);
			// 权限验证异常
		} else if (e instanceof ServiceAuthorizeException || e instanceof AuthenticationException) {
			LOG.error(MessageUtil.formmatString(ERROR_STRING_ERROR_OCCURED,
					ExceptionTypeCode.CD_AUTHORIZATION.getCode(), e.getMessage(), EMPTY_STR), e);
			return new AjaxDone(ExceptionTypeCode.CD_AUTHORIZATION.getCode(), e.getMessage(), EMPTY_STR);
			// CSS Server异常
		} else if (e instanceof ServiceCSSException) {
			LOG.error(MessageUtil.formmatString(ERROR_STRING_ERROR_OCCURED, ExceptionTypeCode.CD_CSS.getCode(),
					e.getMessage(), EMPTY_STR), e);
			return new AjaxDone(ExceptionTypeCode.CD_CSS.getCode(), e.getMessage(), EMPTY_STR);
			// 定时器异常
		} else if (e instanceof ServiceTimerException) {
			LOG.error(MessageUtil.formmatString(ERROR_STRING_ERROR_OCCURED, ExceptionTypeCode.CD_TIMER.getCode(),
					e.getMessage(), EMPTY_STR), e);
			return new AjaxDone(ExceptionTypeCode.CD_TIMER.getCode(), e.getMessage(), EMPTY_STR);
			// 流异常
		} else if (e instanceof ServiceIOException || e instanceof IOException) {
			LOG.error(MessageUtil.formmatString(ERROR_STRING_ERROR_OCCURED, ExceptionTypeCode.CD_FILE.getCode(),
					e.getMessage(), EMPTY_STR), e);
			return new AjaxDone(ExceptionTypeCode.CD_FILE.getCode(), e.getMessage(), EMPTY_STR);
			// 线程异常
		} else if (e instanceof ServiceThreadException || e instanceof InterruptedException) {
			LOG.error(MessageUtil.formmatString(ERROR_STRING_ERROR_OCCURED, ExceptionTypeCode.CD_THREAD.getCode(),
					e.getMessage(), EMPTY_STR), e);
			return new AjaxDone(ExceptionTypeCode.CD_THREAD.getCode(), e.getMessage(), EMPTY_STR);
			// 短信猫异常
		} else if (e instanceof ServiceMailException) {
			LOG.error(MessageUtil.formmatString(ERROR_STRING_ERROR_OCCURED, ExceptionTypeCode.CD_EMAIL.getCode(),
					e.getMessage(), EMPTY_STR), e);
			return new AjaxDone(ExceptionTypeCode.CD_EMAIL.getCode(), e.getMessage(), EMPTY_STR);
			// 其他所有系同级别的异常
		} else {
			LOG.error(MessageUtil.formmatString(ERROR_STRING_ERROR_OCCURED, ExceptionTypeCode.CD_SYS.getCode(),
					e.getMessage(), EMPTY_STR), e);
			return new AjaxDone(ExceptionTypeCode.CD_SYS.getCode(), e.getMessage(), EMPTY_STR);
		}
	}

	/**
	 * 
	 * <p>
	 * Discription:[组装国际化消息]
	 * </p>
	 * 
	 * @param code
	 * @param args
	 * @return
	 */
	protected String getMessage(final String code, final Object... args) {

		final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();

		final LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);

		final Locale locale = localeResolver.resolveLocale(request);

		LogUtil.debug(LOG,
				MessageUtil.formmatString("Internationalized [{0}].", MessageUtil.formmatString(code, args)));

		return _res.getMessage(code, args, locale);
	}

	/**
	 * 
	 * <p>
	 * Discription:[注册PropertyEditors来实现类型转换]
	 * </p>
	 * 
	 * @param request
	 * @param binder
	 * @throws Exception
	 */
	@InitBinder
	protected void initBinder(final HttpServletRequest request, final ServletRequestDataBinder binder)
			throws Exception {
		binder.registerCustomEditor(int.class, new IntegerEditor());
		binder.registerCustomEditor(long.class, new LongEditor());
		binder.registerCustomEditor(double.class, new DoubleEditor());

		// Following binder was conflict with the conversion service
		binder.registerCustomEditor(Date.class, new DateEditor());
	}

	/**
	 * 
	 * <p>
	 * Discription:[根据页面请求封装RowBounds对象]
	 * </p>
	 * 
	 * @param baseReq
	 */
	public RowBounds buildRowBounds(BaseRequest baseReq) {
		RowBounds rb;
		if (StringUtils.isNotEmpty(baseReq.getPage()) && StringUtils.isNotEmpty(baseReq.getPage())) {
			rb = new RowBounds((Integer.parseInt(baseReq.getPage()) - 1) * Integer.parseInt(baseReq.getRows()),
					Integer.parseInt(baseReq.getRows()));
		} else {
			rb = new RowBounds();
		}
		return rb;
	}

	/**
	 * 
	 * <p>
	 * Discription:[根据页面请求封装RowBounds对象]
	 * </p>
	 * 
	 */
	public RowBounds buildRowBounds() {
		return new RowBounds(0, -1);
	}

	/**
	 * 
	 * <p>
	 * Discription:[注入HttpServletResponse]
	 * </p>
	 * 
	 * @param response
	 */
	@ModelAttribute
	public void setReqAndRes(HttpServletResponse response) {
		ThreadLocalResponse.set(response);
	}
}
