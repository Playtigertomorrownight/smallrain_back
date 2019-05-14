package com.wangying.smallrain.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.wangying.smallrain.configs.BaseConfig;
import com.wangying.smallrain.entity.Menu;
import com.wangying.smallrain.entity.PageBean;
import com.wangying.smallrain.entity.Resource;
import com.wangying.smallrain.entity.ResourceGroup;
import com.wangying.smallrain.entity.Result;
import com.wangying.smallrain.entity.enums.FileDataType;
import com.wangying.smallrain.entity.enums.MenuPlatform;
import com.wangying.smallrain.entity.enums.WxMenuType;
import com.wangying.smallrain.entity.query.BaseQueryEntity;
import com.wangying.smallrain.entity.query.ResourceQueryEntity;
import com.wangying.smallrain.service.MenuService;
import com.wangying.smallrain.service.ResourceGroupService;
import com.wangying.smallrain.service.ResourceService;
import com.wangying.smallrain.utils.BaseUtils;
import com.wangying.smallrain.utils.ResultUtil;

/**
 * 后台管理控制器
 * @author 16524
 *
 */
@Controller
@RequestMapping("/back/")
public class BackManagerController {

  @Autowired
  private MenuService menuService;
  @Autowired
  private ResourceService resourceService;
  @Autowired
  private ResourceGroupService resourceGroupService;
  @Autowired
  private BaseConfig baseConfig;
  
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
    ModelAndView  mv = baseConfig.initModwlAndView("back/menu_manager");  //指定viewname
    mv.addObject("topMenu","menu-manager");   //顶部按钮名称
    if(BaseUtils.isEmpty(current)) current = "manager-back-menu-top";
    mv.addObject("currentMenu",current);   //当前左部按钮名称
    mv.addObject("title","菜单管理");
    //平台列表
    mv.addObject("platforms",JSONObject.toJSONString(MenuPlatform.list()));
    //管理哪个平台的菜单
    if(BaseUtils.isEmpty(platform)) {    //设置默认管理菜单平台
      platform  = MenuPlatform.BACKTOP.name();
    }
    mv.addObject("WxMenuType","{}");
    if(platform.equals(MenuPlatform.WECHAT.name())) {     //管理微信公众号按钮需要加上按钮类型
      mv.addObject("WxMenuType",JSONObject.toJSONString(WxMenuType.list()));
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
    //获取该平台所有顶部列表
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
   * 后台资源管理
   * @param current   当前按钮
   * @return
   */
  @RequestMapping("/resource")
  public ModelAndView  resource(@RequestParam(value = "current", required = false)String current) {
    log.info("后台资源管理。。。。");
    ModelAndView  mv = baseConfig.initModwlAndView("back/resource_manager");  //指定viewname
    mv.addObject("topMenu","resource-manager");   //顶部按钮名称
    current = BaseUtils.isEmpty(current)?"manager-back-resource-list":current;
    mv.addObject("currentMenu",current);   //当前左部按钮名称
    //根据顶部所菜单选取需要返回的菜单项
    Map<String, Object>  menus = menuService.getMenuListBytop(MenuPlatform.BACKTOP.name(),"resource-manager");
    //加入菜单数据
    mv.addObject("menus",JSONObject.toJSONString(menus));
    String action = current.substring(current.lastIndexOf("-")+1,current.length());
    mv.addObject("action",action);
    mv.addObject("pageData","{}");
    mv.addObject("resType","{}"); //类型数据
    mv.addObject("resourceGroupData","{}"); //资源组数据
    PageBean<ResourceGroup>  RgpageDate = resourceGroupService.getResourceGroupList(new BaseQueryEntity(1,0));   //获取所有按钮组数据
    mv.addObject("allResourceGroup",JSONObject.toJSONString(RgpageDate.getItems())); //所有按钮组数据
    if("list".equals(action)) {   //资源列表
      mv.addObject("title","资源管理-资源列表");
      Result pageDate = getResourceList(new ResourceQueryEntity());
      mv.addObject("pageData",JSONObject.toJSONString(pageDate.getData())); //资源数据
      mv.addObject("resType",JSONObject.toJSONString(FileDataType.list())); //类型数据
      
    }else if("add".equals(action)){   //资源上传
      mv.addObject("title","资源管理-资源上传");
    }else if("group".equals(action)){
      mv.addObject("title","资源管理-资源组管理");
      Result pageDate = getResourceGroupList(new BaseQueryEntity(1,10));
      mv.addObject("resourceGroupData",JSONObject.toJSONString(pageDate.getData())); //资源组数据
    }else {
      
    }
    
    return mv;
  }
  
  /**
   * 后台资源管理
   * @param current   当前按钮
   * @return
   */
  @RequestMapping("/resource/addOrupdate")
  @ResponseBody
  public Result addOrUpdateResource(@RequestBody(required=true) Resource resource) {
    return resourceService.addOrUpdateResource(resource);
  }
  
  /**
   * 后台资源管理
   * @param platform  待编辑按钮平台
   * @param current   当前按钮
   * @return
   */
  @RequestMapping(value="/resource/list",method = RequestMethod.POST)
  @ResponseBody
  public Result  getResourceList(@RequestBody(required=false) ResourceQueryEntity resQuery) {
    PageBean<Resource> pageDate = new PageBean<Resource>();
    try {
      pageDate = resourceService.getResourceList(resQuery);
    }catch(Exception e) {
      return ResultUtil.exception("查询资源列表失败："+e.getMessage());
    }
    return ResultUtil.success(pageDate);
  }
  
  /**
   * 后台资源管理
   * @param platform  待编辑按钮平台
   * @param current   当前按钮
   * @return
   */
  @RequestMapping(value="/resource-group/list",method = RequestMethod.POST)
  @ResponseBody
  public Result  getResourceGroupList(@RequestBody(required=false) BaseQueryEntity resQuery) {
    PageBean<ResourceGroup> pageDate = new PageBean<ResourceGroup>();
    try {
      pageDate = resourceGroupService.getResourceGroupList(resQuery);
    }catch(Exception e) {
      return ResultUtil.exception("查询资源列表失败："+e.getMessage());
    }
    return ResultUtil.success(pageDate);
  }
  
  /**
   * 后台资源管理
   * @param platform  待编辑按钮平台
   * @param current   当前按钮
   * @return
   */
  @RequestMapping(value="/resource-group/addOrUpdate",method = RequestMethod.POST)
  @ResponseBody
  public Result  resourceGroupAdd(@RequestBody(required=true) ResourceGroup  resourceGroup) {
    try {
      if(null == resourceGroup) {
        return ResultUtil.fail("添加资源组失败,接受到的参数为空！");
      }
      if(resourceGroupService.addOrupdateResourceGroup(resourceGroup)<=0) {
        return ResultUtil.fail("添加资源组失败,数据库操作失败！");
      }
    }catch(Exception e) {
      return ResultUtil.exception("添加资源组异常：" +e.getMessage());
    }
    return ResultUtil.success("添加资源组成功!");
  }
  
  
  /**
   * 后台资源管理
   * @param platform  待编辑按钮平台
   * @param current   当前按钮
   * @return
   */
  @RequestMapping(value="/resource-group/delete",method = RequestMethod.DELETE)
  @ResponseBody
  public Result  resourceGroupDelete(@RequestParam(value="resGroupId",required=true) String  resGroupId) {
    try {
      int count = resourceGroupService.deleteResourceGroup(resGroupId);
      if(count<=0) {
        return ResultUtil.fail("删除资源组失败！");
      }
    }catch(Exception e) {
      return ResultUtil.exception("删除资源组异常：" +e.getMessage());
    }
    return ResultUtil.success("删除资源组成功!");
  }
  
  
}
