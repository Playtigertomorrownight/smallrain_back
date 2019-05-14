package com.wangying.smallrain.markdown;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;

import com.google.common.base.Joiner;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.options.MutableDataSet;
import com.wangying.smallrain.utils.BaseUtils;

public class MarkDown2HtmlWrapper {

  private static String MD_CSS = null;
  
  private static final String MARKDOWN_CSS_PATH = "markdown-style/markdown.css";

  static {
      try {
          ClassPathResource resource = new ClassPathResource(MARKDOWN_CSS_PATH);
          InputStream inputStream = resource.getInputStream();
          byte [] cssStyleByte = BaseUtils.inputStreamToByteArray(inputStream);
          String cssStyleString = new String(cssStyleByte,"UTF-8");
          MD_CSS = "<style type=\"text/css\">\n" + cssStyleString + "\n</style>\n";
          cssStyleString = null;
      } catch (Exception e) {
          MD_CSS = "";
      }
  }


  /**
   * 将本地的markdown文件，转为html文档输出
   *
   * @param path 相对地址or绝对地址 ("/" 开头)
   * @return
   * @throws IOException
   */
  public static MarkdownEntity ofFile(String path) throws IOException {
      ClassPathResource resource = new ClassPathResource(path);
      InputStream inputStream = resource.getInputStream();
      return ofStream(inputStream);
  }



  /**
   * 将流转为html文档输出
   *
   * @param stream
   * @return
   */
  public static MarkdownEntity ofStream(InputStream stream) {
      BufferedReader bufferedReader = new BufferedReader(
          new InputStreamReader(stream, Charset.forName("UTF-8")));
      List<String> lines = bufferedReader.lines().collect(Collectors.toList());
      String content = Joiner.on("\n").join(lines);
      return ofContent(content);
  }


  /**
   * 直接将markdown语义的文本转为html格式输出
   *
   * @param content markdown语义文本
   * @return
   */
  public static MarkdownEntity ofContent(String content) {
      String html = parse(content);
      MarkdownEntity entity = new MarkdownEntity();
      entity.setCss(MD_CSS);
      entity.setHtml(html);
      entity.addDivStyle("class", "markdown-body ");
      return entity;
  }


  /**
   * markdown to image
   *
   * @param content markdown contents
   * @return parse html contents
   */
  public static String parse(String content) {
      MutableDataSet options = new MutableDataSet();
      options.setFrom(ParserEmulationProfile.MARKDOWN);

      // enable table parse!
      options.set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create()));


      Parser parser = Parser.builder(options).build();
      HtmlRenderer renderer = HtmlRenderer.builder(options).build();

      Node document = parser.parse(content);
      return renderer.render(document);
  }

}
