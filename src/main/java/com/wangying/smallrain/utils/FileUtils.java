package com.wangying.smallrain.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

public class FileUtils {

  public static final String FILE_SEPARATOR = System.getProperty("file.separator");

  /**
   * 复制文件的方法：0 -- 通过流复制 ； 1 -- Java NIO transferFrom方法复制
   */
  public static final Integer FILE_COPY_WAY = 1;

  /**
   * 复制文件文件夹地柜中记录文件夹大小
   */
  private static long COPY_DIR_SIZE = 0;

  private FileUtils() {
  }

  /**
   * 创建文件方法
   * 
   * @param file
   *          传入文件对象，不存在则创建父目录及其本身
   * @return
   * @throws IOException
   */
  public static File createNewFile(File file) throws IOException {
    if (null == file) {
      return null;
    }
    // 如果存在则直接返回，不存在则创建
    if (!file.exists()) {
      if (file.isDirectory()) { // 如果是目录,创建后返回
        file.mkdirs();
      } else { // 如果是文件，先建父目录,后创建文件本身
        if (!file.getParentFile().exists()) {
          file.getParentFile().mkdirs();
        }
        file.createNewFile();
      }
    }
    return file;
  }

  /**
   * 创建文件方法
   * 
   * @param path
   *          传入文件路径，创建父目录及其本身
   * @return
   * @throws IOException
   */
  public static File createNewFile(String path) throws IOException {
    if (path == null || "".equals(path.trim()))
      return null;
    File file = new File(formatFilePath(path));
    return createNewFile(file);
  }

  /**
   * 创建文件夹的方法
   * 
   * @param path
   *          传入文件路径，创建父目录及其本身
   * @return
   * @throws IOException
   */
  public static File createNewDir(String path) throws IOException {
    if (path == null || "".equals(path.trim()))
      return null;
    File file = new File(formatFilePath(path));
    Boolean success = true;
    if (!file.exists())
      success = file.mkdir();
    return success ? file : null;
  }

  /**
   * 获取该系统真实文件路径兼容windows和linux
   * 
   * @param path
   * @return
   */
  public static String formatFilePath(String path) {
    return path.replace("/", FILE_SEPARATOR).replace("\\", FILE_SEPARATOR);
  }

  /**
   * 获取http真实路径
   * 
   * @param path
   * @return
   */
  public static String formatHttpURLPath(String path) {
    return path.replace("\\", "/");
  }

  
  /**
   * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
   * @throws Exception 
   * @throws IOException 
   */
  public static byte[] readFileByBytes(String path) throws Exception {
    if (null == path)
      throw new Exception("File Object is Null!");
    File file = new File(path);
    if (!file.exists())
      throw new Exception("Source File is Not Exits!");
    InputStream in = null;
    byte[] result = null;
    try {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      byte[] tempbytes = new byte[1024];
      int length = 0;
      in = new FileInputStream(file);
      // 读入多个字节到字节数组中，byteread为一次读入的字节数
      while ((length = in.read(tempbytes)) != -1) {
        out.write(tempbytes, 0, length);
      }
      result = out.toByteArray();
      out.close();
    } catch (Exception e1) {
      e1.printStackTrace();
    } finally {
      if (in != null) {
        in.close();
      }
    }
    return result;
  }
  
  /**
   * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
   * @throws Exception 
   * @throws IOException 
   */
  public static String readFileByString(String path) throws Exception {
     return new String(readFileByBytes(path),"UTF-8");
  }

  /**
   * 复制文件
   * 
   * @param source
   *          源文件
   * @param aim
   *          目标文件
   */
  public static long copyFile(File source, File aim) throws Exception {
    System.out.println("copy file: " + source + " to : " + aim);
    long result = 0;
    switch (FILE_COPY_WAY) {
    case 0:
      result = copyFileByStream(source, aim);
      break;
    case 1:
      result = copyFileByNIO(source, aim);
      break;
    }
    System.out.println("the size is: " + result);
    return result;
  }

