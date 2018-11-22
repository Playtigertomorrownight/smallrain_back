package com.wangying.smallrain.utils;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * 基本工具类，提供一写基本的方法，目前主要提供对象复制的通用方法
 * 
 * @author wangying.dz3
 *
 */
public class BaseUtils extends BeanUtils {

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
      byte[] result =  out.toByteArray();
      out.close();
      return result;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  
  /**
   * 生成uuid
   * @return
   */
  public static String createUUID() {
    return UUID.randomUUID().toString().replace("-", "");
  }
  
  /**
   * 字符串拼接
   * @return
   */
  public static String joinString(Object ...objects) {
    StringBuilder sbf = new StringBuilder("");
    if(null==objects||objects.length==0) return "";
    for(Object obj: objects) {
      sbf.append(obj);
    }
    return sbf.toString();
  }

}
