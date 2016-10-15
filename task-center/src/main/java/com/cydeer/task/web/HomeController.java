package com.cydeer.task.web;

import com.cydeer.task.result.AjaxResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhangsong on 16/9/22.
 */
@Controller
public class HomeController {

	@RequestMapping("/home")
	@ResponseBody
	public AjaxResult<String> home(@RequestParam(value = "name") String name) {
		return new AjaxResult<>(name);
	}
}
