package com.wangying.smallrain.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangying.smallrain.entity.Result;
import com.wangying.smallrain.service.MessageService;
import com.wangying.smallrain.service.WechatService;
import com.wangying.smallrain.utils.WechatUtil;


/**
 * 微信基本控制类
 * 
 * @author wangying.dz3
 *
 */
@RestController
@RequestMapping("v1/wx")
public class WeChatControlle {

  @Value("${wechat.token}")
  private String wx_token;
  
  @Autowired
  private WechatService wechatService;
  
  @Autowired
  private MessageService messageService;
  
  private Logger log = LoggerFactory.getLogger(WeChatControlle.class);

  /**
   * 用于微信接口配置验证，以及处理微信服务的消息
   * @param signature
   * @param timestamp
   * @param nonce
   * @param echostr
   * @return
   * @throws IOException 
   */
  @RequestMapping("/verify")
  public String initVerify(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
    req.setCharacterEncoding("UTF-8");
    resp.setCharacterEncoding("UTF-8");
    String echostr = req.getParameter("echostr");   //英语检验改接口是否处于验证状态
    if(!StringUtils.isEmpty(echostr)) {    //接口地址验证
      String nonce = req.getParameter("nonce");
      String timestamp = req.getParameter("timestamp");
      String signature = req.getParameter("signature");
      log.info("----微信接口验证 ------");
      String thereString = WechatUtil.sha1(WechatUtil.sort(wx_token, timestamp, nonce));  //参数加密
      log.info("----加密后的参数是 : "+thereString);
      if(!StringUtils.isEmpty(thereString)&&thereString.equals(signature)) {
        log.info("----微信接口验证成功，原样返回 echostr =="+echostr);
        return echostr;
      }else {
        log.info("----微信接口验证失败，本地加密 =="+thereString+" 原先加密串== "+signature);
      }
    }else {   //收到来自用户的消息或者是事件，转交 service 处理
      if(messageService.responseMessage(req,resp)) {
        log.info("成功回复消息！");
      }else {
        log.info("回复消息失败！");
      }
      return null;
    }
    return " ";
  }
  
  
  @RequestMapping("/token")
  public String entry() {
    String  token  = wechatService.getAccessToken();
    return "token = " + token;
  }
  
  @RequestMapping("/menu/init")
  public Result menuInit() {
    return wechatService.initWxMenus();
  }
  
  @RequestMapping("/menu/query")
  public Result menuQuery() {
    return wechatService.queryWxMenus();
  }
  
  @RequestMapping("/menu/delete")
  public Result menuDelete() {
    return wechatService.deleteWxMenus();
  }
  

}
