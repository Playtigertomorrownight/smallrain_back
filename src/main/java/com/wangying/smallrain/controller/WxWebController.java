package com.wangying.smallrain.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.wangying.smallrain.entity.PageBean;
import com.wangying.smallrain.entity.Resource;
import com.wangying.smallrain.entity.ResourceGroup;
import com.wangying.smallrain.entity.enums.FileDataType;
import com.wangying.smallrain.entity.query.ResourceQueryEntity;
import com.wangying.smallrain.service.ResourceGroupService;
import com.wangying.smallrain.service.ResourceService;

@Controller
@RequestMapping("/wx/web")
public class WxWebController {

  @Autowired
  private ResourceService resourceService;
  @Autowired
  private ResourceGroupService resourceGroupService;
  
  /**
   * 后台首页,默认进入菜单管理
   * @return
   */
  @RequestMapping("/index")
  public ModelAndView index() {
    ModelAndView mv = new ModelAndView("wechat/index");
    mv.addObject("title", "微网站首页");
    return mv;
  }
  
  
  /**
   * 文章列表， 一般是指 markdown 类型的资源
   * @return
   */
  @RequestMapping("/articles")
  public ModelAndView articles(@RequestParam(value="label",required = false) String label ) {
    ModelAndView mv = new ModelAndView("wechat/article_list");
    mv.addObject("title", "smalrlain - articles list");
    PageBean<Resource> pageData = new PageBean<Resource>();
    ResourceQueryEntity resQuery = new ResourceQueryEntity();   //新建查询条件
    resQuery.setPageSize(0);   //查询所有指定标签的资源
    resQuery.setLabel(label);
    resQuery.setType(FileDataType.MARKDOWN.name());   //查询  markdown 类型的资源文件作为文章
    pageData = resourceService.getResourceList(resQuery);
    //查询对应的资源组
    List<Resource> resources = pageData.getItems();
    List<String> ids = new ArrayList<String>();
    for(Resource r : resources) {
      ids.add(r.getGroupId());
    }
    List<ResourceGroup> rgData = resourceGroupService.selectResourceGroupWithIds(ids);
    mv.addObject("resourceGroupList",JSONObject.toJSONString(rgData)); //资源数据
    mv.addObject("resourceList",JSONObject.toJSONString(resources)); //资源数据
    return mv;
  }
  
}
