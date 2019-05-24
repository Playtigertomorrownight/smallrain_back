package com.wangying.smallrain.service;

import java.util.List;

import com.wangying.smallrain.entity.PageBean;
import com.wangying.smallrain.entity.ResourceGroup;
import com.wangying.smallrain.entity.query.BaseQueryEntity;

/**
 * 资源组服务类
 * @author wangying.dz3
 *
 */
public interface ResourceGroupService {

  /**
   * 查询资源组
   * @param query
   * @return
   */
  public PageBean<ResourceGroup> getResourceGroupList(BaseQueryEntity query);
  
  /**
   * 删除一个资源组
   * @param resGroupId
   * @return
   */
  boolean deleteResourceGroup(String resGroupId);
  
  /**
   * 获取一个资源组
   * @param resGroupId
   * @return
   */
  ResourceGroup getResourceGroup(String resGroupId);
  
  /**
   * 获取所有资源组
   * @param resGroupId
   * @return
   */
  List<ResourceGroup> getAllResourceGroup();
  
  /**
   * 添加一个资源组
   * @param resourceGroup
   * @return
   */
  boolean add(ResourceGroup resourceGroup);
  
  /**
   * 更新一个资源组
   * @param resourceGroup
   * @return
   */
  boolean update(ResourceGroup resourceGroup);
  
  /**
   * 更新资源数目
   * @param addNum
   * @param rgId
   * @return
   */
  boolean updateResourceGroupResCount(int addNum,String rgId);
  
  /**
   * 根据id 列表查询资源组
   * @param ids
   * @return
   */
  List<ResourceGroup> selectResourceGroupWithIds(List<String> ids);
  
}
