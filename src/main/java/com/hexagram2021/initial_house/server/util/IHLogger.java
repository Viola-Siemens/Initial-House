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
public class IHLogger {
	/**
	 * 是否启用调试日志输出喵~
	 */
	public static boolean debugMode = true;
	/**
	 * 模组主日志器实例喵~
	 */
	public static Logger logger = LogManager.getLogger(MODID);

	/**
	 * 按指定日志级别输出对象内容喵~
	 *
	 * @param logLevel 日志级别喵~
	 * @param object 输出对象喵~
	 */
	public static void log(Level logLevel, Object object) {
		logger.log(logLevel, String.valueOf(object));
	}

	/**
	 * 输出错误级别日志喵~
	 *
	 * @param object 输出对象喵~
	 */
	public static void error(Object object) {
		log(Level.ERROR, object);
	}

	/**
	 * 输出信息级别日志喵~
	 *
	 * @param object 输出对象喵~
	 */
	public static void info(Object object) {
		log(Level.INFO, object);
	}

	/**
	 * 输出警告级别日志喵~
	 *
	 * @param object 输出对象喵~
	 */
	public static void warn(Object object) {
		log(Level.WARN, object);
	}

	/**
	 * 以格式化参数形式输出错误日志喵~
	 *
	 * @param message 日志模板喵~
	 * @param params 格式化参数喵~
	 */
	public static void error(String message, Object... params) {
		logger.log(Level.ERROR, message, params);
	}

	/**
	 * 输出带异常堆栈的错误日志喵~
	 *
	 * @param message 错误信息喵~
	 * @param t 异常对象喵~
	 */
	public static void error(String message, Throwable t) {
		logger.log(Level.ERROR, message, t);
	}

	/**
	 * 以格式化参数形式输出信息日志喵~
	 *
	 * @param message 日志模板喵~
	 * @param params 格式化参数喵~
	 */
	public static void info(String message, Object... params) {
		logger.log(Level.INFO, message, params);
	}

	/**
	 * 以格式化参数形式输出警告日志喵~
	 *
	 * @param message 日志模板喵~
	 * @param params 格式化参数喵~
	 */
	public static void warn(String message, Object... params) {
		logger.log(Level.WARN, message, params);
	}

	/**
	 * 在调试模式下输出调试日志喵~
	 *
	 * @param object 输出对象喵~
	 */
	public static void debug(Object object) {
		if(debugMode) {
			log(Level.INFO, "[DEBUG:] " + object);
		}
	}

	/**
	 * 在调试模式下输出格式化调试日志喵~
	 *
	 * @param format 日志模板喵~
	 * @param params 格式化参数喵~
	 */
	public static void debug(String format, Object... params) {
		if(debugMode) {
			info("[DEBUG:] " + format, params);
		}
	}
}
