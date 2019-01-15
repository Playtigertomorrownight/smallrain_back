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
import com.alibaba.fastjson.JSONObject;
import com.wangying.smallrain.configs.BaseConfig;
import com.wangying.smallrain.dao.ResourceGroupMapper;
import com.wangying.smallrain.dao.extend.ResourceExtendMapper;
import com.wangying.smallrain.entity.Resource;
import com.wangying.smallrain.entity.Result;
import com.wangying.smallrain.entity.enums.FileDataType;
import com.wangying.smallrain.ftp.FTPClientHelper;
import com.wangying.smallrain.markdown.MarkDown2HtmlWrapper;
import com.wangying.smallrain.markdown.MarkdownEntity;
import com.wangying.smallrain.service.FileService;
import com.wangying.smallrain.utils.BaseUtils;
import com.wangying.smallrain.utils.FileUtils;
import com.wangying.smallrain.utils.ResultUtil;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
@Service
public class FileServiceImpl implements FileService {

  @Autowired
  private ResourceExtendMapper resourceMapper;
  
  @Autowired
  private ResourceGroupMapper resourceGroupMapper;
  
  @Autowired
  private FTPClientHelper fTPClientHelper;

  @Value("${ftp.localFtp}")
  private boolean localFtp;
  
  @Value("${ftp.rootPath}")
  private String ftpRootPath;
  
  @Autowired
  private BaseConfig baseConfig;

  private Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

  @Override
  public boolean uploadFile(MultipartFile file, String fileName, String description, String label,String groupId) {
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
    res.setDescription(description);
    log.info("上传的文件描述：" + description);
    res.setLabel(label);
    log.info("上传的文件标签：" + label);
    res.setType(FileDataType.valueOfType(suffixName)); // 生成文件类型
    if(BaseUtils.isEmptyString(groupId)) {
      res.setGroupId(baseConfig.getDefaultResourceGroup());   //default rres group 
    }else {
      res.setGroupId(groupId);
    }
    
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
        if(BaseUtils.isEmptyString(groupId)) {   //如果指派的资源组非空，跟新资源组数量信息
          resourceGroupMapper.addResourceCount(1, groupId);
        }
        
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

  @Override
  public Result loadFile(String resourceId,boolean isMdWithCss) {
    log.info("根据资源ID加载相关的文件，资源ID ："+ resourceId);
    Resource res = resourceMapper.selectByPrimaryKey(resourceId);
    String path = res.getPath();
    if(null == res || BaseUtils.isEmptyString(path)) {
      log.warn("无对应的资源");
      return ResultUtil.fail("无对应的资源");
    }
    try {
      byte [] fileData =  fTPClientHelper.downloadFileToBytes(path);
      JSONObject result = new JSONObject();
      if(FileDataType.TXT == res.getType()) {    //文本文件，直接转 String
         String fileContent = new String(fileData, "UTF-8");
         if(path.endsWith("html")||path.endsWith("htm")) {
           result.put("type", "html");
         }else {
           result.put("type", "txt");
         }
         result.put("content", fileContent.replaceAll("(\r\n|\r|\n|\n\r)", "<br>"));
         return ResultUtil.success(result);
      }else if(FileDataType.MARKDOWN  == res.getType()) {    //markDown 文件，解析为 HTML 之后返回
         String fileContent = new String(fileData, "UTF-8");
         MarkdownEntity html = MarkDown2HtmlWrapper.ofContent(fileContent); 
         result.put("type", "html");
         result.put("content", html.toString(isMdWithCss));
         return ResultUtil.success(result);
      }else {
        return ResultUtil.fail("文件类型："+ res.getType() +" 暂时不支持转换！");
      }
      
    } catch (Exception e) {
      log.error("从ftp 加载资源时发生异常！");
      e.printStackTrace();
      return ResultUtil.fail("从ftp 加载资源时发生异常:"+ e.getMessage());
    }
  }

  @Override
  public Result deleteFile(String resourceId) {
    // TODO Auto-generated method stub
    log.info(BaseUtils.joinString("根据资源ID加载相关的文件，资源ID ：", resourceId));
    Resource res = resourceMapper.selectByPrimaryKey(resourceId);
    String path = res.getPath();
    if(null == res || BaseUtils.isEmptyString(path)) {
      log.warn("无对应的资源");
      return ResultUtil.fail("无对应的资源");
    }
    try {
       boolean isRemoved =  fTPClientHelper.removeFile(path);
       if(!isRemoved) {
         log.warn(BaseUtils.joinString("移除ftp服务器上的资源：",path," 失败！"));
         return ResultUtil.fail("删除资源失败");
       }
       resourceMapper.deleteByPrimaryKey(resourceId);  //删除数据库记录
       return ResultUtil.success("删除资源成功！");
       
    } catch (Exception e) {
      log.error("从ftp 加载资源时发生异常！");
      e.printStackTrace();
      return ResultUtil.fail("从ftp 加载资源时发生异常:"+ e.getMessage());
    }
  }

}
