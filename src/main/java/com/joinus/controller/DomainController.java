package com.joinus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/member/club/*")
@RequiredArgsConstructor
public class DomainController {
	
	
	@RequestMapping(value = "/abcd")
	public void abcd() {
		
	}

}
