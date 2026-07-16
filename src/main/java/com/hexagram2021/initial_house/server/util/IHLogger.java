package com.hexagram2021.initial_house.server.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.hexagram2021.initial_house.InitialHouse.MODID;

/**
 * 模组日志工具类，对 Log4j 日志输出做轻量封装喵~
 *
 * @author liudongyu
 */
@SuppressWarnings("unused")
public final class IHLogger {
	/**
	 * 模组主日志器实例喵~
	 */
	private static final Logger LOGGER = LogManager.getLogger(MODID);

	/**
	 * 输出错误级别日志喵~
	 *
	 * @param object 输出对象喵~
	 */
	public static void error(Object object) {
		LOGGER.log(Level.ERROR, object);
	}

	/**
	 * 输出警告级别日志喵~
	 *
	 * @param object 输出对象喵~
	 */
	public static void warn(Object object) {
		LOGGER.log(Level.WARN, object);
	}

	/**
	 * 输出信息级别日志喵~
	 *
	 * @param object 输出对象喵~
	 */
	public static void info(Object object) {
		LOGGER.log(Level.INFO, object);
	}

	/**
	 * 输出调试级别日志喵~
	 *
	 * @param object 输出对象喵~
	 */
	public static void debug(Object object) {
		LOGGER.log(Level.DEBUG, object);
	}

	/**
	 * 以格式化参数形式输出错误日志喵~
	 *
	 * @param message 日志模板喵~
	 * @param params 格式化参数喵~
	 */
	public static void error(String message, Object... params) {
		LOGGER.log(Level.ERROR, message, params);
	}

	/**
	 * 输出带异常堆栈的错误日志喵~
	 *
	 * @param message 错误信息喵~
	 * @param t 异常对象喵~
	 */
	public static void error(String message, Throwable t) {
		LOGGER.log(Level.ERROR, message, t);
	}

	/**
	 * 以格式化参数形式输出信息日志喵~
	 *
	 * @param message 日志模板喵~
	 * @param params 格式化参数喵~
	 */
	public static void info(String message, Object... params) {
		LOGGER.log(Level.INFO, message, params);
	}

	/**
	 * 以格式化参数形式输出警告日志喵~
	 *
	 * @param message 日志模板喵~
	 * @param params 格式化参数喵~
	 */
	public static void warn(String message, Object... params) {
		LOGGER.log(Level.WARN, message, params);
	}

	/**
	 * 在调试模式下输出格式化调试日志喵~
	 *
	 * @param message 日志模板喵~
	 * @param params 格式化参数喵~
	 */
	public static void debug(String message, Object... params) {
		LOGGER.log(Level.DEBUG, message, params);
	}

	private IHLogger() {
	}
}