  /**
   * 复制文件夹操作，将正文目录复制到目标目录下
   * 
   * @param source
   *          源文件
   * @param aim
   *          目标文件
   * @return
   * @throws Exception
   */
  public static long copyDir(File source, File aim) throws Exception {
    if (null == source || null == aim)
      throw new Exception("File Object is Null!");
    if (!source.exists())
      throw new Exception("Source File is Not Exits!");
    if (!aim.exists()) {
      aim = createNewDir(aim.getPath()); // 确定目标文件夹存在
    }
    COPY_DIR_SIZE = 0;
    copyDirFiles(source.getName(), source, aim.getPath());

    return COPY_DIR_SIZE;
  }

  /**
   * 复制文件夹操作，将正文目录复制到目标目录下
   * 
   * @param source
   *          源文件
   * @param aim
   *          目标文件
   * @return
   * @throws Exception
   */
  public static long copyDir(String source, String aim) throws Exception {
    if (source == null || "".equals(source.trim()) || aim == null || "".equals(aim.trim()))
      return 0;
    File sourceFile = new File(formatFilePath(source));
    File aimFile = createNewDir(aim);
    if (aimFile == null)
      throw new Exception("Failed to create directory ：" + aim);
    return copyDir(sourceFile, aimFile);

  }

  /**
   * 复制文件，主要用于复制文件夹时的递归操作
   * 
   * @param sourceRootPath
   * @param from
   * @param to
   * @param totalSize
   * @throws Exception
   */
  private static void copyDirFiles(String sourceRootPath, File from, String to) throws Exception {
    System.out.println("current total size is: " + COPY_DIR_SIZE);
    if (from.isDirectory()) {
      File[] files = from.listFiles();
      for (File f : files) {
        if (f.isDirectory()) { // 如果是文件夹，递归
          copyDirFiles(sourceRootPath, f, to);
        } else { // 如果是文件
          COPY_DIR_SIZE += copyDirFilesWithRoot(sourceRootPath, f, to);
        }
      }
    } else {
      COPY_DIR_SIZE += copyDirFilesWithRoot(sourceRootPath, from, to);
    }
  }

  /**
   * 通根路径复制文件到指定父目录
   * 
   * @param sourceRootPath
   * @param from
   * @param to
   * @return
   * @throws Exception
   */
  private static long copyDirFilesWithRoot(String sourceRootPath, File from, String to) throws Exception {
    String path = from.getPath();
    String toPath = path.substring(path.indexOf(sourceRootPath), path.length());
    return copyFile(from, new File(to, toPath));
  }

  /**
   * 复制文件
   * 
   * @param source
   *          源文件路径
   * @param aim
   *          目标文件路径
   */
  public static long copyFile(String source, String aim) throws Exception {
    if (source == null || "".equals(source.trim()) || aim == null || "".equals(aim.trim()))
      return 0;
    File sourceFile = createNewFile(source);
    File aimFile = createNewFile(aim);
    return copyFile(sourceFile, aimFile);
  }

