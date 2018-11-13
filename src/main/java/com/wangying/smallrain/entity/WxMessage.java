package com.wangying.smallrain.entity;

import com.wangying.smallrain.entity.enums.MessageType;

public class WxMessage {
  
//文本消息模板
  public static final String TEMPLATE_TEXT = "<xml><ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[%s]]></MsgType><Content><![CDATA[%s]]></Content></xml>\r\n";
  //图片消息模板
  public static final String TEMPLATE_IMAGE = "<xml><ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[%s]]></MsgType><Image><MediaId><![CDATA[%s]]></MediaId></Image></xml>";
  //语音消息模板
  public static final String TEMPLATE_VOICE = "<xml><ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[%s]]></MsgType><Voice><MediaId><![CDATA[%s]]></MediaId></Voice></xml>";
  //视频消息模板
  public static final String TEMPLATE_VIDEO = "<xml><ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[%s]]></MsgType><Video><MediaId><![CDATA[%s]]></MediaId><Title><![CDATA[%s]]></Title><Description><![CDATA[%s]]></Description></Video></xml>";
  //音乐消息模板  
  public static final String TEMPLATE_MUSIC = "<xml><ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[%s]]></MsgType><Music><Title><![CDATA[%s]]></Title><Description><![CDATA[%s]]></Description><MusicUrl><![CDATA[%s]]></MusicUrl><HQMusicUrl><![CDATA[%s]]></HQMusicUrl><ThumbMediaId><![CDATA[%s]]></ThumbMediaId></Music></xml>";
  //图文消息模板 
  public static final String TEMPLATE_NEWS = "<xml><ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[%s]]></MsgType><ArticleCount>%s</ArticleCount><Articles><item><Title><![CDATA[%s]]></Title><Description><![CDATA[%s]]></Description><PicUrl><![CDATA[%s]]></PicUrl><Url><![CDATA[%s]]></Url></item></Articles></xml>";
  
  private String ToUserName;   //接收方帐号（收到的OpenID）,一般就在请求的参数中
  private String FromUserName; //开发者微信号,一般就在请求的参数中
  private String CreateTime;   //消息创建时间 （整型）
  private MessageType MsgType;      //消息类型
  private String Content;      //文本消息  回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
  private String MediaId;      //图片、语音、视频   通过素材管理中的接口上传多媒体文件，得到的id
  private String ArticleCount; //图文消息个数；当用户发送文本、图片、视频、图文、地理位置这五种消息时，开发者只能回复1条图文消息；其余场景最多可回复8条图文消息
  private String Articles;     //图文消息信息，注意，如果图文数超过限制，则将只发限制内的条数
  private String Title;        //资源标题
  private String Description;  //资源描述
  private String PicUrl;    //图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
  private String Url;       //点击图文消息跳转链接
  private String MusicURL;  //音乐链接
  private String HQMusicUrl;  //高质量音乐链接，WIFI环境优先使用该链接播放音乐
  private String ThumbMediaId; //缩略图的媒体id，通过素材管理中的接口上传多媒体文件，得到的id
  
  
  /**
   * 获取消息发送出去的合法字符串
   * @return
   */
  public String toMessageString() {
    if(null == MsgType) return "";
    switch(MsgType.code()) {
      case 0:
         return String.format(TEMPLATE_TEXT,ToUserName,FromUserName,CreateTime,MsgType.type(),Content);
      case 1:
        return String.format(TEMPLATE_IMAGE,ToUserName,FromUserName,CreateTime,MsgType.type(),MediaId);
      case 2:
        return String.format(TEMPLATE_VOICE,ToUserName,FromUserName,CreateTime,MsgType.type(),MediaId);
      case 3:
        return String.format(TEMPLATE_VIDEO,ToUserName,FromUserName,CreateTime,MsgType.type(),MediaId,Title,Description);
      case 4:
        return String.format(TEMPLATE_MUSIC,ToUserName,FromUserName,CreateTime,MsgType.type(),Title,Description,MusicURL,HQMusicUrl,ThumbMediaId);
      case 5:
        return String.format(TEMPLATE_NEWS,ToUserName,FromUserName,CreateTime,MsgType.type(),ArticleCount,Title,Description,PicUrl,Url);
    }
    return "";
  }
  
  public String getToUserName() {
    return ToUserName;
  }
  public String getFromUserName() {
    return FromUserName;
  }
  public String getCreateTime() {
    return CreateTime;
  }
  public MessageType getMsgType() {
    return MsgType;
  }
  public String getContent() {
    return Content;
  }
  public String getMediaId() {
    return MediaId;
  }
  public String getArticleCount() {
    return ArticleCount;
  }
  public String getArticles() {
    return Articles;
  }
  public String getTitle() {
    return Title;
  }
  public String getDescription() {
    return Description;
  }
  public String getPicUrl() {
    return PicUrl;
  }
  public String getUrl() {
    return Url;
  }
  public String getMusicURL() {
    return MusicURL;
  }
  public String getHQMusicUrl() {
    return HQMusicUrl;
  }
  public String getThumbMediaId() {
    return ThumbMediaId;
  }
  public void setToUserName(String toUserName) {
    ToUserName = toUserName;
  }
  public void setFromUserName(String fromUserName) {
    FromUserName = fromUserName;
  }
  public void setCreateTime(String createTime) {
    CreateTime = createTime;
  }
  public void setMsgType(MessageType msgType) {
    MsgType = msgType;
  }
  public void setContent(String content) {
    Content = content;
  }
  public void setMediaId(String mediaId) {
    MediaId = mediaId;
  }
  public void setArticleCount(String articleCount) {
    ArticleCount = articleCount;
  }
  public void setArticles(String articles) {
    Articles = articles;
  }
  public void setTitle(String title) {
    Title = title;
  }
  public void setDescription(String description) {
    Description = description;
  }
  public void setPicUrl(String picUrl) {
    PicUrl = picUrl;
  }
  public void setUrl(String url) {
    Url = url;
  }
  public void setMusicURL(String musicURL) {
    MusicURL = musicURL;
  }
  public void setHQMusicUrl(String hQMusicUrl) {
    HQMusicUrl = hQMusicUrl;
  }
  public void setThumbMediaId(String thumbMediaId) {
    ThumbMediaId = thumbMediaId;
  }
  
   
}
