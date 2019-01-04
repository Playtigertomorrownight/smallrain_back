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
import com.wangying.smallrain.entity.Menu;

@Component
public class LocalConfigDataDao {

  private static final String WX_MENU_CONFIG_PATH = "menu-config/wx_menus.yml";
  private static final String BACK_MANAGER_MENU_CONFIG_PATH = "menu-config/back_menu.yml";
  public static List<Menu> localBackMenusList = null;

  private Logger log = LoggerFactory.getLogger(LocalConfigDataDao.class);

  /**
   * 根据配置文件生成微信菜单项
   * 
   * @return
   */
  public Map<String, Object> loadWxMenuData() {
    log.info("初始化检查项配置，读取本地菜单配置文件并处理....");
    // 获取配置文件输入流
    log.info("从本地加载微信菜单配置文件");
    JSONObject wxMenuConfig = loadConfigFile(WX_MENU_CONFIG_PATH);
    List<JSONObject> buttons = dealList(wxMenuConfig.getJSONArray("buttons"), "sub_button", "all");
    JSONObject result = new JSONObject();
    result.put("button", buttons);
    log.info("-----从本地加载微信菜单配置文件成功------");
    return result;
  }

  /**
   * 根据配置文件生成菜单列表
   * 
   * @return
   */
  public List<Menu> getlocalBackMenusList() {

    try {
      log.info("获取本地配置的菜单列表。。。");
      if (null != localBackMenusList && !localBackMenusList.isEmpty()) {
        return localBackMenusList;
      } else {
        localBackMenusList = new ArrayList<Menu>();
        JSONObject managerBackMenuData = loadConfigFile(BACK_MANAGER_MENU_CONFIG_PATH);
        if (null == managerBackMenuData || managerBackMenuData.isEmpty()) {
          log.info("获取本地配置的菜单列表失败！");
        }
        JSONArray topmenus = managerBackMenuData.getJSONObject("buttons").getJSONArray("top");
        for (int i = 0; i < topmenus.size(); i++) {
          JSONObject menu = topmenus.getJSONObject(i);
          if (null == menu)
            continue;
          localBackMenusList.add(JSONObject.parseObject(menu.toJSONString(), Menu.class));
        }
        JSONArray leftmenus = managerBackMenuData.getJSONObject("buttons").getJSONArray("left");
        for (int i = 0; i < leftmenus.size(); i++) {
          JSONObject menu = leftmenus.getJSONObject(i);
          if (null == menu)
            continue;
          localBackMenusList.add(JSONObject.parseObject(menu.toJSONString(), Menu.class));
        }
        return localBackMenusList;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 将文件转为JSONObject对象方便处理
   * 
   * @param path
   * @return
   */
  private JSONObject loadConfigFile(String path) {
    try {
      ClassPathResource resource = new ClassPathResource(path);
      InputStream inputStream = resource.getInputStream();
      Yaml yaml = new Yaml();
      Map<String, Object> Config = yaml.load(inputStream);
      if (null != inputStream)
        inputStream.close();
      JSONObject menus = JSONObject.parseObject(JSONObject.toJSONString(Config));
      return removeNullEntry(menus);
    } catch (Exception e) {
      e.printStackTrace();
      log.info("从本地配置文件加载数据失败");
    }
    return null;
  }

  /**
   * 处理包含 parent 属性的列表，将其归入到parent的子列表中
   * 
   * @param list
   * @return
   */
  private List<JSONObject> dealList(JSONArray list, String childFiledName, String topMenuName) {
    List<JSONObject> result = new ArrayList<JSONObject>();
    Map<Integer, List<JSONObject>> subs = new HashMap<Integer, List<JSONObject>>();
    List<JSONObject> leftMenus = list.toJavaList(JSONObject.class);
    if (!StringUtils.isEmpty(topMenuName)) {
      // 使用lambda表达式过滤出结果并放到result列表里，written by zhangchao
      if (!"all".equals(topMenuName)) {
        leftMenus = leftMenus.stream().filter((JSONObject b) -> {
          return topMenuName.equals(b.getString("topref"));
        }).collect(Collectors.toList());
      }
    } else {
      leftMenus = null;
    }
    // 遍历所有菜单项，区别一二级菜单
    if (null == leftMenus || leftMenus.isEmpty())
      return result;
    for (int i = 0, len = leftMenus.size(); i < len; i++) {
      JSONObject item = leftMenus.get(i);
      if (null == item)
        continue;
      Integer parent = item.getInteger("parent");
      if (-1 == parent) { // 一级菜单
        result.add(removeNullEntry(item));
      } else { // 二级菜单
        List<JSONObject> sub = subs.get(parent);
        if (sub == null)
          sub = new ArrayList<JSONObject>();
        sub.add(removeNullEntry(item));
        subs.put(parent, sub);
      }
    }
    Integer index = 0;
    for (JSONObject button : result) {
      List<JSONObject> sub = subs.get(index++);
      if (sub == null)
        sub = new ArrayList<JSONObject>();
      button.put(childFiledName, sub);
    }
    return result;
  }

  /**
   * 移除属性值为空的属性
   * 
   * @param obj
   * @return
   */
  private JSONObject removeNullEntry(JSONObject obj) {
    if (obj == null)
      return obj;
    for (Iterator<Map.Entry<String, Object>> it = obj.entrySet().iterator(); it.hasNext();) {
      Map.Entry<String, Object> item = it.next();
      String key = item.getKey();
      if (StringUtils.isEmpty(obj.getString(key)) || "level".equals(key) || "parent".equals(key))
        it.remove();
    }
    return obj;
  }

}
