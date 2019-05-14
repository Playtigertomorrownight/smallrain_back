package com.wangying.smallrain.restController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangying.smallrain.configs.ConfigHelper;
import com.wangying.smallrain.entity.Menu;
import com.wangying.smallrain.entity.Result;
import com.wangying.smallrain.service.MenuService;
import com.wangying.smallrain.utils.BaseUtils;
import com.wangying.smallrain.utils.ResultUtil;

/**
 * 菜单控制类
 * 
 * @author 16524
 *
 */
@RestController
@RequestMapping("/api/")
public class MenuController {

	@Autowired
	private MenuService menuService;

	private Logger log = LoggerFactory.getLogger(MenuController.class);

	/**
	 * 添加一条菜单信息
	 * 
	 * @param config
	 * @return
	 */
	@PostMapping("menu")
	public Result add(@RequestBody Menu menu) {
		log.info("添加一条菜单信息。。");
		if (null == menu) {
			return ResultUtil.fail("添加菜单失败，接受到的菜单信息为空！");
		}
		if (menuService.addMenu(menu)) {
			return ResultUtil.success("添加菜单成功", menu);
		}
		return ResultUtil.fail("添加菜单失败，保存到数据库异常");
	}
	
	/**
	 * 更新一条菜单信息
	 * 
	 * @param config
	 * @return
	 */
	@PutMapping("menu")
	public Result update(@RequestBody Menu menu) {
		log.info("更新一条菜单信息。。");
		if (null == menu) {
			return ResultUtil.fail("更新菜单失败，接受到的菜单信息为空！");
		}
		if (menuService.updateMenu(menu)) {
			return ResultUtil.success("更新菜单成功", menu);
		}
		return ResultUtil.fail("更新菜单失败，保存到数据库异常");
	}
	
	/**
	   * 删除一条菜单信息
	   * @param config
	   * @return
	   */
	  @DeleteMapping("menu/{key}")
	  public Result delete(@PathVariable(name="key") String key) {
	    log.info("删除一条菜单信息。。");
	    if(BaseUtils.isEmpty(key)) {
	      return ResultUtil.fail("删除菜单失败，接受到的 key 值为空！");
	    }
	    if(menuService.deleteByMenuiId(key)) {
	      ConfigHelper.BASE_CONFIG_DB.remove(key);
	      return ResultUtil.success("删除菜单成功",key);
	    }
	    return ResultUtil.fail("删除菜单失败，数据库操作异常");
	  }
	  

}
