package com.wangying.smallrain.entity.enums;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * 使用菜单的平台
 * @author wangying.dz3
 *
 */
public enum MenuPlatform {
  
 // fileds:  id,name,text,icon,clazz,url,parent,orderNum,type,media_id,appid,pagepath,platform,topref
  
  WECHAT(0,"wechat-menus","微信公众号","id,text,name,url,parent,orderNum,type,media_id,appid,pagepath,platform"),  //微信公众平台
  WECHATWEB(1,"wechat-web-menus","微网站","id,name,text,icon,clazz,url,parent,orderNum,platform,topref"),  //微网站
  BEFORETOP(2,"web-before-top-menus","网站前台头部","id,name,text,icon,clazz,url,parent,orderNum,platform"),  //网站前台头部分菜单
  BEFORELEFT(3,"web-before-left-menus","网站前台头左部","id,name,text,icon,clazz,url,parent,orderNum,platform,topref"),  //网站前台头左部分菜单
  BACKTOP(4,"web-back-top-menus","后台管理头部","id,name,text,icon,clazz,url,parent,orderNum,platform"),  //网站后台头部菜单
  BACKLEFT(5,"web-back-left-menus","后台管理左部","id,name,text,icon,clazz,url,parent,orderNum,platform,topref");  //网站后台头部菜单菜单
  
  private final int code;
  private final String  platform;
  private final String fileds;
  private final String description;
  
  MenuPlatform(int code,String platform,String description,String fileds) {
    this.code = code;
    this.platform = platform;
    this.fileds = fileds;
    this.description = description;
  }
  
  public int code() {
    return code;
  }
  
  public String platform() {
    return platform;
  }
  
  public String fileds() {
    return fileds;
  }
  
  public String  toString(){
    JSONObject result = new JSONObject();
    result.put("code", code);
    result.put("name", this.name());
    result.put("platform", platform);
    result.put("fileds", fileds);
    result.put("description", description);
    return result.toJSONString();
  }
  
  public static List<JSONObject> list(){
    List<JSONObject> result = new ArrayList<JSONObject>();
    MenuPlatform []  platforms = MenuPlatform.values();
    for(MenuPlatform plat : platforms) {
      result.add(JSONObject.parseObject(plat.toString()));
    }
    return result;
  }
  
  
}
