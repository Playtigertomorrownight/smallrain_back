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
  
  @RequestMapping("/index")
  public ModelAndView  index() {
    ModelAndView  mv = new ModelAndView("index");
    Map<String, Object>  menus = backManagerService.managerBackMenu("resource-manager");
    //加入菜单数据
    mv.addObject("menus",JSONObject.toJSONString(menus));
    return mv;
  }
  
}
