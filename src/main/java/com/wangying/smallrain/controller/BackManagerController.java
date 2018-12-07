package com.wangying.smallrain.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.wangying.smallrain.service.BackManagerService;

@Controller
@RequestMapping("/manager/")
public class BackManagerController {

  @Autowired
  private BackManagerService backManagerService;
  
  /**
   * 后台首页,默认进入菜单管理
   * @return
   */
  @RequestMapping("/index")
  public ModelAndView  index() {
    ModelAndView  mv = new ModelAndView("index");
    Map<String, Object>  menus = backManagerService.managerBackMenu("menu-manager");   //加载菜单管理页面的菜单
    //加入菜单数据
    mv.addObject("menus",JSONObject.toJSONString(menus));
    mv.addObject("title","Smallrain Back Manager");
    return mv;
  }
  
  /**
   * 后台菜单管理
   * @return
   */
  @RequestMapping("/user")
  public ModelAndView  menu() {
    ModelAndView  mv = new ModelAndView("user_manager");
    Map<String, Object>  menus = backManagerService.managerBackMenu("");
    //加入菜单数据
    mv.addObject("menus",JSONObject.toJSONString(menus));
    mv.addObject("title","User Manager");
    return mv;
  }
  
}
