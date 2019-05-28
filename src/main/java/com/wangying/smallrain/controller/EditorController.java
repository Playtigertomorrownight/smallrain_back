package com.wangying.smallrain.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
	   * 编辑器
	   * @param current   当前按钮
	   * @return
	   */
	  @GetMapping("")
	  public ModelAndView  editor() {
	   return write("","");
	  }
	
	
	  /**
	   * 写文章
	   * @param current   当前按钮
	   * @return
	   */
	  @GetMapping("/{mode}")
	  public ModelAndView  write(@PathVariable("mode") String mode,@RequestParam(value="theme",required=false) String theme) {
	    log.info("文本编辑。。。。");
	    ModelAndView  mv = baseConfig.initModwlAndView("edit");  //指定viewname
	    mv.addObject("title","代码编辑");
	    mv.addObject("mode",ConfigHelper.checkEditorMode(mode));
	    mv.addObject("theme",ConfigHelper.checkEditorTheme(theme));
	    mv.addObject("hint",ConfigHelper.checkEditorHint(mode));
	    return mv;
	  }
}
