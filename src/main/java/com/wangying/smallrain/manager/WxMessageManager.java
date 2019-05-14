package com.wangying.smallrain.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangying.smallrain.entity.WxMessage;
import com.wangying.smallrain.utils.WechatUtil;

/**
 * 微信消息合成管理类
 * @author wangying.dz3
 *
 */
@Component
public class WxMessageManager {
  
  private Logger log = LoggerFactory.getLogger(WxMessageManager.class);
  
  /**
   * 处理图灵机器人返回结果
   * @param resultStr  图灵机器人返回结果
   * @param msg  微信消息信息
   * @return
   */
  public List<WxMessage> dealTulingResult(String resultStr,Map<String,String> msg) {
    List<WxMessage> rsult = new ArrayList<WxMessage>();
    try {
      JSONObject resultData = JSONObject.parseObject(resultStr);
      JSONArray results = resultData.getJSONArray("results");
      Map<Integer,WxMessage> msgMap = new HashMap<Integer,WxMessage>();
      for(int i=0,len=results.size();i<len;i++) {
        JSONObject result = results.getJSONObject(i);
        if(null==result) continue;
        String resultType = result.getString("resultType");
        Integer groupType = result.getInteger("groupType");
        //目前只处理文本
        JSONObject values = result.getJSONObject("values");
        String value = "";
        switch(resultType) {
          case "text":  //文本
              value = null==values?WechatUtil.ERROR_RESPONSE:values.getString("text");
            break;
          case "url":   //链接
              value = null==values?WechatUtil.ERROR_RESPONSE:values.getString("url");
            break;
          /*case "voice":  //音频
            break;
          case "video":  //视频
            break;
          case "image":  //图片
            break;
          case "news":   //图文
            break;*/
           default:
             value = WechatUtil.ERROR_RESPONSE;
        }
        WxMessage singlemsg = msgMap.get(groupType);
        if(null == singlemsg) {
          singlemsg = WechatUtil.initTextMessage(msg,value);
        }
        else {
          singlemsg.setContent(singlemsg.getContent()+value);
        }
        msgMap.put(groupType,singlemsg);
      }
      
      rsult.addAll(msgMap.values());
      
    }catch(Exception e) {
      log.info("解析向图灵机器人请求应答失败");
      e.printStackTrace();
      rsult.add(WechatUtil.initTextMessage(msg,WechatUtil.ERROR_RESPONSE));
    }
    return rsult;
  }
  
  
}
