package com.wangying.smallrain.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.wangying.smallrain.dao.ResourceMapper;
import com.wangying.smallrain.entity.Resource;
import com.wangying.smallrain.entity.enums.FileDataType;
import com.wangying.smallrain.ftp.FTPClientHelper;
import com.wangying.smallrain.service.FileService;
import com.wangying.smallrain.utils.BaseUtils;
import com.wangying.smallrain.utils.FileUtils;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
@Service
public class FileServiceImpl implements FileService {

  @Autowired
  private ResourceMapper resourceMapper;
  @Autowired
  private FTPClientHelper fTPClientHelper;

  @Value("${ftp.localFtp}")
  private boolean localFtp;
  
  @Value("${ftp.rootPath}")
  private boolean ftpRootPath;

  private Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

  @Override
  public boolean uploadFile(MultipartFile file, String fileName) {
    // TODO Auto-generated method stub
    log.info("上传文件。。。。");
    Resource res = new Resource(); // new resource 对象
    res.setCreateTime(new Date()); // 设置时间
    String OrifileName = file.getOriginalFilename(); // 获取文件名
    if (StringUtils.isEmpty(fileName)) { // 如果未传文件名，则设置为原始文件名
      res.setName(OrifileName.substring(0, OrifileName.lastIndexOf("."))); // 原始文件名
    } else {
      res.setName(fileName); // 原始文件名
    }
    log.info(BaseUtils.joinString("上传的文件名为：", fileName));
    String suffixName = OrifileName.substring(OrifileName.lastIndexOf("."));// 获取文件的后缀名
    log.info(BaseUtils.joinString("上传的文件后缀名为：", suffixName));
    res.setSuffix(suffixName);
    long filesize = file.getSize(); // 获取并设置文件大小
    res.setSize(filesize);
    log.info("上传的文件大小为：" + filesize);
    res.setType(FileDataType.valueOfType(suffixName)); // 生成文件类型
    try {
      boolean isUpload = false;
      if(localFtp) {
        isUpload = uploadFileToLocal(file,fileName,res);
      }else {
        isUpload = uploadFileToFtp(file,fileName,res);
      }
      if(isUpload) {
        log.info("上传文件成功！保存数据到数据库。。");
        res.setId(BaseUtils.createUUID());
        resourceMapper.insert(res);
        return true;
      }
    }catch(Exception e) {
      e.printStackTrace();
      log.info("上传文件失败！");
    }
    return false;
  }

  private boolean uploadFileToLocal(MultipartFile file, String fileName, Resource res) throws IOException, Exception {
      log.info("上传文件到本地文件系统");
      if (null == file || file.isEmpty()) {
        log.info("上传文件为空！");
        return false;
      }
      FileDataType type = res.getType(); // 通过type枚举生成目录和文件名
      String filePath = BaseUtils.joinString(ftpRootPath,"/",type.getFtpDir(), "/", type.ftpfileName(res.getSuffix()));
      String path = filePath.substring(filePath.indexOf("/")+1);
      
      long size = FileUtils.writeStreamToFile(file.getInputStream(), filePath);
      if (size > 0) {
        log.info(BaseUtils.joinString("上传文件：", fileName, " 到本地成功。"));
        res.setPath(path);
        return true;
      } else {
        log.info(BaseUtils.joinString("上传文件：", fileName, " 到本地失败。"));
      }
    return false;
  }

  /**
   * 上传文件到FTP服务器
   * 
   * @param file
   * @param fileName
   * @return
   * @throws Exception 
   */
  private boolean uploadFileToFtp(MultipartFile file, String fileName, Resource res) throws Exception {
    // TODO Auto-generated method stub
      log.info("上传文件到ftp");
      if (null == file || file.isEmpty()) {
        log.info("上传文件为空！");
        return false;
      }
      FileDataType type = res.getType(); // 通过type枚举生成目录和文件名
      log.info(BaseUtils.joinString("首先在ftp上创建文件夹：", type.getFtpDir()));
      fTPClientHelper.makeDir(type.getFtpDir()); // 检查文件目录是否存在不存在则创建
      log.info("获取文件输入流并上传.....");
      InputStream ins = file.getInputStream(); // 获取文件流
      String path = BaseUtils.joinString(type.getFtpDir(), "/", type.ftpfileName(res.getSuffix()));
      if (fTPClientHelper.uploadFile(path, ins)) {
        log.info(BaseUtils.joinString("上传文件：", fileName, " 到ftp服务器成功。"));
        res.setPath(path);
        return true;
      } else {
        log.info(BaseUtils.joinString("上传文件：", fileName, " 到ftp服务器失败。"));
      }
      if (null != ins)
        ins.close();
    return false;
  }

  @Override
  public String downloadFile(String path) {
    // TODO Auto-generated method stub
    return null;
  }

}
