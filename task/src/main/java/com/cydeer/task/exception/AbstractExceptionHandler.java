package com.cydeer.task.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhangsong on 15/7/5.
 */
public abstract class AbstractExceptionHandler  implements ExceptionHandler {
    public AbstractExceptionHandler() {
    }

    protected ModelAndView createJsonView(String code, String message, Exception ex, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        jsonView.addStaticAttribute("code", code);
        jsonView.addStaticAttribute("success", Boolean.valueOf(false));
        jsonView.addStaticAttribute("message", message);
        jsonView.addStaticAttribute("data", ExceptionUtils.getStackTrace(ex));
        mav.setView(jsonView);
        return mav;
    }

    protected ModelAndView createPageView(String code, String message, Exception ex, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("code", code);
        mv.addObject("message", message);
        mv.addObject("exception", ExceptionUtils.getStackTrace(ex));
        return mv;
    }
}
