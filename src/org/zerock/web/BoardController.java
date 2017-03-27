package org.zerock.web;

import java.lang.reflect.Method;

import org.zerock.anno.GetMapping;

public class BoardController {

	@GetMapping("register.do")
	public void registerGet() {
		System.out.println("register get");
	}

	public void registerPost() {
		System.out.println("register Post");
	}

	

}
