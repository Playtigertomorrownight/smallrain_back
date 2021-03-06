package com.wangying.smallrain.utils;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 基本工具类，提供一写基本的方法，目前主要提供对象复制的通用方法
 * 
 * @author wangying.dz3
 *
 */
public class BaseUtils extends BeanUtils {

  public static String TEMP_FILE_DIR;
	
  /**
   * 重写对象复制，忽略值为空的属性
   * 
   * @param source
   * @param target
   * @param ignoreProperties
   * @throws BeansException
   */
  public static void copyNotNullProperties(Object source, Object target, @Nullable String... ignoreProperties)
      throws BeansException {

    Assert.notNull(source, "Source must not be null");
    Assert.notNull(target, "Target must not be null");

    Class<?> actualEditable = target.getClass();

    PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
    List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

    for (PropertyDescriptor targetPd : targetPds) {
      Method writeMethod = targetPd.getWriteMethod();
      if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
        PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
        if (sourcePd != null) {
          Method readMethod = sourcePd.getReadMethod();
          if (readMethod != null
              && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
            try {
              Object value = readMethod.invoke(source);
              // 单且仅单属性值不为空的时候才进行复制
              if (null != value && value.toString().length() != 0) {
                writeMethod.invoke(target, value);
              }
            } catch (Throwable ex) {
              throw new FatalBeanException("Could not copy property '" + targetPd.getName() + "' from source to target",
                  ex);
            }
          }
        }
      }
    }
  }

  /**
   * 将输入流转为字节数组
   * 
   * @param in
   * @return
   */
  public static byte[] inputStreamToByteArray(InputStream in) {
    try {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      byte[] buffer = new byte[1024 * 4];
      int n = 0;
      while ((n = in.read(buffer)) > 0) {
        out.write(buffer, 0, n);
      }
      byte[] result = out.toByteArray();
      out.close();
      return result;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 生成uuid
   * 
   * @return
   */
  public static String createUUID() {
    return UUID.randomUUID().toString().replace("-", "");
  }

  public static File createTempFile(String suffixName) throws IOException {
	  String filePath = joinString(TEMP_FILE_DIR,"temp-",createUUID(),suffixName);
	  File result = new File(filePath);
	  if(!result.getParentFile().exists()) {
		  result.getParentFile().mkdirs();
	  }
	  result.createNewFile();
	  return result;
  }
  
  /**
   * 字符串拼接
   * 
   * @return
   */
  public static String joinString(Object... objects) {
    StringBuilder sbf = new StringBuilder("");
    if (null == objects || objects.length == 0)
      return "";
    for (Object obj : objects) {
      sbf.append(obj);
    }
    return sbf.toString();
  }

  /**
   * 判断字符串是否为空，包括全空格
   * 
   * @param str
   * @return
   */
  public static boolean isEmpty(Object str) {
    if(str instanceof String) {  //字符串
      return null == str || str.toString().trim().length() == 0;
    }else if(str instanceof Collection) {  //集合
      return null == str || ((Collection<?>)str).isEmpty();
    }else {  //一般类
      return null == str;
    }
  }

  /**
   * MD5方法
   * 
   * @param text
   *          明文
   * @param key
   *          密钥
   * @return 密文
   * @throws Exception
   */
  public static String md5(String text, String key) throws Exception {
    // 加密后的字符串
    return DigestUtils.md5Hex(joinString(text,key));
  }

  /**
   * MD5验证方法
   * 
   * @param text
   *          明文
   * @param key
   *          密钥
   * @param md5
   *          密文
   * @return true/false
   * @throws Exception
   */
  public static boolean md5Verify(String text, String key, String md5) throws Exception {
    // 根据传入的密钥进行验证
    String md5Text = md5(text, key);
    return md5Text.equalsIgnoreCase(md5);
  }

  /**
   * 移除属性值为空的属性
   * 
   * @param obj
   * @return
   */
  public static JSONObject removeNullEntry(JSONObject obj) {
    if (obj == null)
      return obj;
    for (Iterator<Map.Entry<String, Object>> it = obj.entrySet().iterator(); it.hasNext();) {
      Map.Entry<String, Object> item = it.next();
      String key = item.getKey();
      if (StringUtils.isEmpty(obj.getString(key)) || "level".equals(key) || "parent".equals(key))
        it.remove();
    }
    return obj;
  }
  
}
