package com.wangying.smallrain.restController;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangying.smallrain.entity.BaseConfigs;
import com.wangying.smallrain.entity.Result;
import com.wangying.smallrain.service.ConfigService;
import com.wangying.smallrain.utils.BaseUtils;
import com.wangying.smallrain.utils.ResultUtil;

/**
 * 配置信息 控制类
 * @author wangying.dz3
 *
 */
@RestController
@RequestMapping("/api/")
public class ConfigController {
   
  @Autowired
  private ConfigService ConfigService;
  
  private Logger log = LoggerFactory.getLogger(ConfigController.class);
  
  /**
   * 添加一条配置信息
   * @param config
   * @return
   */
  @PostMapping("config")
  public Result add(@RequestBody BaseConfigs config) {
    log.info("添加一条配置信息。。");
    if(null == config || BaseUtils.isEmpty(config.getKey())) {
      return ResultUtil.fail("添加配置失败，接受到的配置信息为空！");
    }
    if(ConfigService.addConfig(config)) {
      return ResultUtil.success("添加配置成功",config);
    }
    return ResultUtil.fail("添加配置失败，保存到数据库异常");
  }
  
  /**
   * 更新一条配置信息
   * @param config
   * @return
   */
  @PutMapping("config")
  public Result update(@RequestBody BaseConfigs config) {
    log.info("更新一条配置信息。。");
    if(null == config || BaseUtils.isEmpty(config.getKey())) {
      return ResultUtil.fail("更新配置失败，接受到的配置信息为空！");
    }
    if(ConfigService.updateConfig(config)) {
      return ResultUtil.success("更新配置成功",config);
    }
    return ResultUtil.fail("更新配置失败，保存到数据库异常");
  }
  
  /**
   * 删除一条配置信息
   * @param config
   * @return
   */
  @DeleteMapping("config/{key}")
  public Result delete(@PathVariable(name="key") String key) {
    log.info("删除一条配置信息。。");
    if(BaseUtils.isEmpty(key)) {
      return ResultUtil.fail("删除配置失败，接受到的 key 值为空！");
    }
    if(ConfigService.deleteOneConfig(key)) {
      return ResultUtil.success("删除配置成功",key);
    }
    return ResultUtil.fail("删除配置失败，数据库操作异常");
  }
  
  /**
   * 获取一条配置信息
   * @param config
   * @return
   */
  @GetMapping("config/{key}")
  public Result get(@PathVariable(name="key") String key) {
    if(BaseUtils.isEmpty(key)) {
      log.info("获取所有配置信息。。");
      List<BaseConfigs> result = ConfigService.selectAllConfig();
      if(!BaseUtils.isEmpty(result)) {
        return ResultUtil.success("获取配置成功",result);
      }
      return ResultUtil.fail("获取配置失败，数据库操作异常");
    }
    log.info("获取一条配置信息。。");
    BaseConfigs result = ConfigService.selectOneConfig(key);
    if(!BaseUtils.isEmpty(result)) {
      return ResultUtil.success("获取配置成功",result);
    }
    return ResultUtil.fail("获取配置失败，数据库操作异常");
  }
  
  
}
