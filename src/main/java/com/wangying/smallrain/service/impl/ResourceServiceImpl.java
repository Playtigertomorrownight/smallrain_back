package com.wangying.smallrain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.wangying.smallrain.configs.BaseConfig;
import com.wangying.smallrain.dao.extend.ResourceExtendMapper;
import com.wangying.smallrain.entity.PageBean;
import com.wangying.smallrain.entity.Resource;
import com.wangying.smallrain.entity.Result;
import com.wangying.smallrain.entity.query.ResourceQueryEntity;
import com.wangying.smallrain.service.ResourceService;
import com.wangying.smallrain.utils.BaseUtils;
import com.wangying.smallrain.utils.ResultUtil;
@Service
@Transactional
public class ResourceServiceImpl implements ResourceService {

  @Autowired
  private ResourceExtendMapper resourceMapper;
  
  @Autowired
  private BaseConfig baseConfig;
  
  @Override
  public PageBean<Resource> getResourceList(ResourceQueryEntity query) {
    // TODO Auto-generated method stub
    query = null==query?new ResourceQueryEntity():query;
    if(query.getPageSize()>0) {
      PageHelper.startPage(query.getPageNum(), query.getPageSize());  //mapper方法调用之前进行分页设置
    }
    List<Resource> resouceList = resourceMapper.selectRecordsByQuery(query);
    int countNum = resourceMapper.getCountByQuery(query);
    countNum = countNum==0?1:countNum;
    PageBean<Resource> pageData = new PageBean<>(query.getPageNum(), query.getPageSize()==0?countNum:query.getPageSize(),countNum);
    pageData.setItems(resouceList);
    
    return pageData;
  }

  @Override
  public Result addOrUpdateResource(Resource res) {
    // TODO Auto-generated method stub
    if(null==res||BaseUtils.isEmptyString(res.getName())) {
      return ResultUtil.fail("资源名称不能为空！");
    }
    if(BaseUtils.isEmptyString(res.getGroupId())) {
      res.setGroupId(baseConfig.getDefaultResourceGroup());
    }
    String operate = "新建资源成功！";
    if(BaseUtils.isEmptyString(res.getId())) {   //ID为空，新建资源
       res.setId(BaseUtils.createUUID());
       if(resourceMapper.insert(res)<=0) {
         return ResultUtil.fail("新建资源信息是失败！");
       }
    }else {   //更新资源
      Resource targetRes = resourceMapper.selectByPrimaryKey(res.getId());
      BaseUtils.copyNotNullProperties(res, targetRes,"id");  //将出了ID的非空属性复制到原对象
      if(resourceMapper.updateByPrimaryKey(targetRes)<=0) {
        return ResultUtil.fail("新建资源信息成功！");
      }
      operate = "更新资源信息成功";
    }
    return ResultUtil.success(operate);
  }

}
