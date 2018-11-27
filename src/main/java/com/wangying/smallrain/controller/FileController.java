package com.wangying.smallrain.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.wangying.smallrain.entity.Result;
import com.wangying.smallrain.service.FileService;
import com.wangying.smallrain.utils.ResultUtil;

/**
 * 文件操作基本控制类
 * @author wangying.dz3
 *
 */
@RestController
@RequestMapping("/v1")
public class FileController {

  @Autowired
  private FileService fileService;
  
  /**
   * 上传单个文件
   * @param files
   * @return
   */
  @RequestMapping(value = "file/upload", method = RequestMethod.POST)
  public Result uploadfile(@RequestParam("file") MultipartFile files) {
    boolean isSuccess = fileService.uploadFile(files,null);
    if(isSuccess)  return ResultUtil.success("上传成功！");
    return ResultUtil.fail("上传失败！");
  }
  
  /**
   * 上传多个文件
   * @param files
   * @return
   */
  @RequestMapping(value = "file/upload/multiple", method = RequestMethod.POST)
  public Result uploadfile(HttpServletRequest request) {

    List<MultipartFile> mfiles =((MultipartHttpServletRequest)request).getFiles("file"); 
    for(MultipartFile mfile:mfiles) {
      fileService.uploadFile(mfile,null);
    }
    return ResultUtil.success("上传成功！");
    
  }
  
  /**
   * 将图片资源输出到客户端
   * @param resId
   * @param response
   * @return
   */
  @RequestMapping(value = "/image/{resId}", method = RequestMethod.GET)
  public Result getImageData(@PathVariable("resId") String resId,HttpServletResponse response) {
  
    return ResultUtil.success("");
  }
}
