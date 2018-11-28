package com.wangying.smallrain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user/")
public class UserController {

  @RequestMapping("tologin")
  public ModelAndView toLogin() {
    ModelAndView  mv = new ModelAndView("tologin");
    mv.addObject("title", "用户登录");
    return mv;
  }
  
}
