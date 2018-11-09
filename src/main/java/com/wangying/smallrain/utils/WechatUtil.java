package com.wangying.smallrain.utils;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.wangying.smallrain.entity.WxMessage;


/**
 * 微信工具类
 * 
 * @author wangying.dz3
 *
 */
public class WechatUtil {
  
  /**
   * 排序方法给 token 时间戳 和附件信息排序
   */
  public static String sort(String token, String timestamp, String nonce) {
    String[] strArray = { token, timestamp, nonce };
    Arrays.sort(strArray);
    StringBuilder sb = new StringBuilder();
    for (String str : strArray) {
      sb.append(str);
    }
    return sb.toString();
  }

  /**
   * 将字符串进行sha1加密
   *
   * @param str
   *          需要加密的字符串
   * @return 加密后的内容
   */
  public static String sha1(String str) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-1");
      digest.update(str.getBytes());
      byte messageDigest[] = digest.digest();
      // Create Hex String
      StringBuffer hexString = new StringBuffer();
      // 字节数组转换为 十六进制 数
      for (int i = 0; i < messageDigest.length; i++) {
        String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
        if (shaHex.length() < 2) {
          hexString.append(0);
        }
        hexString.append(shaHex);
      }
      return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return "";
  }
  
  /**
   * 解析微信发来的请求（XML）
   * @param request
   * @return map
   * @throws Exception
   */
  public static Map<String,String> parseXml(HttpServletRequest request) throws Exception {
      // 将解析结果存储在HashMap中
      Map<String,String> result = new HashMap<String,String>();
      // 从request中取得输入流
      InputStream inputStream = request.getInputStream();
      try {
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        @SuppressWarnings("unchecked")
        List<Element> elementList = root.elements();
        // 遍历所有子节点
        for (Element e : elementList) {
            result.put(e.getName(), e.getText());
        }
      }catch(Exception e) {
        e.printStackTrace();
        return null;
      }finally {
        // 释放资源
        if(null != inputStream) {
          inputStream.close();
        }
      }
      return result;
  }

  /**
   * 根据消息类型 构造返回消息
   */
  public static String buildXml(Map<String,String> map) {
      String result;
      String msgType = map.get("MsgType").toString();
      System.out.println("MsgType:" + msgType);
      if(msgType.toUpperCase().equals("TEXT")){
          result = buildTextMessage(map, "Cherry的小小窝, 请问客官想要点啥?");
      }else{
          String fromUserName = map.get("FromUserName");
          // 开发者微信号
          String toUserName = map.get("ToUserName");
          result = String
                  .format(
                          "<xml>" +
                                  "<ToUserName><![CDATA[%s]]></ToUserName>" +
                                  "<FromUserName><![CDATA[%s]]></FromUserName>" +
                                  "<CreateTime>%s</CreateTime>" +
                                  "<MsgType><![CDATA[text]]></MsgType>" +
                                  "<Content><![CDATA[%s]]></Content>" +
                                  "</xml>",
                          fromUserName, toUserName, getUtcTime(),
                          "请回复如下关键词：\n文本\n图片\n语音\n视频\n音乐\n图文");
      }

      return result;
  }

  /**
   * 构造文本消息
   *
   * @param map
   * @param content
   * @return
   */
  private static String buildTextMessage(Map<String,String> map, String content) {
      //发送方帐号
      String fromUserName = map.get("FromUserName");
      // 开发者微信号
      String toUserName = map.get("ToUserName");
      /**
       * 文本消息XML数据格式
       */
      return String.format(
              "<xml>" +
                      "<ToUserName><![CDATA[%s]]></ToUserName>" +
                      "<FromUserName><![CDATA[%s]]></FromUserName>" +
                      "<CreateTime>%s</CreateTime>" +
                      "<MsgType><![CDATA[text]]></MsgType>" +
                      "<Content><![CDATA[%s]]></Content>" + "</xml>",
              fromUserName, toUserName, getUtcTime(), content);
  }
  
  /**
   * 根据接收到的消息初始化返回的消息
   * 主要公共的初始化 发送者，接受者和创建时间 
   * @param msg
   * @return
   */
  public static WxMessage initMessage(Map<String,String> msg) {
    WxMessage result = new  WxMessage();
    result.setFromUserName(msg.get("ToUserName"));
    result.setToUserName(msg.get("FromUserName"));
    result.setCreateTime(getUtcTime());
    return result;
  }

  private static String getUtcTime() {
      Date dt = new Date();// 如果不需要格式,可直接用dt,dt就是当前系统时间
      DateFormat df = new SimpleDateFormat("yyyyMMddhhmm");// 设置显示格式
      String nowTime = df.format(dt);
      long dd = (long) 0;
      try {
          dd = df.parse(nowTime).getTime();
      } catch (Exception e) {

      }
      return String.valueOf(dd);
  }

}
