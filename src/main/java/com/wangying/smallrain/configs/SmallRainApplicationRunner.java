package com.wangying.smallrain.configs;

import static com.wangying.smallrain.configs.ConfigHelper.getValue;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.wangying.smallrain.cache.MenuCache;
import com.wangying.smallrain.dao.LocalConfigDataDao;
import com.wangying.smallrain.dao.extend.MenuExtendMapper;
import com.wangying.smallrain.entity.BaseConfigs;
import com.wangying.smallrain.entity.Menu;
import com.wangying.smallrain.ftp.FTPClientPool;
import com.wangying.smallrain.service.ConfigService;
import com.wangying.smallrain.service.WechatService;
import com.wangying.smallrain.utils.BaseUtils;

/**
 * @author wangying.dz3 程序启动初始化操作
 */
@Component
@Order(1) // 通过order值的大小来决定启动的顺序
public class SmallRainApplicationRunner implements CommandLineRunner {
  
  @Value("${app.smallrain.tempFileDir}")
  private String tempFileDir;
  
  @Autowired
  private WechatService wechatService;
  
  @Autowired
  private FTPClientPool ftpClientPool;
  
  @Autowired
  private ConfigService ConfigService;
  
  @Autowired
  private LocalConfigDataDao localConfigDataDao;
  
  @Autowired
  private MenuExtendMapper menuMapper;
  
  private Logger log = LoggerFactory.getLogger(SmallRainApplicationRunner.class);

  @Override
  public void run(String... args) throws Exception {
    log.info("程序启动完成后首先需要执行的初始化操作");
    BaseUtils.TEMP_FILE_DIR = tempFileDir;
    //初始化token 操作
    log.info("-- 1. 获取 token 操作 --");
    String access_token = wechatService.getAccessToken();
    log.info("-- 1. 获取到的 token 是:"+access_token);
    
    if(!Boolean.valueOf(getValue("FTP_ISLOCAL"))) {
      log.info("-- 开始初始化 ftp 连接池 --");
      ftpClientPool.initPool();
      log.info("-- 初始化 ftp 连接池完毕 --");
    }
    //初始化配置文件缓存
    initDbConfigValues();
    //初始化菜单数据缓存
    initMenuCache();
  }
  
  /**
   * 初始化数据库配置项的值
   */
  private void initDbConfigValues() {
    log.info("初始化数据库中配置项的值。。");
    List<BaseConfigs> result = ConfigService.selectAllConfig();
    if(!BaseUtils.isEmpty(result)) {
      for(BaseConfigs config: result) {
        if(BaseUtils.isEmpty(config)||BaseUtils.isEmpty(config.getKey()))  continue;  //无效的配置项
        log.info(BaseUtils.joinString("加载数据库配置项：",config.getKey(), "  --  ", config.getValue()));
        ConfigHelper.BASE_CONFIG_DB.put(config.getKey(), config);
      }
      log.info("初始化数据库中配置项的值完成");
    }else {
      log.info("初始化数据库中配置项的值为空！");
    }
  }
  
  private void  initMenuCache() {
    List<Menu> allList = localConfigDataDao.getlocalBackMenusList(); // 加载本地配置的初始化菜单列表
    if(BaseUtils.isEmpty(allList)) {
      allList = new ArrayList<Menu>();
    }
    log.info("加载本地菜单完成");
    allList.addAll(menuMapper.selectsAllMenus()); // 查询数据库里的菜单项
    log.info("加载数据库菜单完成");
    if(!BaseUtils.isEmpty(allList)) {
      for(Menu menu: allList) {
        MenuCache.addOrUpdateMenu(menu);
      }
      log.info("缓存菜单数据完成。。");
    }
  }
  
}
