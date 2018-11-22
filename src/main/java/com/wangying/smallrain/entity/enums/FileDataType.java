package com.wangying.smallrain.entity.enums;

import java.util.HashMap;
import java.util.Map;

import com.wangying.smallrain.utils.BaseUtils;

/**
 * 文件数据类型
 * @author wangying.dz3
 *
 */
public enum FileDataType {

  TXT(0,"text","txt-",".txt",".js",".css",".htm",".html",".java",".cpp",".xml",".yaml",".json"),//文本文件
  OFFICE(1,"office","ofe-",".doc",".docx",".ppt",".pptx",".wps",".pdf"),//文件
  COMPRESS(2,"compress","cps-",".zip",".rar",".arj",".gz",".z"),//压缩文件
  IMAGE(3,"image","img-",".bmp",".gif",".jpg",".jpeg",".pic",".png",".tif"), //图片文件
  VOICE(4,"voice","vic-",".wav",".au",".aif",".mp3",".ram"),  //音频文件
  VIDEO(5,"video","vid-",".mp4",".avi",".mpg",".nov",".swf"),
  MARKDOWN(6,"markdown","md-",".md"),
  DEFAULT(10,"default","def-");
  private final int code ;
  private final  String ftpDir;
  private final  String hpath;
  private Map<String,Boolean> childTypes;
  
  private FileDataType(int code, String ftpDir, String hpath, String ...childs) {
    this.code = code;
    this.ftpDir = ftpDir;
    this.hpath = hpath;
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
  
}
