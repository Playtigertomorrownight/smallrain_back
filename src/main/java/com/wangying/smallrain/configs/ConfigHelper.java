package com.wangying.smallrain.configs;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.yaml.snakeyaml.Yaml;

import com.wangying.smallrain.entity.BaseConfigs;
import com.wangying.smallrain.entity.enums.ConfigModel;
import com.wangying.smallrain.utils.BaseUtils;

/**
 * 配置工具类
 * @author wangying.dz3
 *
 */
public class ConfigHelper {

  private static Logger log = LoggerFactory.getLogger(ConfigHelper.class);
  
  public static ConfigModel CURRENT_CONFIGMODEL = ConfigModel.LOCAL;
  
  /**
   * 基础配置信息
   */
  public static Map<String, BaseConfigs> BASE_CONFIG = new HashMap<>();
  
  /**
   * 基础配置信息 来自数据库
   */
  public static Map<String, BaseConfigs> BASE_CONFIG_DB = new HashMap<>();
  
  static {
    initBaseConfig();
  }
  
  /**
   * 获取当前所有配置
   * @return
   */
  public static List<Map<String, Object>> getAllCurrentConfig(){
    List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    Set<String> keys = BASE_CONFIG.keySet();
    Map<String, List<BaseConfigs>> temp = new HashMap<String, List<BaseConfigs>>();
    if(keys.isEmpty()) return result;
    for(String key:keys) {
      BaseConfigs bcs = BASE_CONFIG_DB.get(key);
      if (BaseUtils.isEmpty(bcs)) {
        bcs = BASE_CONFIG.get(key);
      }
      if(null == bcs||BaseUtils.isEmpty(bcs.getKey())) continue;
      String label = bcs.getLabel();
      label = BaseUtils.isEmpty(label)?"DEFAULT":label;
      List<BaseConfigs> cs = temp.get(label);
      if(BaseUtils.isEmpty(cs)) {
        cs = new ArrayList<BaseConfigs>();
        cs.add(bcs);
        temp.put(label, cs);
      }else {
        cs.add(bcs);
      }
    }
    Set<String> labelkeys = temp.keySet();
    if(labelkeys.isEmpty()) return result;
    for(String key:labelkeys) {
      Map<String, Object> labelFileds = new HashMap<>();
      labelFileds.put("label", key);
      labelFileds.put("configs", temp.get(key));
      result.add(labelFileds);
    }
    return result;
  }
  
  /**
   * 获取配置，悠闲获取环境变量，然后获取数据库配置，最后是默认值
   * 
   * @param envName
   * @return
   */
  public static String getValue(String envName) {
    String result = System.getenv(envName); // 首先获取环境变量
    if (!BaseUtils.isEmpty(result))  
      return result.trim();
    BaseConfigs bcs = BASE_CONFIG_DB.get(envName);
    if (!BaseUtils.isEmpty(bcs)) {
      result = bcs.getValue();
      if (!BaseUtils.isEmpty(result))  
        return result.trim();
    }
    bcs = BASE_CONFIG.get(envName);
    if (!BaseUtils.isEmpty(bcs)) {
      result = bcs.getValue();
      return BaseUtils.isEmpty(result) ? "" : result.trim();
    }
    return "";
  }
  
  
  /**
   * 初始化Git上的配置到本地
   */
  private static void initBaseConfig() {
    ClassPathResource resource = new ClassPathResource("baseConfig/configs.yml");
    try(InputStream in = resource.getInputStream()){
      Yaml yaml = new Yaml();
      parseBaseConfig(yaml.load(in));
      log.error("加载配置文件成功！");
    } catch (Exception e) {
      log.error("加载配置文件失败！"+e.getMessage());
    }
  }
  
  /**
   * @param source
   * parse yaml Object to Map 
   */
  @SuppressWarnings("unchecked")
  private static void parseBaseConfig(Map<String, Object> source) {
    if (null == source || source.isEmpty())
      return;
    Set<String> keys = source.keySet();
    if (keys.isEmpty())
      return;
    for (String key : keys) {
      Map<String, Object> map = (Map<String, Object>) source.get(key);
      if (null == map || map.isEmpty()) continue;
      if (map.containsKey("env") || map.containsKey("value")) { // 到了最底层
        Object env = map.get("env");
        if (BaseUtils.isEmpty(env)) continue;
        BaseConfigs bcs = new BaseConfigs();
        bcs.setKey(String.valueOf(map.get("env")));
        bcs.setValue(String.valueOf(map.get("value")));
        bcs.setDescription(String.valueOf(map.get("description")));
        bcs.setLabel(String.valueOf(map.get("label")));
        BASE_CONFIG.put(env.toString(), bcs);
      } else {
        parseBaseConfig(map);
      }
    }
  }
}
