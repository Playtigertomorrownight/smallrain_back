package com.wangying.smallrain.entity.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.wangying.smallrain.utils.BaseUtils;

/**
 * 文件数据类型
 * @author wangying.dz3
 *
 */
public enum FileDataType {

  TXT(0,"text","txt-","文本文件",".txt",".js",".css",".htm",".html",".java",".cpp",".xml",".yaml",".json",".conf"),//文本文件
  OFFICE(1,"office","ofe-","OFFICE 文件",".doc",".docx",".ppt",".pptx",".wps",".pdf"),//文件
  COMPRESS(2,"compress","cps-","压缩文件",".zip",".rar",".arj",".gz",".z"),//压缩文件
  IMAGE(3,"image","img-","图片文件",".bmp",".gif",".jpg",".jpeg",".pic",".png",".tif"), //图片文件
  VOICE(4,"voice","vic-","图片文件",".wav",".au",".aif",".mp3",".ram"),  //图片文件
  VIDEO(5,"video","vid-","视频文件",".mp4",".avi",".mpg",".nov",".swf"),
  MARKDOWN(6,"markdown","md-","MarkDown 文件",".md"),
  DEFAULT(10,"default","def-","默认类型");
  private final int code ;
  private final  String ftpDir;
  private final  String hpath;
  private final  String description;
  private Map<String,Boolean> childTypes;
  
  private FileDataType(int code, String ftpDir, String hpath,String description, String ...childs) {
    this.code = code;
    this.ftpDir = ftpDir;
    this.hpath = hpath;
    this.description = description;
    childTypes = new HashMap<String,Boolean>();
    if(null!=childs && childs.length>0) {
      for(String child: childs) 
        childTypes.put(child, Boolean.TRUE);
    }
  }

  public int getCode() {
    return code;
  }
  
  public String getFtpDir() {
    return ftpDir;
  }

  public String getHpath() {
    return hpath;
  }
  
  public String getDescription() {
    return description;
  }
  
  /**
   * 根据类型转枚举
   * @param type
   * @return
   */
  public static FileDataType valueOfType(String type) {
    FileDataType [] types = FileDataType.values();
    for(FileDataType t : types){
      if(t.childTypes.containsKey(type))
         return t;
    }
    return DEFAULT;
  }
  
  /**
   * 生成文件名-uuid
   * @return
   */
  public String ftpfileName(String suffix) {
    return BaseUtils.joinString(hpath,BaseUtils.createUUID(),suffix);
  }
  
  public String  toString(){
    JSONObject result = new JSONObject();
    result.put("code", code);
    result.put("name", this.name());
    result.put("ftpDir", ftpDir);
    result.put("hpath", hpath);
    result.put("description", description);
    return result.toJSONString();
  }
  
  public static List<JSONObject> list(){
    List<JSONObject> result = new ArrayList<JSONObject>();
    FileDataType []  types = FileDataType.values();
    for(FileDataType type : types) {
      result.add(JSONObject.parseObject(type.toString()));
    }
    return result;
  }
  
}
