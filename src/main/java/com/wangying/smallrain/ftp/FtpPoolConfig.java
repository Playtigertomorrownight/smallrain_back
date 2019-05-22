package com.wangying.smallrain.ftp;

import static com.wangying.smallrain.configs.ConfigHelper.getValue;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FtpPoolConfig extends GenericObjectPoolConfig {

  private String host;// 主机名
  private int port;// 端口
  private String username;// 用户名
  private String password;// 密码
  private String rootPath; //文件存放路径
  private int connectTimeOut;// ftp 连接超时时间 毫秒
  private String controlEncoding;
  private int bufferSize;// 缓冲区大小
  private int fileType;// 传输数据格式 2表binary二进制数据
  private int dataTimeout;
  private boolean useEPSVwithIPv4;
  private boolean passiveMode;// 是否启用被动模式
  
  public static FtpPoolConfig getInstance() {
    FtpPoolConfig result = new FtpPoolConfig();
    result.init();
    return result;
  }
  
  //初始化属性值
  private void init(){
    this.host = getValue("FTP_HOST");
    this.port = Integer.parseInt(getValue("FTP_PORT"));
    this.username = getValue("FTP_USERNAME");
    this.password = getValue("FTP_PASSWORD");
    this.rootPath = getValue("FTP_ROOT_PATH");
    this.connectTimeOut = Integer.parseInt(getValue("FTP_CONNECT_TIMEOUT"));
    this.controlEncoding = getValue("FTP_ENCODING");
    this.bufferSize = Integer.parseInt(getValue("FTP_BUFFER_SIZE"));
    this.fileType = Integer.parseInt(getValue("FTP_FILE_TYPE"));
    this.dataTimeout = Integer.parseInt(getValue("FTP_DATA_TIMEOUT"));
    this.useEPSVwithIPv4 = Boolean.valueOf(getValue("USE_EPSV_WITH_IPV4"));
    this.passiveMode = Boolean.valueOf(getValue("FTP_PASSIVE_MODE"));
  }

  public int getConnectTimeOut() {
    return connectTimeOut;
  }

  public void setConnectTimeOut(int connectTimeOut) {
    this.connectTimeOut = connectTimeOut;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public String getRootPath() {
    return rootPath;
  }

  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getControlEncoding() {
    return controlEncoding;
  }

  public void setControlEncoding(String controlEncoding) {
    this.controlEncoding = controlEncoding;
  }

  public int getBufferSize() {
    return bufferSize;
  }

  public void setBufferSize(int bufferSize) {
    this.bufferSize = bufferSize;
  }

  public int getFileType() {
    return fileType;
  }

  public void setFileType(int fileType) {
    this.fileType = fileType;
  }

  public int getDataTimeout() {
    return dataTimeout;
  }

  public void setDataTimeout(int dataTimeout) {
    this.dataTimeout = dataTimeout;
  }

  public boolean isUseEPSVwithIPv4() {
    return useEPSVwithIPv4;
  }

  public void setUseEPSVwithIPv4(boolean useEPSVwithIPv4) {
    this.useEPSVwithIPv4 = useEPSVwithIPv4;
  }

  public boolean isPassiveMode() {
    return passiveMode;
  }

  public void setPassiveMode(boolean passiveMode) {
    this.passiveMode = passiveMode;
  }
}
