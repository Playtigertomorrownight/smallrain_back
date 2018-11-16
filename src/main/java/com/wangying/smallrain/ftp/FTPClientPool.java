package com.wangying.smallrain.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FTPClientPool {

  /**
   * ftp客户端连接池
   */
  private GenericObjectPool<FTPClient> pool;
  /**
   * ftp客户端工厂
   */
  @Autowired
  private FTPClientFactory clientFactory;

  @Autowired
  private FtpPoolConfig ftpPoolConfig;
  
  /**
   * 构造函数中 注入一个bean
   * 
   * @param clientFactory
   */
  public FTPClientPool() {
    pool = new GenericObjectPool<FTPClient>(clientFactory, ftpPoolConfig);
  }

  /**
   * 借 获取一个连接对象
   * 
   * @return
   * @throws Exception
   */
  public FTPClient borrowObject() throws Exception {

    FTPClient client = pool.borrowObject();

    // if(!client.sendNoOp()){
    // //使池中的对象无效
    // client.logout();
    // client.disconnect();
    // pool.invalidateObject(client);
    // client =clientFactory.create();
    // pool.addObject();
    // }
    //
    return client;

  }

  /**
   * 还 归还一个连接对象
   * 
   * @param ftpClient
   */
  public void returnObject(FTPClient ftpClient) {

    if (ftpClient != null) {
      pool.returnObject(ftpClient);
    }
  }
  
  public void invalidateObject(FTPClient ftpClient) {
    if (ftpClient != null) {
      try {
        pool.invalidateObject(ftpClient);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }
  
  
}
