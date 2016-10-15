package com.cydeer.task.exception;

import com.cydeer.task.result.AjaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhangsong on 15/7/5.
 */
public class AjaxExceptionHandler extends AbstractExceptionHandler {
	private static Logger logger = LoggerFactory.getLogger(AjaxExceptionHandler.class);

	public AjaxExceptionHandler() {
	}

	public ModelAndView getModelAndView(Exception ex, HttpServletRequest request, HandlerMethod handler) {
		if (ex instanceof AjaxException) {
			AjaxException e = (AjaxException) ex;
			String code = e.getCode();
			String message = e.getMessage();
			logger.info("Ajax异常处理：code=[{}], msg=[{}]", code, message);
			return this.createJsonView(code, message, ex, request);
		} else {
			return null;
		}
	}
}