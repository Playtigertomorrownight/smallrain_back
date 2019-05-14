package com.wangying.smallrain.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wangying.smallrain.dao.ResourceMapper;
import com.wangying.smallrain.entity.Resource;
import com.wangying.smallrain.entity.query.ResourceQueryEntity;

public interface ResourceExtendMapper extends ResourceMapper {

  /**
   * 获取总记录数
   * @return
   */
  int getAllCount();
  
  /**
   * 插查询所有记录
   * @return
   */
  List<Resource> selectAllRecords();
  
  /**
   * 根据条件获取总记录数
   * @return
   */
  int getCountByQuery(@Param("resQuery")ResourceQueryEntity query);
  
  /**
   * 插查询所有记录
   * @return
   */
  List<Resource> selectRecordsByQuery(@Param("resQuery")ResourceQueryEntity query);
  
}
