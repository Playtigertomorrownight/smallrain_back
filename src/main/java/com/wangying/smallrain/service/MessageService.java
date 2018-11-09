package com.wangying.smallrain.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.wangying.smallrain.entity.enums.MessageType;
import com.wangying.smallrain.manager.EventManager;
import com.wangying.smallrain.utils.WechatUtil;

/**
 * 消息服务层
 * @author wangying.dz3
 *
 */
@Service
public class MessageService {
  
  @Autowired
  private EventManager eventManager;
  
  private Logger log = LoggerFactory.getLogger(MessageService.class);
  
  /**
   * 处理来自微信端的消息，并作出响应的响应
   * @param req
   * @param resp
   * @throws IOException
   */
  public void dealMessage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    try {
      log.info("--消息请求进入--");
      String result = "";
      //将请求中 HttpServletRequest 的得参数解析为 map 数据对象
      Map<String,String> map = WechatUtil.parseXml(req);
      //分析收到的消息，根据分析结果返回相应的结果
      if(null == map||map.isEmpty()) return ;
      String msgType = map.get("MsgType").toString();
      log.info("--接收到的消息类型是："+msgType);
      if(StringUtils.isEmpty(msgType)) return ;
      MessageType mtype = MessageType.valueOf(msgType.toUpperCase());
      switch(mtype) {
        case TEXT:
          result = dealTextMessage(map);
          break;
        case IMAGE:
          result = dealImageMessage(map);
          break;
        case VOICE:
          result = dealVoiceMessage(map);
          break;
        case VIDEO:
          result = dealVideoMessage(map);
          break;
        case SHORTVIDEO:
          result = dealShortvideoMessage(map);
          break;
        case LOCATION:
          result = dealLocationMessage(map);
          break;
        case LINK:
          result = dealLinkMessage(map);
          break;
        case EVENT:
          result = eventManager.dealEventMessage(map);
          break;
      default:
        break;
      }
      resp.getWriter().println(result);
    } catch (Exception e) {
      e.printStackTrace();
      log.error("发生异常："+ e.getMessage());
    }
  }
  
  /**
   * 处理文本消息
   *  ToUserName 开发者微信号
      FromUserName  发送方帐号（一个OpenID）
      CreateTime  消息创建时间 （整型）
      MsgType text
      Content 文本消息内容
      MsgId 消息id，64位整型
   * @param msg
   * @return
   */
  private String dealTextMessage(Map<String,String> msg) {
    
    return "";
  }
  
  /**
   * 处理图片消息
   *  ToUserName  开发者微信号
      FromUserName  发送方帐号（一个OpenID）
      CreateTime  消息创建时间 （整型）
      MsgType image
      PicUrl  图片链接（由系统生成）
      MediaId 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
      MsgId 消息id，64位整型
   * @param msg
   * @return
   */
  private String dealImageMessage(Map<String,String> msg) {
    
    return "";
  }
  
  /**
   * 处理语音消息
   *  ToUserName  开发者微信号
      FromUserName  发送方帐号（一个OpenID）
      CreateTime  消息创建时间 （整型）
      MsgType 语音为voice
      MediaId 语音消息媒体id，可以调用多媒体文件下载接口拉取数据。
      Format  语音格式，如amr，speex等
      Recognition 语音识别结果，UTF8编码  （开启语音识别后有该字段）
      MsgID 消息id，64位整型
   * @param msg
   * @return
   */
  private String dealVoiceMessage(Map<String,String> msg) {
    
    return "";
  }
  
  /**
   * 处理视频消息
   *  ToUserName  开发者微信号
      FromUserName  发送方帐号（一个OpenID）
      CreateTime  消息创建时间 （整型）
      MsgType 视频为video
      MediaId 视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
      ThumbMediaId  视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
      MsgId 消息id，64位整型
   * @param msg
   * @return
   */
  private String dealVideoMessage(Map<String,String> msg) {
    
    return "";
  }
  
  /**
   * 处理小视频视频消息
   *  ToUserName  开发者微信号
      FromUserName  发送方帐号（一个OpenID）
      CreateTime  消息创建时间 （整型）
      MsgType 小视频为shortvideo
      MediaId 视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
      ThumbMediaId  视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
      MsgId 消息id，64位整型
   * @param msg
   * @return
   */
  private String dealShortvideoMessage(Map<String,String> msg) {
    
    return "";
  }
  
  /**
   * 处理小视频视频消息
   *  ToUserName  开发者微信号
      FromUserName  发送方帐号（一个OpenID）
      CreateTime  消息创建时间 （整型）
      MsgType location
      Location_X  地理位置维度
      Location_Y  地理位置经度
      Scale 地图缩放大小
      Label 地理位置信息
      MsgId 消息id，64位整型
   * @param msg
   * @return
   */
  private String dealLocationMessage(Map<String,String> msg) {
    
    return "";
  }
  
  /**
   * 处理小视频视频消息
   *  ToUserName  接收方微信号
      FromUserName  发送方微信号，若为普通用户，则是一个OpenID
      CreateTime  消息创建时间
      MsgType 消息类型，link
      Title 消息标题
      Description 消息描述
      Url 消息链接
      MsgId 消息id，64位整型
   * @param msg
   * @return
   */
  private String dealLinkMessage(Map<String,String> msg) {
    
    return "";
  }
  
}
