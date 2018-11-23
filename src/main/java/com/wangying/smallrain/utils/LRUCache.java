package com.wangying.smallrain.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 通过LinkedHashMap实现LRU 缓存列表
 * @author wangying.dz3
 *
 * @param <K>
 * @param <V>
 */
public class LRUCache<K,V>  extends LinkedHashMap<K,V>{
  
  private static final long serialVersionUID = 11245687456624L;
  
  private int maxCapacity;                     //最大缓存
  
  public LRUCache(int capacity){          //构造方法，传入一个参数
      super(64,0.75f,true);               //调用LinkedHashMap，传入参数    
      this.maxCapacity=capacity;             //传递指定的最大内存容量
  }
  @Override
  public boolean removeEldestEntry(Map.Entry<K, V> eldest){     
      //重写此方法就判断是size是否超过了已定的容量，当容量超出最大值，linkedHashMap会移除最早使用的元素
      return size() > maxCapacity;        
  }
  
}
