package com.carrot.styleshare.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StyleController {
	// 화면이동
		//메인화면
		@GetMapping({ "", "/", "/list" })
		public String posts(Model model) {
			
			return "/style/list";
		}
}
