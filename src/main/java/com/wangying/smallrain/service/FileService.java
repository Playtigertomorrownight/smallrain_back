package com.wangying.smallrain.service;

import org.springframework.web.multipart.MultipartFile;

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
  public boolean uploadFileToFtp(MultipartFile files,String Filename);
  
  
  /**
   * 下载文件
   * @param path
   * @return
   */
  public String downloadFile(String path);
}
