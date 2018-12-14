package com.wangying.smallrain.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangying.smallrain.dao.LocalConfigDataDao;
import com.wangying.smallrain.dao.MenuExtendMapper;
import com.wangying.smallrain.entity.Menu;
import com.wangying.smallrain.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

  @Autowired
  private LocalConfigDataDao localConfigDataDao;
  @Autowired
  private MenuExtendMapper menuMapper;
  
  @Override
  public Menu getMenuById(String menuId) {
    List<Menu> localList = localConfigDataDao.getlocalBackMenusList();  //查看本地是否存在，否则直接查询数据库
    for(Menu menu :  localList) {
      if(null == menu) continue;
      if(menuId.equals(menu.getId())) {
        return menu;
      }
    }
    return menuMapper.selectByPrimaryKey(menuId);
  }

  @Override
  public Map<String, Object> getMenuListBytop(String paltform,String topMenuName) {
    Map<String, Object> result = new HashMap<String, Object>();
    List<Menu> topMenus = new ArrayList<Menu>();
    List<Menu> leftMenus = new ArrayList<Menu>();
    List<Menu> localList = localConfigDataDao.getlocalBackMenusList();  //加载本地配置的初始化菜单列表
    List<Menu> dbList = menuMapper.selectsMenusByTop(topMenuName, paltform);  //查询数据库里的菜单项
    localList.addAll(dbList);
    for(Menu menu :  localList) {
      if(null == menu) continue;
      if(paltform.equals(menu.getPlatform().name())) {
        topMenus.add(menu);
      }
      if(topMenuName.equals(menu.getTopref())) {
        leftMenus.add(menu);
      }
    }
    result.put("top", dealList(topMenus));
    result.put("left", dealList(leftMenus));
    return result;
  }
  
  
  @Override
  public List<Menu> getMenusOfPlatForm(String platform) {
    // TODO Auto-generated method stub
    List<Menu> result = new ArrayList<Menu>();
    List<Menu> localList = localConfigDataDao.getlocalBackMenusList();  //加载本地配置的初始化菜单列表
    for(Menu menu :  localList) {
      if(null == menu) continue;
      if(platform.equals(menu.getPlatform().name())) {
        result.add(menu);
      }
    }
    List<Menu> dbList = menuMapper.selectsMenusByPlatform(platform);  //查询数据库里的菜单项
    if(null!=dbList&&!dbList.isEmpty())
      result.addAll(dbList);
    return dealList(result);
  }

  /**
   * 处理包含 parent 属性的列表，将其归入到parent的子列表中
   * 
   * @param list
   * @return
   */
  private List<Menu> dealList(List<Menu> list) {
    List<Menu> result = new ArrayList<Menu>();
    Map<String, List<Menu>> subs = new HashMap<String, List<Menu>>();
    // 遍历所有菜单项，区别一二级菜单
    for (int i = 0, len = list.size(); i < len; i++) {
      Menu item = list.get(i);
      if (null == item)  continue;
      String parent = item.getParent();
      if ("-1".equals(parent)) { // 一级菜单
        result.add(item);
      } else { // 二级菜单
        List<Menu> sub = subs.get(parent);
        if (sub == null)
          sub = new ArrayList<Menu>();
        sub.add(item);
        subs.put(parent, sub);
      }
    }
    for (Menu menu : result) {
      List<Menu> sub = subs.get(menu.getParent());
      if (sub == null)
        sub = new ArrayList<Menu>();
      menu.setSubMenus(sub);;
    }
    return result;
  }


}
