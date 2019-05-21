package com.wangying.smallrain.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.wangying.smallrain.entity.Menu;
import com.wangying.smallrain.utils.BaseUtils;

/**
 * 运行时缓存工具类
 * @author wangying.dz3
 *
 */
public class MenuCache {

  
  /**
   * 菜单缓存
   */
  private static Map<String,Menu> MenuCache = new ConcurrentHashMap<String,Menu>();
  
  /**
   * 根据id获取菜单
   * @param id
   * @return
   */
  public static Menu getMenuById(String id) {
    return MenuCache.get(id);
  }
  
  /**
   * 添加获取更新菜单信息到缓存
   * @param id
   * @return
   */
  public static Menu addOrUpdateMenu(Menu menu) {
    if(null==menu||BaseUtils.isEmpty(menu.getId())) return null;
    return MenuCache.put(menu.getId(), menu);
  }
  
  /**
   * 根据菜单ID删除缓存
   * @param id
   * @return
   */
  public static Menu deleteMenu(String id) {
    return MenuCache.remove(id);
  }
  
  
  /**
   * 获取所有缓存菜单
   * @return
   */
  public static List<Menu> getAllMenus() {
    List<Menu> result = new ArrayList<Menu>();
    result.addAll(MenuCache.values());
    return result;
  }
  
  /**
   * 获取对应平台所有缓存菜单
   * @return
   */
  public static List<Menu> getMenusByplatform(String platform) {
    List<Menu> result = new ArrayList<Menu>();
    Collection<Menu> values = MenuCache.values();
    if(BaseUtils.isEmpty(values)) return result;
    for(Menu menu: values) {
      if(platform.equals(menu.getPlatform().name())) {
        result.add(menu);
      }
    }
    return result;
  }
  
  /**
   * 获取对应平台及其菜单名所有缓存菜单
   * @return
   */
  public static List<Menu> getMenusByplatformAndTop(String platform,String topref) {
    List<Menu> result = new ArrayList<Menu>();
    Collection<Menu> values = MenuCache.values();
    if(BaseUtils.isEmpty(values)) return result;
    for(Menu menu: values) {
      if(platform.equals(menu.getPlatform().name())||topref.equals(menu.getTopref())) {
        result.add(menu);
      }
    }
    return result;
  }
  
}
