package com.wangying.smallrain.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.wangying.smallrain.configs.WeChatConfig;
import com.wangying.smallrain.dao.LocalConfigDataDao;
import com.wangying.smallrain.entity.Result;
import com.wangying.smallrain.entity.enums.ResultCode;
import com.wangying.smallrain.utils.HttpUtil;
import com.wangying.smallrain.utils.ResultUtil;

@Service
public class WechatService {

  @Autowired
  private WeChatConfig weChatConfig;
  @Autowired
  LocalConfigDataDao localConfigDataDao;

  private static String access_token = "";
  
  private Logger log = LoggerFactory.getLogger(WechatService.class);
  
  /**
   * 获取微信 access_token ，静态变量缓存 ，定时任务获取
   * @return
   */
  public String getAccessToken() {
    //如果已经存在，直接返回
    log.info("---开始获取access_token---");
    if(!StringUtils.isEmpty(access_token)) {
      log.info("---静态缓存中获得access_token== -" + access_token);
      return access_token;
    }   
    //以下代码块只会执行一次
    //不存在的话就先请求获取
    int expires_in = requestAccessToken();
    if(expires_in==0) { //请求获取失败，返回空
      return "";    
    }
    //开启医德任务
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
          requestAccessToken();
          log.info("定时任务，根据token 有效时间去执行，new token == " + access_token);
        } 
    };
    Timer timer = new Timer();
    //安排指定的任务在指定的时间开始进行重复的固定延迟执行。这里是每 expires_in 秒执行一次
    timer.schedule(timerTask,expires_in*990,expires_in*990);

    return access_token;
  }
  
  
  /**
   * 请求access_token
   */
  private int requestAccessToken() {
  //获取请求token路径
    String request_url = weChatConfig.getAccessTokenUrl();
    //替换参数
    try {
      request_url = request_url.replace("APPID", weChatConfig.getAppId()).replace("APPSECRET", weChatConfig.getAppsecret());
      String resultStr = HttpUtil.doGet(request_url, null, null);  //请求微信
      log.info("请求 access_token 的结果为： "+ resultStr);
      if(!StringUtils.isEmpty(resultStr)){
        JSONObject result = JSONObject.parseObject(resultStr);
        String access_tocken = result.getString("access_token");
        WechatService.access_token = access_tocken;
        int expires_in = result.getIntValue("expires_in");
        return expires_in;
      }
    }catch(Exception e) { 
      log.error("请求微信获取 access_token 出错！");
      e.printStackTrace();
    }
    return 0;
  }
  
  /**
   * 向微信服务发送请求初始化菜单项
   * @return
   */
  public Result initWxMenus() {
    log.info("---开始设置微信公众号菜单---");
    //合成请求地址
    String createUrl = weChatConfig.getMenusCreateUrl();
    if(StringUtils.isEmpty(createUrl)) return ResultUtil.fail(ResultCode.ERROR,"更新菜单项失败，请求地址为空！");
    //获取accesstoken
    String access_token = getAccessToken();
    createUrl = createUrl.replace("ACCESS_TOKEN",access_token);
    //获取本地菜单配置信息
    Map<String,Object> menus = localConfigDataDao.loadMenuData();
    log.info("---本地菜单配置信息： "+menus);
    //发送请求
    Map<String, String> headers = new HashMap<String, String>();
    headers.put("Content-Type", "application/json");
    String resultStr = HttpUtil.doPost(createUrl, headers, menus);  //请求微信
    log.info("请求 微信 初始化 菜单的结果为： "+ resultStr);
    //根据结果返回
    if(StringUtils.isEmpty(resultStr)) ResultUtil.fail(ResultCode.ERROR,"更新菜单项失败，微信服务响应出错！");
    JSONObject result =  JSONObject.parseObject(resultStr);
    if(0==result.getInteger("errcode"))  return ResultUtil.success("更新菜单项成功！");
    else return new Result().setStatus(ResultCode.ERROR).setData(result).setMessage("更新菜单项失败，微信服务响应出错！");
  }
  
  /**
   * 向微信服务发送请求查询菜单项
   * @return
   */
  public Result queryWxMenus() {
    log.info("---开始查询微信公众号菜单---");
    //合成请求地址
    String createUrl = weChatConfig.getMenusGetUrl();
    if(StringUtils.isEmpty(createUrl)) return ResultUtil.fail(ResultCode.ERROR,"查询菜单项失败，请求地址为空！");
    //获取accesstoken
    String access_token = getAccessToken();
    createUrl = createUrl.replace("ACCESS_TOKEN",access_token);
    //发送请求
    String resultStr = HttpUtil.doGet(createUrl, null, null);  //请求微信
    log.info("查询菜单项的结果为： "+ resultStr);
    //根据结果返回
    return ResultUtil.success(resultStr);
    
  }
  
  /**
   * 向微信服务发送请求删除菜单项
   * @return
   */
  public Result deleteWxMenus() {
    log.info("---开始删除微信公众号菜单---");
    //合成请求地址
    String createUrl = weChatConfig.getMenusDeleteUrl();
    if(StringUtils.isEmpty(createUrl)) return ResultUtil.fail(ResultCode.ERROR,"删除菜单项失败，请求地址为空！");
    //获取accesstoken
    String access_token = getAccessToken();
    createUrl = createUrl.replace("ACCESS_TOKEN",access_token);
    //发送请求
    String resultStr = HttpUtil.doGet(createUrl, null, null);  //请求微信
    log.info("删除菜单项的结果为： "+ resultStr);
    //根据结果返回
    return ResultUtil.success(resultStr);
    
  }
  
  
}
