package com.wangying.smallrain.entity.enums;

/**
 * 使用菜单的平台
 * @author wangying.dz3
 *
 */
public enum MenuPlatform {

  WECHAT(0,"wechat"),  //微信公众平台
  WECHATWEB(1,"wechat-web"),  //微网站
  BEFORETOP(2,"web-before-top"),  //网站前台头部分菜单
  BEFORELEFT(3,"web-before-left"),  //网站前台头左部分菜单
  BACKTOP(4,"web-back-top"),  //网站后台头部菜单
  BACKLEFT(5,"web-back-left");  //网站后台左部分菜单
  
  private final int code;
  private final String  platform;
  
  MenuPlatform(int code,String platform) {
    this.code = code;
    this.platform = platform;
  }
  
  public int code() {
    return code;
  }
  
  public String platform() {
    return platform;
  }
  
}
