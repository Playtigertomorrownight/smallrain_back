package com.wangying.smallrain.dao;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.yaml.snakeyaml.Yaml;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Component
public class LocalConfigDataDao {

  private static final String WX_MENU_CONFIG_PATH = "menu-config/wx_menus.yml";
  private static final String BACK_MANAGER_MENU_CONFIG_PATH = "menu-config/back_menu.yml";
  
  private Logger log = LoggerFactory.getLogger(LocalConfigDataDao.class);
  
  /**
   * 根据配置文件生成微信菜单项
   * @return
   */
  public Map<String,Object> loadWxMenuData() {
    log.info("初始化检查项配置，读取本地菜单配置文件并处理....");
      // 获取配置文件输入流
      log.info("从本地加载微信菜单配置文件");
      JSONObject wxMenuConfig = loadConfigFile(WX_MENU_CONFIG_PATH);
      List<JSONObject> buttons = dealList(wxMenuConfig.getJSONArray("buttons"),"sub_button","all");
      JSONObject result = new JSONObject();
      result.put("buttons", buttons);
      log.info("-----从本地加载微信菜单配置文件成功------");
      return result;
  }
  
  /**
   * 根据配置文件生成管理后台菜单项
   * @return
   */
  public Map<String,Object> loadManagerBackMenuData(String topMenuId) {
    log.info("初始化检查项配置，读取管理后台菜单项配置文件并处理....");
    Map<String,Object> result = new  HashMap<String,Object>();
    try {
      JSONObject managerBackMenuData = loadConfigFile(BACK_MANAGER_MENU_CONFIG_PATH);
      List<JSONObject> topButtons = dealList(managerBackMenuData.getJSONObject("buttons").getJSONArray("top"),"sub","all");
      List<JSONObject> leftButtons = dealList(managerBackMenuData.getJSONObject("buttons").getJSONArray("left"),"sub",topMenuId);
      
      result.put("top", topButtons);
      result.put("left", leftButtons);
      log.info("初始化检查项配置，读取管理后台菜单项配置文件success！");
    }catch(Exception e) {
      e.printStackTrace();
    }
    return result; 
  }
  
  
  /**
   * 将文件转为JSONObject对象方便处理
   * @param path
   * @return
   */
  private JSONObject loadConfigFile(String path) {
    try {
      ClassPathResource resource = new ClassPathResource(path);
      InputStream inputStream = resource.getInputStream();
      Yaml yaml = new Yaml();
      Map<String,Object> Config = yaml.load(inputStream);
      if (null != inputStream)
        inputStream.close();
      JSONObject menus = JSONObject.parseObject(JSONObject.toJSONString(Config));
      return removeNullEntry(menus);
    }catch(Exception e) {
      e.printStackTrace();
      log.info("从本地配置文件加载数据失败");
    }
    return null;
  }
  
  /**
   * 处理包含 parent 属性的列表，将其归入到parent的子列表中
   * @param list
   * @return
   */
  private List<JSONObject> dealList(JSONArray list,String childFiledName,String topMenuId) {
    List<JSONObject> result = new ArrayList<JSONObject>();
    Map<Integer,List<JSONObject>> subs = new HashMap<Integer,List<JSONObject>>();
    List<JSONObject> leftMenus = list.toJavaList(JSONObject.class);
    if(!StringUtils.isEmpty(topMenuId)) {
      // 使用lambda表达式过滤出结果并放到result列表里，written by zhangchao
      if(!"all".equals(topMenuId)) {
        leftMenus = leftMenus.stream()
                .filter((JSONObject b) ->{
                 return  topMenuId.equals(b.getString("topref"));
                }).collect(Collectors.toList());
      }
    }else {
      leftMenus = null;
    }
    //遍历所有菜单项，区别一二级菜单
    if(null==leftMenus||leftMenus.isEmpty()) return result;
    for(int i=0,len = leftMenus.size();i<len;i++) {
      JSONObject item = leftMenus.get(i);
      if(null == item) continue;
      Integer parent = item.getInteger("parent");
      if(-1==parent) {   //一级菜单
        result.add(removeNullEntry(item));
      }else {  //二级菜单
        List<JSONObject> sub = subs.get(parent);
        if(sub==null)
          sub = new ArrayList<JSONObject>();
        sub.add(removeNullEntry(item));
        subs.put(parent, sub);
      }
    }
    Integer index = 0;
    for(JSONObject button:result) {
      List<JSONObject> sub = subs.get(index++);
      if(sub==null)
        sub = new ArrayList<JSONObject>();
      button.put(childFiledName, sub);
    }
    return result;
  }
  
  
  /**
   * 移除属性值为空的属性
   * @param obj
   * @return
   */
  private JSONObject removeNullEntry(JSONObject obj) {
    if(obj == null) return obj;
    for (Iterator<Map.Entry<String, Object>> it = obj.entrySet().iterator(); it.hasNext();){
      Map.Entry<String, Object> item = it.next();
      String key = item.getKey();
      if(StringUtils.isEmpty(obj.getString(key))||"level".equals(key)||"parent".equals(key))
        it.remove();
    }
    return obj;
  }
  
}
