package com.wangying.smallrain.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wangying.smallrain.configs.BaseConfig;
import com.wangying.smallrain.entity.Result;
import com.wangying.smallrain.utils.BaseUtils;
import com.wangying.smallrain.utils.ResultUtil;

@Controller
@RequestMapping("/user/")
public class UserController {

  @Autowired
  private BaseConfig baseConfig;

  private Logger log = LoggerFactory.getLogger(UserController.class);

  @RequestMapping("tologin")
  public ModelAndView toLogin() {
    log.info("跳转登录页。。");
    ModelAndView mv = new ModelAndView("tologin");
    mv.addObject("title", "用户登录");
    return mv;
  }

  @RequestMapping(value = "login", method = { RequestMethod.POST })
  @ResponseBody
  public Result login(@RequestParam("username") String username, @RequestParam("password") String password,
      @RequestParam("remberMe") boolean remberMe, HttpServletRequest request) {
    try {
      log.info(BaseUtils.joinString("进入用户登录。。。。。。 参数是： 用户名--",username," 密码--",password," remberMe--",remberMe));
      // 先获取到Subject对象
      Subject subject = SecurityUtils.getSubject();
      // 创建UsernamePasswordToken对象，封装用户名和密码
      UsernamePasswordToken token = new UsernamePasswordToken(username, BaseUtils.md5(password.trim(), ""), remberMe);
      // 使用shiro框架进行校验
      subject.login(token);
      // 获取返回的结果
      // User user = (User) subject.getPrincipal();
      SavedRequest savedRequest = WebUtils.getSavedRequest(request);
      // 返回原始页
      String latUrl = BaseUtils.joinString(baseConfig.getDommainName(), savedRequest.getRequestUrl());
      log.info("跳转回原地址："+latUrl);
      return  ResultUtil.success(latUrl);
    } catch (Exception e) {
      e.printStackTrace();
      log.error("用户登录失败!");
      return ResultUtil.fail("用户登录失败，用户名或者密码错误！");
    }
  }

}
