package com.wangying.smallrain.entity.enums;

public enum EventType {

  SUBSCRIBE(0,"subscribe"),  //关注事件
  UNSUBSCRIBE(1,"unsubscribe"),  //取关事件
  SCAN(2,"SCAN"),  //二维码扫描事件
  LOCATION(3,"LOCATION"),  //上报地理位置事件 用户同意上报地理位置后，每次进入公众号会话时，都会在进入时上报地理位置，或在进入会话后每5秒上报一次地理位置，公众号可以在公众平台网站中修改以上设置。上报地理位置时，微信会将上报地理位置事件推送到开发者填写的URL。
  CLICK(4,"CLICK"),  //点击菜单拉取消息时的事件推送
  VIEW(5,"VIEW");    //点击菜单跳转链接时的事件推送
  
  private final int code;
  private final String  event;
  
  EventType(int code,String event) {
    this.code = code;
    this.event = event;
  }

  public int code() {
    return code;
  }
  
  public String event() {
    return event;
  }
  
}
