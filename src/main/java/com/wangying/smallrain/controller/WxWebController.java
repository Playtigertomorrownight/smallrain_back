package com.wangying.smallrain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.wangying.smallrain.entity.PageBean;
import com.wangying.smallrain.entity.Resource;
import com.wangying.smallrain.entity.enums.FileDataType;
import com.wangying.smallrain.entity.query.ResourceQueryEntity;
import com.wangying.smallrain.service.ResourceService;

@Controller
@RequestMapping("/wx/web")
public class WxWebController {

  @Autowired
  private ResourceService resourceService;
  
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
    PageBean<Resource> pageDate = new PageBean<Resource>();
    ResourceQueryEntity resQuery = new ResourceQueryEntity();   //新建查询条件
    resQuery.setLabel(label);
    resQuery.setType(FileDataType.MARKDOWN.name());   //查询 markdown
    pageDate = resourceService.getResourceList(resQuery);
    mv.addObject("pageData",JSONObject.toJSONString(pageDate)); //资源数据
    mv.addObject("title", "smalrlain - articles list");
    return mv;
  }
  
}
