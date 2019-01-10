package com.wangying.smallrain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.wangying.smallrain.dao.ResourceGroupMapper;
import com.wangying.smallrain.entity.PageBean;
import com.wangying.smallrain.entity.ResourceGroup;
import com.wangying.smallrain.entity.query.BaseQueryEntity;
import com.wangying.smallrain.service.ResourceGroupService;
import com.wangying.smallrain.utils.BaseUtils;

@Service
@Transactional
public class ResourceGroupServiceImpl implements ResourceGroupService {

  @Autowired
  private ResourceGroupMapper resourceGroupMapper;
  
  @Override
  public PageBean<ResourceGroup> getResourceGroupList(BaseQueryEntity query) {
    // TODO Auto-generated method stub
    if(query.getPageSize()!=0) {
      PageHelper.startPage(query.getPageNum(), query.getPageSize());
    }
    List<ResourceGroup> resouceList = resourceGroupMapper.selectAllRecords();
    int countNum = resourceGroupMapper.selectAllCount();
    countNum = countNum==0?1:countNum;
    PageBean<ResourceGroup> pageData = new PageBean<>(query.getPageNum(), query.getPageSize()==0?countNum:query.getPageSize(),countNum);
    pageData.setItems(resouceList);
    
    return pageData;
  }

  @Override
  public int deleteResourceGroup(String resGroupId) {
    // TODO Auto-generated method stub
    return resourceGroupMapper.deleteByPrimaryKey(resGroupId);
  }

  @Override
  public int addOrupdateResourceGroup(ResourceGroup resourceGroup) {
    // TODO Auto-generated method stub
    if(BaseUtils.isEmptyString(resourceGroup.getId())) {   //id为空，新建资源组
      resourceGroup.setId(BaseUtils.createUUID());
      resourceGroup.setResourceCount(0);
      return resourceGroupMapper.insert(resourceGroup);
    }else {    //id不为空，更新资源组
      return resourceGroupMapper.updateByPrimaryKeySelective(resourceGroup);
    }
  }

  @Override
  public int updateResourceGroupResCount(int addNum,String rgId) {
    // TODO Auto-generated method stub
    return resourceGroupMapper.addResourceCount(addNum, rgId);
  }

}
