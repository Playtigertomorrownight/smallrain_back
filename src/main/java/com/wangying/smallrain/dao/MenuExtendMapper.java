package com.wangying.smallrain.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.wangying.smallrain.entity.Menu;

/**
 * 菜单mapper 扩展类
 * @author wangying.dz3
 *
 */
@Mapper
public interface MenuExtendMapper extends MenuMapper {

  List<Menu> selectMenusByRoleId(String roleId);
  
}
