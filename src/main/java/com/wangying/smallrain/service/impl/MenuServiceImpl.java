package com.wangying.smallrain.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.wangying.smallrain.cache.MenuCache;
import com.wangying.smallrain.dao.LocalConfigDataDao;
import com.wangying.smallrain.dao.extend.MenuExtendMapper;
import com.wangying.smallrain.entity.Menu;
import com.wangying.smallrain.entity.enums.MenuPlatform;
import com.wangying.smallrain.entity.enums.WxMenuType;
import com.wangying.smallrain.service.MenuService;
import com.wangying.smallrain.utils.BaseUtils;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

  @Autowired
  private LocalConfigDataDao localConfigDataDao;
  @Autowired
  private MenuExtendMapper menuMapper;

  private Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);

  @Override
  public Menu getMenuById(String menuId) {
    Menu cmenu = MenuCache.getMenuById(menuId);
    if (!BaseUtils.isEmpty(cmenu)) {
      log.info("从缓存根据menuID获取menu");
      return cmenu;
    }
    List<Menu> localList = localConfigDataDao.getlocalBackMenusList(); // 查看本地是否存在，否则直接查询数据库
    for (Menu menu : localList) {
      if (null == menu)
        continue;
      if (menuId.equals(menu.getId())) {
        return menu;
      }
    }
    return menuMapper.selectByPrimaryKey(menuId);
  }

  @Override
  public Map<String, Object> getMenuListBytop(String paltform, String topMenuName) {
    Map<String, Object> result = new HashMap<String, Object>();
    List<Menu> topMenus = new ArrayList<Menu>();
    List<Menu> leftMenus = new ArrayList<Menu>();
    List<Menu> allList = MenuCache.getMenusByplatformAndTop(paltform, topMenuName); // 先取缓存
    if (BaseUtils.isEmpty(allList)) {
      allList = localConfigDataDao.getlocalBackMenusList(); // 加载本地配置的初始化菜单列表
      List<Menu> dbList = menuMapper.selectsMenusByTop(topMenuName, paltform); // 查询数据库里的菜单项
      allList.addAll(dbList);
    }
    for (Menu menu : allList) {
      if (null == menu)
        continue;
      if (paltform.equals(menu.getPlatform().name())) {
        topMenus.add(menu);
      }
      if (topMenuName.equals(menu.getTopref())) {
        leftMenus.add(menu);
      }
    }
    Collections.sort(topMenus);
    Collections.sort(leftMenus);
    result.put("top", dealList(topMenus));
    result.put("left", dealList(leftMenus));
    return result;
  }

  @Override
  public List<Menu> getMenusOfPlatForm(String platform) {
    // TODO Auto-generated method stub
    List<Menu> result = MenuCache.getMenusByplatform(platform); // 先取缓存
    if (BaseUtils.isEmpty(result)) {
      List<Menu> localList = localConfigDataDao.getlocalBackMenusList(); // 加载本地配置的初始化菜单列表
      for (Menu menu : localList) {
        if (null == menu)
          continue;
        if (platform.equals(menu.getPlatform().name())) {
          result.add(menu);
        }
      }
      List<Menu> dbList = menuMapper.selectsMenusByPlatform(platform); // 查询数据库里的菜单项
      if (null != dbList && !dbList.isEmpty())
        result.addAll(dbList);
    }
    Collections.sort(result);
    return dealList(result);
  }

  @Override
  public boolean addMenu(Menu menu) {
    // TODO Auto-generated method stub
    if (BaseUtils.isEmpty(menu.getId())) {
      menu.setId(BaseUtils.createUUID());
    }
    if(menuMapper.insert(menu) != 0) {
      MenuCache.addOrUpdateMenu(menu);
      return true;
    }
    return false;
  }

  @Override
  public boolean updateMenu(Menu menu) {
    // 更新
    if(menuMapper.updateByPrimaryKeySelective(menu) != 0) {
      MenuCache.addOrUpdateMenu(menu);
      return true;
    }
    return false;
  }

  @Override
  public boolean deleteByMenuiId(String MenuId) {
    // TODO Auto-generated method stub
    if(menuMapper.deleteByPrimaryKey(MenuId) != 0) {
      MenuCache.deleteMenu(MenuId);
      return true;
    }
    return false;
  }
  
  @Override
  public JSONObject dealAndLoadWxMenus() {
    // TODO Auto-generated method stub
    JSONObject result = new JSONObject();
    List<Menu> MenuList = MenuCache.getMenusByplatform(MenuPlatform.WECHAT.name());
    if(BaseUtils.isEmpty(MenuList)) {
      MenuList = menuMapper.selectsMenusByPlatform(MenuPlatform.WECHAT.name()); // 查询数据库里的菜单项
    }
    if (null != MenuList && !MenuList.isEmpty()) {
      Collections.sort(MenuList);
      List<JSONObject> wxMenuList = deaWxMenulList(MenuList);
      result.put("button", wxMenuList);
    }
    return result;
  }

  /**
   * 处理包含 parent 属性的列表，将其归入到parent的子列表中
   * 
   * @param list
   * @return
   */
  private List<JSONObject> deaWxMenulList(List<Menu> list) {
    List<JSONObject> result = new ArrayList<JSONObject>();
    Map<String, List<JSONObject>> subs = new HashMap<String, List<JSONObject>>();
    // 遍历所有菜单项，区别一二级菜单
    for (int i = 0, len = list.size(); i < len; i++) {
      Menu item = list.get(i);
      if (null == item)
        continue;
      String parent = item.getParent();
      if ("-1".equals(parent)) { // 一级菜单
        result.add(MenutoWxMenu(item));
      } else { // 二级菜单
        List<JSONObject> sub = subs.get(parent);
        if (sub == null)
          sub = new ArrayList<JSONObject>();
        sub.add(MenutoWxMenu(item));
        subs.put(parent, sub);
      }
    }
    for (JSONObject menu : result) {
      List<JSONObject> sub = subs.get(menu.getString("key"));
      if (sub == null)
        sub = new ArrayList<JSONObject>();
      menu.put("sub_button", sub);
    }
    return result;
  }

  private JSONObject MenutoWxMenu(Menu menu) {
    JSONObject result = new JSONObject();
    WxMenuType menuType = WxMenuType.valueOfType(menu.getType());
    result.put("type", null == menuType ? "" : menuType.type());
    result.put("name", menu.getText());
    result.put("key", menu.getId());
    result.put("url", menu.getUrl());
    result.put("media_id  ", menu.getMediaId());
    result.put("appid", menu.getAppid());
    result.put("pagepath", menu.getPagepath());
    result.put("sub_button", "");
    return BaseUtils.removeNullEntry(result);

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
      if (null == item)
        continue;
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
      List<Menu> sub = subs.get(menu.getId());
      if (sub == null)
        sub = new ArrayList<Menu>();
      menu.setSubMenus(sub);
    }
    return result;
  }

}
