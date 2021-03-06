package com.wangying.smallrain.ftp;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wangying.smallrain.utils.BaseUtils;

/**
 * ftp 客户端工具类
 * 
 * @author wangying.dz3
 *
 */
@Component
public class FTPClientHelper {

	@Autowired
	private FTPClientPool ftpClientPool;

	private Logger log = LoggerFactory.getLogger(FTPClientHelper.class);

	/**
	 * 下载 remote文件流
	 * 
	 * @param remote
	 *            远程文件
	 * @return 字节数据
	 * @throws IOException 
	 * @throws Exception
	 */
	public byte[] downloadFileToBytes(String remote) throws IOException{
		FTPClient client = null;
		InputStream in = null;
		try {
			log.info("下载远程文件流，转为字节数组。。");
			client = ftpClientPool.borrowObject();
			in = client.retrieveFileStream(remote);
			byte[] result = BaseUtils.inputStreamToByteArray(in);
			log.info("下载远程文件流结束。文件字节数：" + result.length);
			return result;
		} catch (Exception e) {
			log.error("在ftp服务器上创建单个目录失败");
			return null;
		} finally {
			if (in != null) {
				in.close();
			}
			if (client.completePendingCommand()) {
				ftpClientPool.returnObject(client);
			}

		}
	}

	/**
	 * 创建目录 单个 检测是否存在，不存在则创建
	 * 
	 * @param pathname
	 *            目录名称
	 * @return
	 * @throws Exception
	 */
	public boolean makeDir(String pathname) {
		log.info("在ftp服务器上创建单个目录。。");
		FTPClient client = null;
		try {
			client = ftpClientPool.borrowObject();
			FTPFile[] ftpDirs = client.listDirectories();
			boolean isExits = false;
			if (null != ftpDirs && ftpDirs.length > 0) {
				for (FTPFile f : ftpDirs) {
					if (f.getName().equals(pathname)) {
						isExits = true;
						log.info("目录已存在");
						break;
					}
				}
			}
			return isExits ? true : client.makeDirectory(pathname);
		} catch (Exception e) {
			log.error("在ftp服务器上创建单个目录失败");
			return false;
		} finally {
			ftpClientPool.returnObject(client);
		}
	}

	/**
	 * 删除目录，单个不可递归
	 * 
	 * @param pathname
	 * @return
	 * @throws IOException
	 */
	public boolean removeDir(String pathname) {
		FTPClient client = null;
		try {
			log.info("删除ftp服务器上的单个目录。。");
			client = ftpClientPool.borrowObject();
			return client.removeDirectory(pathname);
		} catch (Exception e) {
			log.error("删除ftp目录失败");
			return false;
		} finally {
			ftpClientPool.returnObject(client);
		}
	}

	/**
	 * 删除文件 单个 ，不可递归
	 * 
	 * @param pathname
	 * @return
	 * @throws Exception
	 */
	public boolean removeFile(String pathname) {
		FTPClient client = null;
		try {
			log.info("删除ftp服务器上的单个文件。。");
			client = ftpClientPool.borrowObject();
			return client.deleteFile(pathname);
		} catch (Exception e) {
			log.error("删除ftp文件失败");
			return false;
		} finally {
			ftpClientPool.returnObject(client);
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param remote
	 * @param local
	 * @return
	 * @throws Exception
	 */
	public boolean uploadFile(String remote, InputStream local) {
		FTPClient client = null;
		try {
			log.info("上传文件到ftp服务器上。。");
			client = ftpClientPool.borrowObject();
			return client.storeFile(remote, local);
		} catch (Exception e) {
			log.error("上传文件到ftp服务器上失败");
			return false;
		} finally {
			ftpClientPool.returnObject(client);
		}
	}

	public void setFtpClientPool(FTPClientPool ftpClientPool) {
		this.ftpClientPool = ftpClientPool;
	}

}
