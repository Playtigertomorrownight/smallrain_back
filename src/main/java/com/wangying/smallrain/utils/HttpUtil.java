package com.wangying.smallrain.utils;

import java.io.IOException;
import java.net.URI;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * Http 工具类，用于发送http请求
 * 
 * @author wangying.dz3
 *
 */
public class HttpUtil {

  private static Logger log = LoggerFactory.getLogger(HttpUtil.class);

  private static final CloseableHttpClient httpClient;
  private static CloseableHttpClient httpSslClient;
  public static final String CHARSET = "utf-8";
  // 采用静态代码块，初始化超时时间配置，再根据配置生成默认httpClient对象
  static {
    // 连接管理器，使用无惨构造
    PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
    connManager.setMaxTotal(20); // 最大连接数
    // 禁用重试(参数：retryCount、requestSentRetryEnabled)
    HttpRequestRetryHandler requestRetryHandler = new DefaultHttpRequestRetryHandler(0, false);
    // 创建HttpClient
    RequestConfig config = RequestConfig.custom().setConnectTimeout(30000).setSocketTimeout(30000)
        .setConnectionRequestTimeout(5000).build();
    httpClient = HttpClients.custom().setConnectionManager(connManager).setDefaultRequestConfig(config)
        .setRetryHandler(requestRetryHandler).build();
    // 创建了忽略整数验证的CloseableHttpClient对象
    try {
      SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
        public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
          return true; // 信任所有
        }
      }).build();
      SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
      httpSslClient = HttpClients.custom().setConnectionManager(connManager).setDefaultRequestConfig(config)
          .setRetryHandler(requestRetryHandler).setSSLSocketFactory(sslsf).build();
    } catch (Exception e) {
      e.printStackTrace();
      httpSslClient = HttpClients.createDefault();
    }
  }

  /**
   * http GET 请求
   * 
   * @param url
   * @param headerMap
   * @param params
   * @return
   * @throws ApproveException
   */
  public static String doGet(String url, Map<String, String> headerMap, Map<String, Object> params) {
    return send(url, headerMap, params, HttpMethods.GET);
  }
  
  /**
   * http PUT 请求
   * @param url
   * @param headerMap
   * @param params
   * @return
   * @throws ApproveException
   */
  public static String doPut(String url, Map<String, String> headerMap, Map<String, Object> params) {
    return send(url, headerMap, params, HttpMethods.PUT);
  }

  /**
   * http POST 请求
   * 
   * @param url
   * @param headerMap
   * @param params
   * @return
   * @throws ApproveException
   * @throws IOException
   */
  public static String doPost(String url, Map<String, String> headerMap, Map<String, Object> params) {
    return send(url, headerMap, params, HttpMethods.POST);
  }
  
  public static String doPostWithJSON(String url, Map<String, String> headerMap, Map<String, Object> params) {
    Map<String, String> headers = new HashMap<String, String>();
    if(null != headerMap && !headerMap.isEmpty()) {
      headers.putAll(headerMap);
    }
    headers.put("Content-Type", "application/json");
    return send(url, headers, params, HttpMethods.POST);
  }

  /**
   * http 请求的通用方法
   * 
   * @param url
   * @param headerMap
   * @param params
   * @param method
   * @return
   * @throws ApproveException
   */
  private static String send(String url, Map<String, String> headerMap, Map<String, Object> params, HttpMethods method) {
    // 请求地址不能为空
    url = url.trim(); // 去收尾空格
    log.info("http request url as: " + url);
    CloseableHttpResponse response = null;
    try {
      // 获取请求对象
      HttpRequestBase request = getRequest(method);
      // 参数设置
      if (params != null && !params.isEmpty()) {
        List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
          String value = String.valueOf(entry.getValue());
          if (value != null) {
            pairs.add(new BasicNameValuePair(entry.getKey(), value));
          }
        }
        if (pairs != null && pairs.size() > 0) {
          // 如果是requestBody请求体
          if (HttpEntityEnclosingRequestBase.class.isAssignableFrom(request.getClass())) {
            // 设置参数
            if(null!=headerMap&&headerMap.containsKey("Content-Type")&&headerMap.get("Content-Type")!=null&&"application/json".equals(headerMap.get("Content-Type").toString())) {
              StringEntity s = new StringEntity(JSONObject.toJSONString(params),CHARSET);
              s.setContentEncoding(CHARSET);
              s.setContentType("application/json");//发送json数据需要设置contentType
              ((HttpEntityEnclosingRequestBase) request).setEntity(s);
            }else {
              ((HttpEntityEnclosingRequestBase) request).setEntity(new UrlEncodedFormEntity(pairs, CHARSET));
            }
          } else {
            // 将请求参数和url进行拼接,已包含参数?
            url += url.contains("?") ? "&" : "?";
            url += EntityUtils.toString(new UrlEncodedFormEntity(pairs, CHARSET));
          }
        }
      }
      // 设置请求地址
      request.setURI(URI.create(url));
      // 生成请求头信息
      List<Header> headers = new ArrayList<Header>();
      if (null != headerMap && !headerMap.isEmpty()) {
        Set<String> headKeys = headerMap.keySet();
        for (String key : headKeys) {
          headers.add(new BasicHeader(key, (String) headerMap.get(key)));
        }
      }
      Header[] headerList = headers.toArray(new Header[headers.size()]);
      // 设置请求头
      request.setHeaders(headerList);
      // 发送请求
      if (url.startsWith("https")) {
        response = httpSslClient.execute(request);
      } else {
        response = httpClient.execute(request);
      }
      int statusCode = response.getStatusLine().getStatusCode();
      // 请求出现异常
      if (statusCode != 200) {
        request.abort();
        log.error("HttpClient error , status code :" + statusCode);
      }
      // 获取相应结果
      String result = null;
      HttpEntity entity = response.getEntity();
      if (entity != null) {
        result = EntityUtils.toString(entity, CHARSET);
        log.info("请求结果：" + result);
      } else {
        result = response.getStatusLine().toString();
      }
      EntityUtils.consume(entity);

      return result;

    } catch (ParseException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return null;
  }

  /**
   * 根据请求方法和地址获取请求对象
   * 
   * @param url
   * @param method
   * @return
   */
  private static HttpRequestBase getRequest(HttpMethods method) {
    HttpRequestBase request = null;
    switch (method.getCode()) {
    case 0:// HttpGet
      request = new HttpGet();
      break;
    case 1:// HttpPost
      request = new HttpPost();
      break;
    case 2:// HttpPost
      request = new HttpPut();
      break;
    }
    return request;
  }

  /**
   * 拼接请求地址
   */
  public static String getRequestUrl(String root, String... params) {
    StringBuffer sbr = new StringBuffer(root);
    boolean isParam = false;
    for (String s : params) {
      if (StringUtils.isEmpty(s))
        return null;
      if (s.startsWith(".")) { // 数据格式
        sbr.append(s);
      } else if (s.startsWith("?") || isParam) { // 参数
        sbr.append(s);
        isParam = true;
      } else { // 路径
        sbr.append("/").append(s);
      }
    }
    return sbr.toString();
  }

  /**
   * 请求方法枚举
   * 
   * @author wangying.dz3
   *
   */
  enum HttpMethods {
    GET(0, "GET"), POST(1, "POST"), PUT(2, "PUT"), DELETE(3, "DELETE");
    private int code;
    private String name;

    private HttpMethods(int code, String name) {
      this.code = code;
      this.name = name;
    }

    public String getName() {
      return name;
    }

    public int getCode() {
      return code;
    }
  }

}
