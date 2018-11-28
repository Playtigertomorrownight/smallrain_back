package com.wangying.smallrain.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.wangying.smallrain.dao.MenuExtendMapper;
import com.wangying.smallrain.dao.RoleMapper;
import com.wangying.smallrain.dao.UserExtendMapper;
import com.wangying.smallrain.entity.Menu;
import com.wangying.smallrain.entity.Role;
import com.wangying.smallrain.entity.User;

public class MyRealm extends AuthorizingRealm {
  
  @Autowired
  private UserExtendMapper userMapper;
  
  @Autowired
  private RoleMapper roleMapper;

  @Autowired
  private MenuExtendMapper menuMapper;

  /* (non-Javadoc)授权，即权限验证
   * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
    // TODO Auto-generated method stub
    String userName=(String) SecurityUtils.getSubject().getPrincipal();

    //User user=userRepository.findByUserName(userName);
    //根据用户名查询出用户记录
    User user = userMapper.selectByUserName(userName);

    SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();

    //List<Role> roleList=roleRepository.findByUserId(user.getId());
    List<Role> roleList = roleMapper.selectRolesByUserId(user.getId());

    Set<String> roles=new HashSet<String>();
    if(roleList.size()>0){
        for(Role role:roleList){
            roles.add(role.getName());
            //根据角色id查询所有资源
            List<Menu> menuList = menuMapper.selectMenusByRoleId(role.getId());
            for(Menu menu:menuList){
                info.addStringPermission(menu.getName()); // 添加权限
            }
        }
    }
    info.setRoles(roles);
    return info;
  }

  /* (non-Javadoc)身份认证/登录
   * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    String userName = (String)token.getPrincipal();
    //User user=userRepository.findByUserName(userName);
    //根据用户名查询出用户记录
    User user = userMapper.selectByUserName(userName);
    if(user!=null){
        AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(user.getName(),user.getPassword(),"xxx");
        return authcInfo;
    }else{
        return null;
    }
  }

}
