package com.wangying.smallrain.restController;

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

import com.wangying.smallrain.entity.PageBean;
import com.wangying.smallrain.entity.Resource;
import com.wangying.smallrain.entity.Result;
import com.wangying.smallrain.entity.query.ResourceQueryEntity;
import com.wangying.smallrain.service.ResourceService;
import com.wangying.smallrain.utils.BaseUtils;
import com.wangying.smallrain.utils.ResultUtil;

/**
 * 资源 控制类
 * @author wangying.dz3
 *
 */
@RestController
@RequestMapping("/api/")
public class ResourceController {
  
  @Autowired
  private ResourceService resourceService;
  
  private Logger log = LoggerFactory.getLogger(ResourceController.class);
  
  /**
   * 添加一条资源信息
   * @param config
   * @return
   */
  @PostMapping("resource")
  public Result add(@RequestBody Resource res) {
    log.info("添加一条资源信息。。");
    if(null==res||BaseUtils.isEmpty(res.getName())) {
      return ResultUtil.fail("资源名称不能为空！");
    }
    if(resourceService.add(res)) {
      return ResultUtil.success("添加资源成功",res);
    }
    return ResultUtil.fail("添加资源失败，保存到数据库异常");
  }
  
  /**
   * 更新一条资源信息
   * @param config
   * @return
   */
  @PutMapping("resource")
  public Result update(@RequestBody Resource res) {
    log.info("更新一条资源信息。。");
    if(null==res||BaseUtils.isEmpty(res.getId())) {
      return ResultUtil.fail("资源名称不能为空！");
    }
    if(resourceService.update(res)) {
      return ResultUtil.success("更新资源成功",res);
    }
    return ResultUtil.fail("更新资源失败，保存到数据库异常");
  }
  
  /**
   * 删除一条资源信息
   * @param config
   * @return
   */
  @DeleteMapping("resource/{id}")
  public Result delete(@PathVariable(name="id") String id) {
    log.info("删除一条资源信息。。");
    if(BaseUtils.isEmpty(id)) {
      return ResultUtil.fail("删除资源失败，接受到的 id 值为空！");
    }
    if(resourceService.delete(id)) {
      return ResultUtil.success("删除资源成功",id);
    }
    return ResultUtil.fail("删除资源失败，数据库操作异常");
  }
  
  /**
   * 获取一条资源信息
   * @param config
   * @return
   */
  @GetMapping("resource/{id}")
  public Result get(@PathVariable(name="id") String id) {
    if(BaseUtils.isEmpty(id)) {
      return ResultUtil.fail("获取资源失败，传入的资源id 为空");
    }
    log.info("获取一条资源信息。。");
    Resource result = resourceService.getResourceById(id);
    if(!BaseUtils.isEmpty(result)) {
      return ResultUtil.success("获取资源成功",result);
    }
    return ResultUtil.fail("获取资源失败，数据库操作异常");
  }
  
  /**
   * 获取一条资源信息
   * @param config
   * @return
   */
  @PostMapping("resource/query")
  public Result get(@RequestBody(required=false) ResourceQueryEntity resQuery) {
    PageBean<Resource> pageDate = new PageBean<Resource>();
    try {
      pageDate = resourceService.getResourceList(resQuery);
    }catch(Exception e) {
      return ResultUtil.exception("查询资源列表失败："+e.getMessage());
    }
    return ResultUtil.success("查询资源列表成功",pageDate);
  }
  
}
