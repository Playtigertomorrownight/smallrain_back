package com.wangying.smallrain.entity.query;

/**
 * 基本查询条件实体类，包含基础的 页码 和 页面容量
 * 
 * @author wangying.dz3
 *
 */
public class BaseQueryEntity {

  private Integer pageNum = 1;
  private Integer pageSize = 10;

  public BaseQueryEntity() {
  }

  public BaseQueryEntity(Integer pageNum, Integer pageSize) {
    this.pageNum = pageNum;
    this.pageSize = pageSize;
  }

  public Integer getPageNum() {
    return pageNum;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageNum(Integer pageNum) {
    this.pageNum = pageNum;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

}
