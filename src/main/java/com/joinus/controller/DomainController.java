package com.joinus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/member/club/*")
@Controller
public class DomainController {
	
	
	@RequestMapping(value = "/abcd")
	public void abcd() {
		
	}

}
