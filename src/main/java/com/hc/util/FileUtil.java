 package com.hc.util;
 
 import java.io.BufferedReader;
 import java.io.BufferedWriter;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.FileNotFoundException;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.InputStreamReader;
 import java.io.OutputStream;
 import java.io.OutputStreamWriter;
 import java.io.Writer;
 
 public class FileUtil
 {
   public static String toPath(Object[] obj)
   {
     return toPath(false, false, obj);
   }
 
   public static String toPath(boolean startSeparator, boolean endSeparator, Object[] obj)
   {
     Object[] arr = (Object[])obj.clone();
     if (arr != null) {
       StringBuffer buf = new StringBuffer();
       if (startSeparator) {
         buf.append("/");
       }
       int len = arr.length - 1;
       for (int i = 0; i <= len; i++) {
           if ((arr[i] != null) && (!"".equals(arr[i]))) {
               if ((i != 0) && (!buf.toString().endsWith("/")) && (!String.valueOf(arr[i]).startsWith("/"))) {
                   buf.append("/");
               }
               buf.append(arr[i]);
           }
       }
       if (endSeparator) {
         buf.append("/");
       }
       return buf.toString();
     }
     return null;
   }
 
   public static String toPath(String path) {
     if (path != null) {
       path = path.replaceAll("\\\\", "/");
       Object[] temp = path.split("/");
       boolean startSeparator = false; boolean endSeparator = false;
       if (path.startsWith("/")) {
         startSeparator = true;
       }
       if (path.endsWith("/")) {
         endSeparator = true;
       }
       return toPath(startSeparator, endSeparator, temp);
     }
     return null;
   }
 
   public static InputStream getInputStream(File source)
     throws FileNotFoundException
   {
     return new FileInputStream(source);
   }
 
   public static InputStream getInputStream(String source) throws FileNotFoundException
   {
     return getInputStream(new File(source));
   }
 
   public static OutputStream getOutputStream(File dest)
     throws FileNotFoundException
   {
     return new FileOutputStream(dest);
   }
 
   public static OutputStream getOutputStream(String dest) throws FileNotFoundException
   {
     return getOutputStream(new File(dest));
   }
 
   public static boolean copy(File source, File dest)
   {
     if (!source.exists()) {
       return false;
     }
     boolean flag = false;
     InputStream in = null;
     OutputStream output = null;
     try {
       if (!dest.getParentFile().exists()) {
         dest.getParentFile().mkdirs();
       }
       in = getInputStream(source);
       output = getOutputStream(dest);
       byte[] b = new byte[1024];
       int n;
       while ((n = in.read(b)) != -1)
       {
         output.write(b, 0, n);
       }
       flag = true;
     } catch (IOException e) {
       e.printStackTrace();
     } finally {
       closeStream(in);
       closeStream(output);
     }
     return flag;
   }
 
   public static boolean copy(File source, String dest) {
     return copy(source, new File(dest));
   }
 
   public static boolean copy(String source, File dest) {
     return copy(new File(source), dest);
   }
 
   public static boolean copy(String source, String dest) {
     return copy(new File(source), dest);
   }
 
   public static void copyFolder(String oldPath, String newPath) {
     try {
       new File(newPath).mkdirs();
       File a = new File(oldPath);
       String[] file = a.list();
       File temp = null;
       for (int i = 0; i < file.length; i++) {
         if (oldPath.endsWith("/")) {
             temp = new File(oldPath + file[i]);
         } else {
           temp = new File(oldPath + "/" + file[i]);
         }
 
         if (temp.isFile()) {
           FileInputStream input = new FileInputStream(temp);
           FileOutputStream output = new FileOutputStream(newPath + "/" + temp.getName().toString());
           byte[] b = new byte[5120];
           int len;
           while ((len = input.read(b)) != -1)
           {
             output.write(b, 0, len);
           }
           output.flush();
           output.close();
           input.close();
         }
         if (temp.isDirectory()) {
             copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
         }
       }
     }
     catch (Exception e) {
       e.printStackTrace();
     }
   }
 
   public static boolean move(File source, File dest)
   {
     boolean flag = false;
     if (source.exists()) {
       flag = copy(source, dest);
       source.delete();
     }
     return flag;
   }
 
   public static boolean move(File source, String dest) {
     return move(source, new File(dest));
   }
 
   public static boolean move(String source, File dest) {
     return move(new File(source), dest);
   }
 
   public static boolean move(String source, String dest) {
     return move(new File(source), dest);
   }
 
   public static void delete(File source)
   {
     if (source.exists()) {
         source.delete();
     }
   }
 
   public static void delete(String source)
   {
     File s = new File(source);
     delete(s);
   }
 
   public static void closeStream(InputStream in) {
     try {
       if (in != null) {
           in.close();
       }
     }
     catch (Exception localException) {
     }
   }
 
   public static void closeStream(OutputStream out) {
     try {
       if (out != null) {
           out.close();
       }
     }
     catch (Exception localException)
     {
     }
   }
 
   public static boolean mkdir(String folderPath)
   {
     File file = new File(folderPath);
     boolean flag = file.mkdirs();
     return flag;
   }
 
   public static String getContent(File file, String encoding)
     throws IOException
   {
     InputStreamReader read = new InputStreamReader(
       new FileInputStream(file), encoding);
 
     BufferedReader bufferedReader = new BufferedReader(read);
 
     StringBuffer bufferStr = new StringBuffer(1024);
 
     String tempLine = null;
 
     String newLine = System.getProperty("line.separator");
 
     while ((tempLine = bufferedReader.readLine()) != null) {
       bufferStr.append(tempLine);
       bufferStr.append(newLine);
     }
 
     bufferedReader.close();
     read.close();
 
     String turnStr = bufferStr.toString();
 
     return turnStr;
   }
 
   public static void writeStr(File file, String content)
     throws IOException
   {
     FileOutputStream outStream = new FileOutputStream(file);
 
     Writer write = new OutputStreamWriter(outStream, "utf-8");
 
     BufferedWriter writeBuffer = new BufferedWriter(write);
 
     writeBuffer.write(content);
 
     writeBuffer.close();
     write.close();
     outStream.close();
   }
 }
