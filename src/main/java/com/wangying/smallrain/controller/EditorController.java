package com.wangying.smallrain.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.wangying.smallrain.configs.BaseConfig;
import com.wangying.smallrain.configs.ConfigHelper;

/**
 * 编辑器控制类
 * @author 16524
 *
 */
@Controller
@RequestMapping("/edit")
public class EditorController {
   
	@Autowired
	  private BaseConfig baseConfig;
	
	private Logger log = LoggerFactory.getLogger(EditorController.class);
	
	  /**
	   * 写文章
	   * @param current   当前按钮
	   * @return
	   */
	  @RequestMapping("")
	  public ModelAndView  write() {
	    log.info("配置管理。。。。");
	    ModelAndView  mv = baseConfig.initModwlAndView("back/markdown_edit");  //指定viewname
	    mv.addObject("title","文本编辑");
	    mv.addObject("currentConfigs",JSONObject.toJSONString(ConfigHelper.getAllCurrentConfig()));
	    return mv;
	  }
}
