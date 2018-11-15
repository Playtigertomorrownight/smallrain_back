package com.wangying.smallrain.external;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.wangying.smallrain.utils.HttpUtil;
import com.wangying.smallrain.utils.WechatUtil;

@Component
public class TulingBotData {
   
  @Value("${tuling.apiUrl}")
  private String apiUrl;
  
  @Value("${tuling.apiKey}")
  private String tlApiKey;
  
   
  private Logger log = LoggerFactory.getLogger(TulingBotData.class);
  
  /**
   * 调用图灵机器人接口返回相关应答,传入文本
   * @param request
   * @return
   */
  public String  tulingRobotTxtResonse(String request, String userId, Location location) {
    log.info("向图灵机器人请求应答。。");
    Map<String,Object> param = initParam(0,userId,request,null,location);
    log.info("向图灵机器人的请求参数："+JSONObject.toJSONString(param));
    //发送请求
    String resultStr = HttpUtil.doPostWithJSON(apiUrl, null, param);  //请求微信
    log.info("向图灵机器人请求应答的结果为： "+ resultStr);
    if(StringUtils.isEmpty(resultStr)) {
      log.info("向图灵机器人请求应答失败，请求结果为空 ！");
      return WechatUtil.FIRST_FAIL_RESPONSE;
    }
   
    return resultStr;
  }
  
 /**
  * 初始化图灵机器人请求参数
 * @param requestType 请求类型 0-文本、1-音频、2-视频
 * @param userId
 * @param text
 * @param medisUrl
 * @param location
 * @return
 */
private Map<String,Object> initParam(int requestType,String userId,String text,String medisUrl,Location location){
   Map<String,Object> param = new HashMap<String,Object>();
   param.put("reqType", requestType);
   //参数信息
   Map<String,Object> perception = new HashMap<String,Object>();
   Map<String,String> source = new HashMap<String,String>();
   switch(requestType) {
      case 0:  //文本
        source.put("text", text);
        perception.put("inputText", source);
        break;
      case 1:  //音频
        source.put("url", medisUrl);
        perception.put("inputImage", source);
        break;
      case 2:  //视频
        source.put("url", medisUrl);
        perception.put("inputMedia", source);
        break;
   }
   if(null!=location) {  //selfInfo 信息
     Map<String,Object> selfInfo = new HashMap<String,Object>();
     selfInfo.put("location", location);
     perception.put("selfInfo", selfInfo);
   }
   param.put("perception", perception);
   //用户信息
   Map<String,Object> userInfo = new HashMap<String,Object>();
   userInfo.put("apiKey", tlApiKey);
   userInfo.put("userId", userId);
   param.put("userInfo", userInfo);
   return param;
  }
  
 
 
 //自定义位置类
 static class Location {
   private String  city;  //城市
   private String  province;  //省份
   private String  street;   //街道
   
  public String getCity() {
    return city;
  }
  public String getProvince() {
    return province;
  }
  public String getStreet() {
    return street;
  }
  public void setCity(String city) {
    this.city = city;
  }
  public void setProvince(String province) {
    this.province = province;
  }
  public void setStreet(String street) {
    this.street = street;
  }
   
 }
 
}
