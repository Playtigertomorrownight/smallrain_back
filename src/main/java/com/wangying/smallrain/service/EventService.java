package com.wangying.smallrain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.wangying.smallrain.entity.WxMessage;
import com.wangying.smallrain.entity.enums.EventType;
import com.wangying.smallrain.utils.WechatUtil;


/**
 * 事件管理层
 * @author wangying.dz3
 *
 */
@Service
public class EventService {
 
  /**
   * 根据不同的事件类型处理不同的事件消息
   * @param msg
   * @return
   */
  public List<WxMessage> dealEventMessage(Map<String,String> msg) {
    if(null==msg||msg.isEmpty()) return null;
    String event =  msg.get("Event").toString();
    if(StringUtils.isEmpty(event)) return null;
    EventType etype = EventType.valueOf(event.toUpperCase());
    switch(etype) {
      case SUBSCRIBE:   //关注事件
        return dealSubscribeEvent(msg);
      case UNSUBSCRIBE: //取消关注事件
         return dealUnsubscribeEvent(msg);
      case SCAN:    //二维码扫描事件
        return dealScanEvent(msg);
      case LOCATION:  //上报地理位置事件 
        return  dealLocationEvent(msg);
      case CLICK:  //点击菜单拉取消息时的事件推送
        return  dealClickEvent(msg);
      case VIEW:   //点击菜单跳转链接时的事件推送
        return  dealViewEvent(msg);
    }
    return null;
  }
  
  /**
   * 处理点击菜单跳转链接时的事件推送事件
   *  ToUserName  开发者微信号
      FromUserName  发送方帐号（一个OpenID）
      CreateTime  消息创建时间 （整型）
      MsgType 消息类型，event
      Event 事件类型，VIEW
      EventKey  事件KEY值，设置的跳转URL
   * @return
   */
  private  List<WxMessage>  dealViewEvent(Map<String,String> msg){
    List<WxMessage> list = new ArrayList<WxMessage>();
    return list;
  }
  
  /**
   * 处理关注事件
   *  ToUserName 开发者微信号
      FromUserName  发送方帐号（一个OpenID）
      CreateTime  消息创建时间 （整型）
      MsgType 消息类型，event
      Event 事件类型，subscribe(订阅)、unsubscribe(取消订阅)
   * @return
   */
  private  List<WxMessage>  dealSubscribeEvent(Map<String,String> msg){
    List<WxMessage> list = new ArrayList<WxMessage>();
    return list;
  }
  
  /**
   * 处理取消关注事件
   *  ToUserName 开发者微信号
      FromUserName  发送方帐号（一个OpenID）
      CreateTime  消息创建时间 （整型）
      MsgType 消息类型，event
      Event 事件类型，subscribe(订阅)、unsubscribe(取消订阅)
   * @return
   */
  private  List<WxMessage>  dealUnsubscribeEvent(Map<String,String> msg){
    List<WxMessage> list = new ArrayList<WxMessage>();
    return list;
  }
  
  /**
   * 处理二维码扫描事件事件
   *  ToUserName  开发者微信号
      FromUserName  发送方帐号（一个OpenID）
      CreateTime  消息创建时间 （整型）
      MsgType 消息类型，event
      Event 事件类型，subscribe
      EventKey  事件KEY值，qrscene_为前缀，后面为二维码的参数值
      Ticket  二维码的ticket，可用来换取二维码图片
   * @return
   */
  private  List<WxMessage>  dealScanEvent(Map<String,String> msg){
    List<WxMessage> list = new ArrayList<WxMessage>();
    return list;
  }
  
  /**
   * 处理上报地理位置事件事件事件
   *  ToUserName  开发者微信号
      FromUserName  发送方帐号（一个OpenID）
      CreateTime  消息创建时间 （整型）
      MsgType 消息类型，event
      Event 事件类型，LOCATION
      Latitude  地理位置纬度
      Longitude 地理位置经度
      Precision 地理位置精度
   * @return
   */
  private  List<WxMessage>  dealLocationEvent(Map<String,String> msg){
    List<WxMessage> list = new ArrayList<WxMessage>();
    return list;
  }
   
  /**
   * 处理点击菜单拉取消息时的事件推送事件
   *  ToUserName  开发者微信号
      FromUserName  发送方帐号（一个OpenID）
      CreateTime  消息创建时间 （整型）
      MsgType 消息类型，event
      Event 事件类型，CLICK
      EventKey  事件KEY值，与自定义菜单接口中KEY值对应
   * @return
   */
  private  List<WxMessage>  dealClickEvent(Map<String,String> msg){
    List<WxMessage> list = new ArrayList<WxMessage>();
    list.add(WechatUtil.initTextMessage(msg,"你点击了按钮"));
    return list;
  }
  
}
