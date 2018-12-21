package com.wangying.smallrain.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.wangying.smallrain.entity.Menu;
import com.wangying.smallrain.entity.Result;
import com.wangying.smallrain.entity.enums.MenuPlatform;
import com.wangying.smallrain.service.MenuService;
import com.wangying.smallrain.utils.BaseUtils;
import com.wangying.smallrain.utils.ResultUtil;

@Controller
@RequestMapping("/manager/")
public class BackManagerController {

  @Autowired
  private MenuService menuService;
  
  private Logger log = LoggerFactory.getLogger(BackManagerController.class);
  
  /**
   * 后台首页,默认进入菜单管理
   * @return
   */
  @RequestMapping("/index")
  public ModelAndView  index() {
    
    return menu("","");
  }
  
  /**
   * 后台菜单管理
   * @param platform  待编辑按钮平台
   * @param current   当前按钮
   * @return
   */
  @RequestMapping("/menu")
  public ModelAndView  menu(@RequestParam(value = "platform", required = false)String platform,
                            @RequestParam(value = "current", required = false)String current) {
    log.info("后台菜单管理。。。。");
    ModelAndView  mv = new ModelAndView("menu_manager");  //指定viewname
    mv.addObject("topMenu","menu-manager");   //顶部按钮名称
    if(BaseUtils.isEmptyString(current)) current = "manager-back-menu-top";
    mv.addObject("currentMenu",current);   //当前左部按钮名称
    mv.addObject("title","菜单管理");
    //平台列表
    mv.addObject("platforms",JSONObject.toJSONString(MenuPlatform.list()));
    //管理哪个平台的菜单
    if(BaseUtils.isEmptyString(platform)) {    //设置默认管理菜单平台
      platform  = MenuPlatform.BACKTOP.name();
    }
    MenuPlatform mPlatForm = MenuPlatform.valueOf(platform);
    mv.addObject("menuPlatform",mPlatForm);
    mv.addObject("treeRoot",mPlatForm.platform());
    mv.addObject("effectiveFileds",mPlatForm.fileds());
    //根据顶部所菜单选取需要返回的菜单项
    Map<String, Object>  menus = menuService.getMenuListBytop(MenuPlatform.BACKTOP.name(),"menu-manager");
    //获取待编辑菜单列表
    List<Menu> editMenus = menuService.getMenusOfPlatForm(platform);
    //添加待编辑菜单列表
    mv.addObject("editMenus",JSONObject.toJSONString(editMenus));
    //获取该青苔所有顶部列表
    String topPlatForm = "";
    switch (mPlatForm) {
        case BEFORELEFT:
          topPlatForm = MenuPlatform.BACKTOP.name();
          break ;
        case BACKLEFT:
        default  :
          topPlatForm = MenuPlatform.BACKTOP.name();
          break ;
    }
    List<Menu> topMenus = menuService.getMenusOfPlatForm(topPlatForm);
     //添加待选择顶部菜单列表
    mv.addObject("topMenus",JSONObject.toJSONString(topMenus));
    
    //加入菜单数据
    mv.addObject("menus",JSONObject.toJSONString(menus));
    //当前管理菜单的平台
    return mv;
  }
  
  
  /**
   * 添加或者更新菜单
   * @param menu
   * @return
   */
  @RequestMapping("/menu/add")
  @ResponseBody
  public Result addMenu(@RequestBody Menu menu) {
    log.error("添加菜单数据！");
    if(null == menu) {
      log.error("接收到菜单数据为空！");
      return ResultUtil.exception("接收到菜单数据为空！");
    }
    int count  =  menuService.addMenu(menu);
    if(count <= 0 ) {
      return ResultUtil.fail("添加或更新菜单失败");
    }else {
      return ResultUtil.success("添加或更新菜单成功",menu);
    }
  }
  
  /**
   * 后台资源管理
   * @param platform  待编辑按钮平台
   * @param current   当前按钮
   * @return
   */
  @RequestMapping("/resource")
  public ModelAndView  resource() {
    log.info("后台资源管理。。。。");
    ModelAndView  mv = new ModelAndView("resource_manager");  //指定viewname
    mv.addObject("topMenu","resource-manager");   //顶部按钮名称
    mv.addObject("currentMenu","manager-back-resource-list");   //当前左部按钮名称
    mv.addObject("title","资源管理");
    
    return mv;
  }
  
  
}
