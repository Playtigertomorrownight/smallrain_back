package com.wangying.smallrain.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.wangying.smallrain.entity.Menu;

/**
 * 菜单mapper 扩展类
 * @author wangying.dz3
 *
 */
@Mapper
public interface MenuExtendMapper extends MenuMapper {

  /**
   * 根据角色ID获取菜单列表
   * @param roleId
   * @return
   */
  List<Menu> selectMenusByRoleId(@Param("roleId") String roleId);
  
  /**
   * 根据顶部按钮明和平台名获取当前需要显示的所有菜单
   * @param topref
   * @param platform
   * @return
   */
  List<Menu> selectsMenusByTop(@Param("topref") String topref,@Param("platform") String platform);
  
  /**
   * 根据平台查询平台下所有菜单
   * @param topref
   * @param platform
   * @return
   */
  List<Menu> selectsMenusByPlatform(@Param("platform") String platform);
  
}
