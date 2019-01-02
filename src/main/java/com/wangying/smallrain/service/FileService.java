package com.wangying.smallrain.service;

import org.springframework.web.multipart.MultipartFile;

import com.wangying.smallrain.entity.Result;

/**
 * 文件服务层，定义文件操作的基本接口
 * @author wangying.dz3
 *
 */
public interface FileService {

  /**
   * 上传文件到ftp,并生成记录到数据库
   * @param files
   * @param Filename
   * @return
   */
  public boolean uploadFile(MultipartFile file,String fileName,String description,String label);
  
  
  /**
   * 下载文件
   * @param path
   * @return
   */
  public String downloadFile(String path);
  
  /**
   * 根据资源ID加载相关的文件
   * @param resourceId
   * @return
   */
  public Result loadFile(String resourceId);
  
  /**
   * 根据资源ID删除相关的文件及其记录
   * @param resourceId
   * @return
   */
  public Result deleteFile(String resourceId);
  
  
  
}
