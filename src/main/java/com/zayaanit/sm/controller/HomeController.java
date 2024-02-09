package com.zayaanit.sm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Zubayer Ahamed
 * @since Feb 9, 2024
 */
@Controller
@RequestMapping({"/", "/home"})
public class HomeController {

	@GetMapping
	public String home(Model model) {
		
		return "index";
	}
}
