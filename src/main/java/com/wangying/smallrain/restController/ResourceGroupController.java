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
import com.wangying.smallrain.entity.ResourceGroup;
import com.wangying.smallrain.entity.Result;
import com.wangying.smallrain.entity.query.ResourceQueryEntity;
import com.wangying.smallrain.service.ResourceGroupService;
import com.wangying.smallrain.utils.BaseUtils;
import com.wangying.smallrain.utils.ResultUtil;

/**
 * 资源组控制类
 * @author wangying.dz3
 *
 */
@RestController
@RequestMapping("/api/")
public class ResourceGroupController {

  @Autowired
  private ResourceGroupService resourceGroupService;
  
  private Logger log = LoggerFactory.getLogger(ResourceGroupController.class);
  
  /**
   * 添加一条资源组信息
   * @param config
   * @return
   */
  @PostMapping("resourcegroup")
  public Result add(@RequestBody ResourceGroup resourceGroup) {
    log.info("添加一条资源组信息。。");
    if(null == resourceGroup) {
      return ResultUtil.fail("添加资源组失败，接受到的资源组信息为空！");
    }
    if(resourceGroupService.add(resourceGroup)) {
      return ResultUtil.success("添加资源组成功",resourceGroup);
    }
    return ResultUtil.fail("添加资源组失败，保存到数据库异常");
  }
  
  /**
   * 更新一条资源组信息
   * @param config
   * @return
   */
  @PutMapping("resourcegroup")
  public Result update(@RequestBody ResourceGroup resourceGroup) {
    log.info("更新一条资源组信息。。");
    if(null == resourceGroup) {
      return ResultUtil.fail("更新资源组失败，接受到的资源组信息为空！");
    }
    if(resourceGroupService.update(resourceGroup)) {
      return ResultUtil.success("更新资源组成功",resourceGroup);
    }
    return ResultUtil.fail("更新资源组失败，保存到数据库异常");
  }
  
  /**
   * 删除一条资源组信息
   * @param config
   * @return
   */
  @DeleteMapping("resourcegroup/{id}")
  public Result delete(@PathVariable(name="id") String id) {
    log.info("删除一条资源组信息。。");
    if(BaseUtils.isEmpty(id)) {
      return ResultUtil.fail("删除资源组失败，接受到的 key 值为空！");
    }
    if(resourceGroupService.deleteResourceGroup(id)) {
      return ResultUtil.success("删除资源组成功",id);
    }
    return ResultUtil.fail("删除资源组失败，数据库操作异常");
  }
  
  /**
   * 获取一条资源组信息
   * @param config
   * @return
   */
  @GetMapping("resourcegroup/{id}")
  public Result get(@PathVariable(name="id") String id) {
    if(BaseUtils.isEmpty(id)) {
      return ResultUtil.fail("获取资源组失败，传入的资源id 为空");
    }
    log.info("获取一条资源组信息。。");
    ResourceGroup result = resourceGroupService.getResourceGroup(id);
    if(!BaseUtils.isEmpty(result)) {
      return ResultUtil.success("获取资源组成功",result);
    }
    return ResultUtil.fail("获取资源组失败，数据库操作异常");
  }
  
  /**
   * 获取查询资源组信息
   * @param config
   * @return
   */
  @PostMapping("resourcegroup/query")
  public Result get(@RequestBody(required=false) ResourceQueryEntity resQuery) {
    PageBean<ResourceGroup> pageDate = new PageBean<ResourceGroup>();
    try {
      pageDate = resourceGroupService.getResourceGroupList(resQuery);
    }catch(Exception e) {
      return ResultUtil.exception("查询资源列表失败："+e.getMessage());
    }
    return ResultUtil.success("查询资源列表成功",pageDate);
  }
  
}
