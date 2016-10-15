package com.cydeer.task.exception;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhangsong on 15/7/5.
 */
public interface ExceptionHandler {
    ModelAndView getModelAndView(Exception var1, HttpServletRequest var2, HandlerMethod var3);
}
