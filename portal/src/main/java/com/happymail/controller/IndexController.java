package com.happymail.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class IndexController
{
	@RequestMapping("/")
	public String index()
	{
		log.info("web 启动成功:{}", IndexController.class.toString());
		return "hello world";
	}
}
