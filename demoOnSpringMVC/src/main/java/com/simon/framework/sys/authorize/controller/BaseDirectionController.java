/**
 * Title: BaseDirectoryController.java <br>
 * Description:[] <br>
 * Date: 2016.08.09 <br>
 * Copyright (c) 2016 Simon Zhang <br>
 * 
 * @author Simon
 * @version V1.0
 */

package com.simon.framework.sys.authorize.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.simon.framework.base.BaseController;

@RequestMapping
@Controller("baseDirectionController")
public class BaseDirectionController extends BaseController {
	// @RequestMapping({"", "/", "/index"})
	public String baseMain() {
		return "redirect:auth/main";
	}
}
