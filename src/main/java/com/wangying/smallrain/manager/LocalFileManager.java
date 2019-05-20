package com.wangying.smallrain.manager;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wangying.smallrain.entity.Resource;
import com.wangying.smallrain.entity.enums.FileDataType;
import com.wangying.smallrain.utils.BaseUtils;
import static com.wangying.smallrain.configs.ConfigHelper.getValue;
/**
 * 本地文件管理
 * 
 * @author 16524
 *
 */
@Component
public class LocalFileManager {

	private Logger log = LoggerFactory.getLogger(LocalFileManager.class);

	/**
	 * 保存文件到本地
	 * 
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	public boolean uploadFileToLocal(File file, Resource res) {
		log.info("上传文件到本地文件系统");
		if (null == file || !file.exists()) {
			log.info("上传文件为空！");
			return false;
		}
		try {
			FileDataType type = res.getType(); // 通过type枚举生成目录和文件名
			String path = BaseUtils.joinString(type.getFtpDir(), "/", type.ftpfileName(res.getSuffix()));
			String filePath = BaseUtils.joinString(getValue("FTP_ROOT_PATH"), "/", path);
			FileUtils.copyFile(file, new File(filePath), false); // 复制文件
			log.info(BaseUtils.joinString("上传文件：", res.getName(), " 到本地成功。"));
			res.setPath(path);
			return true;
		} catch (Exception e) {
			log.error(BaseUtils.joinString("上传文件：", res.getName(), " 到本地失败。"));
		}
		return false;
	}
	
	/**
	 * 加载本地文件
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public String loadLocalFile(String path) throws IOException {
		String filePath = BaseUtils.joinString(getValue("FTP_ROOT_PATH"), "/", path);
		File file = new File(filePath);
		if(!file.exists()) {
			log.error("加载本地文件失败，文件不存在。。");
		}
		return FileUtils.readFileToString(file);
	}
	
	/**
	 * 删除本地文件
	 * @return
	 */
	public boolean deleteLocalFile(String path) {
		String filePath = BaseUtils.joinString(getValue("FTP_ROOT_PATH"), "/", path);
		File file = new File(filePath);
		if(!file.exists()) {
			log.error("删除本地文件失败，文件不存在。。");
		}
		return FileUtils.deleteQuietly(file);
	}

}
