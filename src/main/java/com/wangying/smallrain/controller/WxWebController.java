package com.wangying.smallrain.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.wangying.smallrain.configs.BaseConfig;
import com.wangying.smallrain.entity.PageBean;
import com.wangying.smallrain.entity.Resource;
import com.wangying.smallrain.entity.ResourceGroup;
import com.wangying.smallrain.entity.Result;
import com.wangying.smallrain.entity.enums.FileDataType;
import com.wangying.smallrain.entity.query.ResourceQueryEntity;
import com.wangying.smallrain.service.FileService;
import com.wangying.smallrain.service.ResourceGroupService;
import com.wangying.smallrain.service.ResourceService;
import com.wangying.smallrain.utils.BaseUtils;
import com.wangying.smallrain.utils.ResultUtil;

@Controller
@RequestMapping("/wx/web")
public class WxWebController {

  @Autowired
  private ResourceService resourceService;
  
  @Autowired
  private ResourceGroupService resourceGroupService;
  
  @Autowired
  private FileService fileService;
  
  @Autowired
  private BaseConfig baseConfig;
  
  /**
   * 后台首页,默认进入菜单管理
   * @return
   */
  @RequestMapping("/index")
  public ModelAndView index() {
    ModelAndView mv = baseConfig.initModwlAndView("wechat/index");
    mv.addObject("title", "微网站首页");
    return mv;
  }
  
  
  /**
   * 文章列表， 一般是指 markdown 类型的资源
   * @return
   */
  @RequestMapping("/articles")
  public ModelAndView articles(@RequestParam(value="label",required = false) String label ) {
    ModelAndView mv = baseConfig.initModwlAndView("wechat/article_list");
    mv.addObject("title", BaseUtils.joinString(StringUtils.isEmpty(label)?"":label,StringUtils.isEmpty(label)?"全部文章":" 相关文章"));
    PageBean<Resource> pageData = new PageBean<Resource>();
    ResourceQueryEntity resQuery = new ResourceQueryEntity();   //新建查询条件
    resQuery.setPageSize(0);   //查询所有指定标签的资源
    resQuery.setLabel(label);
    resQuery.setType(FileDataType.MARKDOWN.name());   //查询  markdown 类型的资源文件作为文章
    pageData = resourceService.getResourceList(resQuery);
    //查询对应的资源组
    List<Resource> resources = pageData.getItems();
    List<String> ids = new ArrayList<String>();
    JSONObject resourceListShow = new JSONObject();
    for(Resource r : resources) {
      ids.add(r.getGroupId());
      resourceListShow.put(r.getGroupId(), false);
    }
    List<ResourceGroup> rgData = resourceGroupService.selectResourceGroupWithIds(ids);
    mv.addObject("resourceGroupList",JSONObject.toJSONString(rgData)); //资源数据
    mv.addObject("resourceList",JSONObject.toJSONString(resources)); //资源数据
    mv.addObject("resourceListShow",JSONObject.toJSONString(resourceListShow)); //资源是否显示
    return mv;
  }
  
  /**
   * 文章列表， 一般是指 markdown 类型的资源
   * @return
   */
  @RequestMapping("/article")
  public ModelAndView article(@RequestParam(value="resourceId",required = true) String resourceId) {
    ModelAndView mv = baseConfig.initModwlAndView("wechat/article");
    Resource resource = resourceService.getResourceById(resourceId);
    if(null==resource) {
      mv.addObject("title", "An null resource");
      Result result = ResultUtil.fail(BaseUtils.joinString("id 为：",resourceId," 的资源信息为空！"));
      mv.addObject("datas",result);
      return mv;
    }
    mv.addObject("title", BaseUtils.joinString(resource.getName()));
    Result resContent =  fileService.loadFile(resourceId,false);
    mv.addObject("datas",resContent);
    return mv;
  }
  
}
