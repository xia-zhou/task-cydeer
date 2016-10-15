package com.cydeer.task.exception;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhangsong on 15/7/5.
 */
public class GlobalExceptionHandler extends AbstractExceptionHandler implements InitializingBean {
    private static final String DEFAULT_CODE = "GLOBAL_ERROR";
    private static final String DEFAULT_MESSAGE = "服务器繁忙，请稍候重试";
    private static final String DEFAULT_ERROR_VIEW = "error";
    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private String errorPage;

    public GlobalExceptionHandler() {
    }

    public void afterPropertiesSet() throws Exception {
        if (StringUtils.isBlank(this.errorPage)) {
            this.errorPage = DEFAULT_ERROR_VIEW;
        }

    }

    public ModelAndView getModelAndView(Exception ex, HttpServletRequest request, HandlerMethod handler) {
        logger.error("全局异常处理: ", ex);
        return this.createJsonView(DEFAULT_CODE, DEFAULT_MESSAGE, ex, request);
    }

    protected ModelAndView createPageView(String code, String message, Exception ex, HttpServletRequest request) {
        ModelAndView mv = super.createPageView(code, message, ex, request);
        mv.setViewName(this.errorPage);
        return mv;
    }

    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }
}