  /**
   * 通过文件流复制文件
   * 
   * @param source
   *          源文件
   * @param aim
   *          目标文件
   * @throws Exception
   */
  public static long copyFileByStream(File source, File aim) throws Exception {
    if (null == source || null == aim)
      throw new Exception("File Object is Null!");
    if (!source.exists())
      throw new Exception("Source File is Not Exits!");
    if (!aim.exists())
      createNewFile(aim);
    // 创建相关流
    InputStream inStream = null;
    OutputStream outStream = null;
    long size = 0;
    try {
      inStream = new FileInputStream(source);
      outStream = new FileOutputStream(aim);
      // 定义缓冲区
      byte[] buffer = new byte[1024];
      int buflen = 0;
      // 开始复制
      while ((buflen = inStream.read(buffer)) > 0) {
        outStream.write(buffer, 0, buflen);
        size += buflen;
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      // 关闭流
      if (inStream != null)
        inStream.close();
      if (outStream != null)
        outStream.close();
    }
    return size;
  }

  /**
   * 将流写入到文件中
   * 
   * @param source
   * @param aimPath
   * @return
   * @throws Exception
   */
  public static long writeStreamToFile(InputStream source, String aimPath) throws Exception {
    if (null == source || null == aimPath)
      throw new Exception("File Object is Null!");
    File aim = new File(aimPath);
    if (!aim.exists())
      createNewFile(aim);
    // 创建相关流
    InputStream inStream = source;
    OutputStream outStream = null;
    long size = 0;
    try {
      outStream = new FileOutputStream(aim);
      // 定义缓冲区
      byte[] buffer = new byte[1024];
      int buflen = 0;
      // 开始复制
      while ((buflen = inStream.read(buffer)) > 0) {
        outStream.write(buffer, 0, buflen);
        size += buflen;
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      // 关闭流
      if (inStream != null)
        inStream.close();
      if (outStream != null)
        outStream.close();
    }
    return size;
  }

  /**
   * Java NIO transferFrom方法复制
   * 
   * @param source
   *          源文件
   * @param aim
   *          目标文件
   * @throws IOException
   * @throws FileNotFoundException
   */
  public static long copyFileByNIO(File source, File aim) throws Exception {
    if (null == source || null == aim)
      throw new Exception("File Object is Null!");
    if (!source.exists())
      throw new Exception("Source File is Not Exits!");
    if (!aim.exists())
      createNewFile(aim);
    // 创建相关流和管道
    FileInputStream inStream = null;
    FileOutputStream outStream = null;
    FileChannel inputChannel = null;
    FileChannel outputChannel = null;
    try {
      inStream = new FileInputStream(source);
      outStream = new FileOutputStream(aim);
      inputChannel = inStream.getChannel();
      outputChannel = outStream.getChannel();
      return outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      // 关闭流
      if (inStream != null)
        inStream.close();
      if (outStream != null)
        outStream.close();
      // 关闭管道
      if (inputChannel != null)
        inputChannel.close();
      if (outputChannel != null)
        outputChannel.close();
    }
    return 0;
  }

  /**
   * 删除文件夹
   * 
   * @param delpath
   *          待删除文件路径
   * @return
   * @throws Exception
   */
  public static boolean deleteDir(String delpath) throws Exception {
    File file = new File(delpath);
    if (null == file || !file.exists())
      throw new Exception("The file which to delete is invalid!");
    // 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true
    boolean isSuccess = true;
    if (!file.isDirectory()) {
      return file.delete();
    } else {
      String[] filelist = file.list();
      for (int i = 0; i < filelist.length; i++) {
        File delfile = new File(delpath, filelist[i]);
        if (!delfile.isDirectory()) {
          if (!delfile.delete())
            isSuccess = false;
          System.out.println("delete file: " + delfile.getPath() + " success!");
        } else {
          isSuccess = deleteDir(delpath + FILE_SEPARATOR + filelist[i]);
        }
      }
      if (!file.delete())
        isSuccess = false; // 删除文件夹
    }
    return isSuccess;
  }

  /**
   * 删除问价夹
   * 
   * @param delpath
   *          文件夹路径
   * @param deleteRoot
   *          是否删除本身
   * @return
   * @throws Exception
   */
  public static boolean deleteDir(String delpath, boolean deleteRoot) throws Exception {
    File file = new File(delpath);
    if (null == file || !file.exists())
      throw new Exception("The file which to delete is invalid!");
    // 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true
    boolean isSuccess = true;
    if (!file.isDirectory()) {
      return file.delete();
    } else {
      String[] filelist = file.list();
      for (int i = 0; i < filelist.length; i++) {
        File delfile = new File(delpath, filelist[i]);
        if (!delfile.isDirectory()) {
          if (!delfile.delete())
            isSuccess = false;
          System.out.println("delete file: " + delfile.getPath() + " success!");
        } else {
          isSuccess = deleteDir(delpath + FILE_SEPARATOR + filelist[i]);
        }
      }
      if (!file.delete())
        isSuccess = false; // 删除文件夹
    }
    return isSuccess;
  }

  /**
   * 删除文件夹
   * 
   * @param delpath
   *          待删除文件
   * @return
   * @throws Exception
   */
  public static boolean deleteDir(File delFile) throws Exception {
    if (null == delFile || !delFile.exists())
      throw new Exception("The file which to delete is not exits!");
    return deleteDir(delFile.getPath());
  }

  public static void readFile(File file) {

  }

}
