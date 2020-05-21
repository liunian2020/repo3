package com.strive.springcloud.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import com.strive.springcloud.common.response.ResponseResult;
import com.strive.springcloud.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CommonExceptionHandler {

	private final static Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);

	@SuppressWarnings("rawtypes")
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ResponseResult<Object> handel(Exception e) {
		if (e instanceof BusinessException) {
			BusinessException businessException = (BusinessException) e;
			logger.error("[业务异常] {}", businessException.getMsg() == null ? "" : businessException.getMsg());
			return ResponseUtil.error(businessException.getErrorCode() == null ? -1 : businessException.getErrorCode(),
					businessException.getMsg() == null ? "[业务异常]" : "[业务异常]" + businessException.getMsg());
		} else {
			logger.error("[系统异常] {}", e);
			//
			// return ResponseUtil2.error(-1, "[系统异常]" + getStackTrace(e));
			return ResponseUtil.error(-1, "[系统异常]" + e.getMessage());
		}
	}

	public String getExceptionDetail(Exception e) {

		StringBuffer stringBuffer = new StringBuffer(e.toString() + "\n");

		StackTraceElement[] messages = e.getStackTrace();

		int length = messages.length;

		for (int i = 0; i < length; i++) {

			stringBuffer.append("\t" + messages[i].toString() + "\n");

		}

		return stringBuffer.toString();

	}

	/**
	 * 获取详细的异常链信息--精准定位异常位置
	 * 
	 * @param aThrowable
	 * 
	 * @return
	 * 
	 */
	public static String getStackTrace(Throwable aThrowable) {

		final Writer result = new StringWriter();

		final PrintWriter printWriter = new PrintWriter(result);

		aThrowable.printStackTrace(printWriter);

		return result.toString();

	}
}
