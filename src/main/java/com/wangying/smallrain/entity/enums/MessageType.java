package com.wangying.smallrain.entity.enums;

public enum MessageType {

  TEXT(0,"text"),   //文本消息
  IMAGE(1,"image"), //图片消息
  VOICE(2,"voice"), //语音消息
  VIDEO(3,"video"), //视频消息
  MUSIC(4,"music"), //音乐消息
  NEWS(5,"news"),   //图文消息
  SHORTVIDEO(6,"shortvideo"), //小视频消息
  LOCATION(7,"location"),  //地理位置
  LINK(8,"link"),   //链接消息
  EVENT(9,"event");   //事件消息
  
  
  private final int code;
  private final String  type;
  
  MessageType(int code,String type) {
    this.code = code;
    this.type = type;
  }

  public int code() {
    return code;
  }
  
  public String type() {
    return type;
  }
  
}
