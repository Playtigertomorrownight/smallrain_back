package com.wangying.smallrain.service;

import java.util.List;
import java.util.Map;

import com.wangying.smallrain.entity.Menu;

public interface MenuService {
  
  /**
   * 根据menuID获取menu
   * @param menuId
   * @return
   */
  
  public Menu getMenuById(String menuId);
  
  /**
   * 根据当前顶部按钮选取次级按钮组
   * @param paltform  顶部菜单平台
   * @param topMenuId
   * @return
   */
  public Map<String,Object> getMenuListBytop(String paltform,String topMenuId);
  
  
  /**
   * 获取某个平台类别下的所有按钮
   * @param paltform
   * @return
   */
  public List<Menu> getMenusOfPlatForm(String platform);
 
}
