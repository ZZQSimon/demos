package com.simon.framework.util;

import org.apache.commons.logging.Log;

/**
 * @ClassName: LogUtil <br>
 * @Description: log统一工具类 <br>
 * @date 2016.08.09 <br>
 * 
 * @author Simon
 */
public class LogUtil {
	/**
	 * <p>
	 * Discription:[Debug with message.]
	 * </p>
	 * 
	 * @param log
	 *            Log
	 * @param message
	 *            log message
	 * @author:[Simon]
	 */
	public static void debug(final Log log, final String message) {
		if (log.isDebugEnabled()) {
			log.debug(message);
		}
	}

	/**
	 * <p>
	 * Discription:[Debug with formatter.]
	 * </p>
	 * 
	 * @param log
	 *            Log
	 * @param formatter
	 *            String formatter
	 * @param args
	 *            use by formatter
	 * @author:[Simon]
	 */
	public static void debug(final Log log, final String formatter, final Object... args) {
		if (log.isDebugEnabled()) {
			log.debug(MessageUtil.formmatString(formatter, args));
		}
	}

	/**
	 * <p>
	 * Discription:[Debug with exception message.]
	 * </p>
	 * 
	 * @param log
	 *            Log
	 * @param e
	 *            Exception
	 * @author:[Simon]
	 */
	public static void debug(final Log log, final Throwable e) {
		if (log.isDebugEnabled()) {
			log.debug(e.getMessage(), e);
		}
	}

	/**
	 * <p>
	 * Discription:[Debug with exception and formatter message.]
	 * </p>
	 * 
	 * @param log
	 *            Log
	 * @param e
	 *            Exception
	 * @param formatter
	 *            String formatter
	 * @param args
	 *            use by formatter
	 * @author:[Simon]
	 */
	public static void debug(final Log log, final Throwable e, final String formatter, final Object... args) {
		if (log.isDebugEnabled()) {
			log.debug(MessageUtil.formmatString(formatter, args), e);
		}
	}

	/**
	 * <p>
	 * Discription:[Error with message.]
	 * </p>
	 * 
	 * @param log
	 *            Log
	 * @param message
	 *            log message
	 * @author:[Simon]
	 */
	public static void error(final Log log, final String message) {
		if (log.isErrorEnabled()) {
			log.error(message);
		}
	}

	/**
	 * <p>
	 * Discription:[Error with formatter.]
	 * </p>
	 * 
	 * @param log
	 *            Log
	 * @param formatter
	 *            String formatter
	 * @param args
	 *            use by formatter
	 * @author:[Simon]
	 */
	public static void error(final Log log, final String formatter, final Object... args) {
		if (log.isErrorEnabled()) {
			log.error(MessageUtil.formmatString(formatter, args));
		}
	}

