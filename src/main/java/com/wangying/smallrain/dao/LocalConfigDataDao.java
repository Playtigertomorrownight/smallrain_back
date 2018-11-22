package com.wangying.smallrain.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

  private static final String MENU_CONFIG_PATH = "wx-config/menus.yml";
  
  private Logger log = LoggerFactory.getLogger(LocalConfigDataDao.class);
  
  /**
   * 根据配置文件生成微信菜单项
   * @return
   */
  public Map<String,Object> loadWxMenuData() {
    log.info("初始化检查项配置，读取本地菜单配置文件并处理....");
    try {
      // 获取配置文件输入流
      log.info("从本地加载微信菜单配置文件");
      ClassPathResource resource = new ClassPathResource(MENU_CONFIG_PATH);
      InputStream inputStream = resource.getInputStream();
      Yaml yaml = new Yaml();
      Map<String,Object> wxMenuConfig = yaml.load(inputStream);
      if (null != inputStream)
        inputStream.close();
      JSONArray menus = JSONArray.parseArray(JSONObject.toJSONString(wxMenuConfig.get("buttons")));
      List<JSONObject> buttons = new ArrayList<JSONObject>();
      Map<Integer,List<JSONObject>> subs = new HashMap<Integer,List<JSONObject>>();
      //遍历所有菜单项，区别一二级菜单
      for(int i=0,len = menus.size();i<len;i++) {
        JSONObject menu = menus.getJSONObject(i);
        if(null == menu) continue;
        Integer parent = menu.getInteger("parent");
        if(-1==parent) {   //一级菜单
          buttons.add(removeNullEntry(menu));
        }else {  //二级菜单
          List<JSONObject> sub = subs.get(parent);
          if(sub==null)
            sub = new ArrayList<JSONObject>();
          sub.add(removeNullEntry(menu));
          subs.put(parent, sub);
        }
      }
      Integer index = 0;
      for(JSONObject button:buttons) {
        List<JSONObject> sub = subs.get(index++);
        if(sub==null)
          sub = new ArrayList<JSONObject>();
        button.put("sub_button", sub);
      }
      JSONObject result = new JSONObject();
      result.put("button", buttons);
      log.info("-----从本地加载微信菜单配置文件成功------");
      return result;
    } catch (IOException e) {
      // TODO Auto-generated catch block
      log.error("-----从本地加载微信菜单配置文件失败------");
      e.printStackTrace();
    }
    return null;
  }
  
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
