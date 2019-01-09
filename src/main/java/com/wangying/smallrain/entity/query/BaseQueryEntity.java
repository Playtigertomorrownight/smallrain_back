package com.wangying.smallrain.entity.query;

public class BaseQueryEntity {

  private Integer pageNum=1;
  private Integer pageSize=10;
  
  public BaseQueryEntity() {}
  
  public BaseQueryEntity(Integer pageNum, Integer pageSize) {
    super();
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