	/**
	 * <p>
	 * Discription:[Error with exception message.]
	 * </p>
	 * 
	 * @param log
	 *            Log
	 * @param e
	 *            Exception
	 * @author:[Simon]
	 */
	public static void error(final Log log, final Throwable e) {
		if (log.isErrorEnabled()) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * <p>
	 * Discription:[Error with exception and formatter message.]
	 * </p>
	 * 
	 * @param log
	 *            Log
	 * @param e
	 *            Exception
	 * @param formatter
	 *            String formatter
	 * @param args
	 *            use by formatter
	 * @author:[Simon]
	 */
	public static void error(final Log log, final Throwable e, final String formatter, final Object... args) {
		if (log.isErrorEnabled()) {
			log.error(MessageUtil.formmatString(formatter, args), e);
		}
	}

	/**
	 * <p>
	 * Discription:[Fatal with message.]
	 * </p>
	 * 
	 * @param log
	 *            Log
	 * @param message
	 *            log message
	 * @author:[Simon]
	 */
	public static void fatal(final Log log, final String message) {
		if (log.isFatalEnabled()) {
			log.fatal(message);
		}
	}

	/**
	 * <p>
	 * Discription:[Fatal with formatter.]
	 * </p>
	 * 
	 * @param log
	 *            Log
	 * @param formatter
	 *            String formatter
	 * @param args
	 *            use by formatter
	 * @author:[Simon]
	 */
	public static void fatal(final Log log, final String formatter, final Object... args) {
		if (log.isFatalEnabled()) {
			log.fatal(MessageUtil.formmatString(formatter, args));
		}
	}

	/**
	 * <p>
	 * Discription:[Fatal with exception message.]
	 * </p>
	 * 
	 * @param log
	 *            Log
	 * @param e
	 *            Exception
	 * @author:[Simon]
	 */
	public static void fatal(final Log log, final Throwable e) {
		if (log.isFatalEnabled()) {
			log.fatal(e.getMessage(), e);
		}
	}

	/**
	 * <p>
	 * Discription:[fatal with exception and formatter message.]
	 * </p>
	 * 
	 * @param log
	 *            Log
	 * @param e
	 *            Exception
	 * @param formatter
	 *            String formatter
	 * @param args
	 *            use by formatter
	 * @author:[Simon]
	 */
	public static void fatal(final Log log, final Throwable e, final String formatter, final Object... args) {
		if (log.isFatalEnabled()) {
			log.fatal(MessageUtil.formmatString(formatter, args), e);
		}
	}

	/**
	 * <p>
	 * Discription:[Info with message.]
	 * </p>
	 * 
	 * @param log
	 *            Log
	 * @param message
	 *            log message
	 * @author:[Simon]
	 */
	public static void info(final Log log, final String message) {
		if (log.isInfoEnabled()) {
			log.info(message);
		}
	}

	/**
	 * <p>
	 * Discription:[Info with formatter.]
	 * </p>
	 * 
	 * @param log
	 *            Log
	 * @param formatter
	 *            String formatter
	 * @param args
	 *            use by formatter
	 * @author:[Simon]
	 */
	public static void info(final Log log, final String formatter, final Object... args) {
		if (log.isInfoEnabled()) {
			log.info(MessageUtil.formmatString(formatter, args));
		}
	}

	/**
	 * <p>
	 * Discription:[Info with exception message.]
	 * </p>
	 * 
	 * @param log
	 *            Log
	 * @param e
	 *            Exception
	 * @author:[Simon]
	 */
	public static void info(final Log log, final Throwable e) {
		if (log.isInfoEnabled()) {
			log.info(e.getMessage(), e);
		}
	}

	/**
	 * <p>
	 * Discription:[Info with exception and formatter message.]
	 * </p>
	 * 
	 * @param log
	 *            Log
	 * @param e
	 *            Exception
	 * @param formatter
	 *            String formatter
	 * @param args
	 *            use by formatter
	 * @author:[Simon]
	 */
	public static void info(final Log log, final Throwable e, final String formatter, final Object... args) {
		if (log.isInfoEnabled()) {
			log.info(MessageUtil.formmatString(formatter, args), e);
		}
	}

	/**
	 * <p>
	 * Discription:[Warn with message.]
	 * </p>
	 * 
	 * @param log
	 *            Log
	 * @param message
	 *            log message
	 * @author:[Simon]
	 */
	public static void warn(final Log log, final String message) {
		if (log.isWarnEnabled()) {
			log.warn(message);
		}
	}

	/**
	 * <p>
	 * Discription:[Warn with formatter.]
	 * </p>
	 * 
	 * @param log
	 *            Log
	 * @param formatter
	 *            String formatter
	 * @param args
	 *            use by formatter
	 * @author:[Simon]
	 */
	public static void warn(final Log log, final String formatter, final Object... args) {
		if (log.isWarnEnabled()) {
			log.warn(MessageUtil.formmatString(formatter, args));
		}
	}

	/**
	 * <p>
	 * Discription:[Warn with exception message.]
	 * </p>
	 * 
	 * @param log
	 *            Log
	 * @param e
	 *            Exception
	 * @author:[Simon]
	 */
	public static void warn(final Log log, final Throwable e) {
		if (log.isWarnEnabled()) {
			log.warn(e.getMessage(), e);
		}
	}

	/**
	 * <p>
	 * Discription:[Warn with exception and formatter message.]
	 * </p>
	 * 
	 * @param log
	 *            Log
	 * @param e
	 *            Exception
	 * @param formatter
	 * @param args
	 *            use by formatter
	 * @author:[Simon]
	 */
	public static void warn(final Log log, final Throwable e, final String formatter, final Object... args) {
		if (log.isWarnEnabled()) {
			log.warn(MessageUtil.formmatString(formatter, args), e);
		}
	}
}
