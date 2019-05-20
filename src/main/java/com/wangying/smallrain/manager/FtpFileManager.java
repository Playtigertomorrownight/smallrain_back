package com.wangying.smallrain.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wangying.smallrain.entity.Resource;
import com.wangying.smallrain.entity.enums.FileDataType;
import com.wangying.smallrain.ftp.FTPClientHelper;
import com.wangying.smallrain.utils.BaseUtils;

@Component
public class FtpFileManager {
	
	@Autowired
	private FTPClientHelper fTPClientHelper;

	private Logger log = LoggerFactory.getLogger(FtpFileManager.class);

	/**
	 * 上传文件到FTP服务器
	 * 
	 * @param file
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public boolean uploadFileToFtp(File file, Resource res) throws IOException{
		// TODO Auto-generated method stub
		log.info("上传文件到ftp");
		if (null == file || !file.exists()) {
			log.info("上传文件为空！");
			return false;
		}
		InputStream ins = null;
		try {
			FileDataType type = res.getType(); // 通过type枚举生成目录和文件名
			log.info(BaseUtils.joinString("首先在ftp上创建文件夹：", type.getFtpDir()));
			fTPClientHelper.makeDir(type.getFtpDir()); // 检查文件目录是否存在不存在则创建
			ins = new FileInputStream(file); // 获取文件流
			String path = BaseUtils.joinString(type.getFtpDir(), "/", type.ftpfileName(res.getSuffix()));
			if (fTPClientHelper.uploadFile(path, ins)) {
				log.info(BaseUtils.joinString("上传文件：", res.getName(), " 到ftp服务器成功。"));
				res.setPath(path);
				return true;
			} else {
				log.info(BaseUtils.joinString("上传文件：", res.getName(), " 到ftp服务器失败。"));
				return false;
			}
		} catch (Exception e) {
			log.error(BaseUtils.joinString("上传文件：", res.getName(), " 到ftp服务器失败：", e.getMessage()));
			return false;
		} finally {
			if (null != ins)
				ins.close();
		}
	}
	
	/**
	 * 加载ftp文件到本地
	 * @param path
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	public String loadFtpFile(String path) throws IOException{
		byte[] fileData = fTPClientHelper.downloadFileToBytes(path);
		if(null==fileData||fileData.length==0) {
			log.error("从ftp上下载到的数据为空");
			return "";
		}
		return new String(fileData, "UTF-8");
	}
	
	public boolean deleteFtpFile(String path){
		return fTPClientHelper.removeFile(path);
	}

}
