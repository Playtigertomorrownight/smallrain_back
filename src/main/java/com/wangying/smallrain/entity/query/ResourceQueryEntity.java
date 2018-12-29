package com.wangying.smallrain.entity.query;

import java.util.Date;

/**
 * 资源搜索查询条件
 * @author wangying.dz3
 *
 */
public class ResourceQueryEntity extends BaseQueryEntity {

  private String name;
  private String type;
  private String label;
  private Date createTimeBegin;
  private Date createTimeEnd;

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public String getLabel() {
    return label;
  }

  public Date getCreateTimeBegin() {
    return createTimeBegin;
  }

  public Date getCreateTimeEnd() {
    return createTimeEnd;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public void setCreateTimeBegin(Date createTimeBegin) {
    this.createTimeBegin = createTimeBegin;
  }

  public void setCreateTimeEnd(Date createTimeEnd) {
    this.createTimeEnd = createTimeEnd;
  }

}
