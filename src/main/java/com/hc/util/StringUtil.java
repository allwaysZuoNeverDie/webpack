package com.hc.util;

import java.util.List;

public class StringUtil
{
  public static String bin2hex(String bin)
  {
    char[] digital = "0123456789ABCDEF".toCharArray();
    StringBuffer sb = new StringBuffer("");
    byte[] bs = bin.getBytes();

    for (int i = 0; i < bs.length; i++) {
      int bit = (bs[i] & 0xF0) >> 4;
      sb.append(digital[bit]);
      bit = bs[i] & 0xF;
      sb.append(digital[bit]);
    }
    return sb.toString();
  }

  public static String hex2bin(String hex)
  {
    String digital = "0123456789ABCDEF";
    char[] hex2char = hex.toCharArray();
    byte[] bytes = new byte[hex.length() / 2];

    for (int i = 0; i < bytes.length; i++) {
      int temp = digital.indexOf(hex2char[(2 * i)]) * 16;
      temp += digital.indexOf(hex2char[(2 * i + 1)]);
      bytes[i] = ((byte)(temp & 0xFF));
    }
    return new String(bytes);
  }

  public static String substr(String str, Integer length) {
    if ((str != null) && (length != null)) {
      StringBuffer sb = new StringBuffer(str);
      return sb.delete(length.intValue(), str.length()).toString();
    }
    return str;
  }

  public static boolean isEmpty(String str) {
    if ((str == null) || (str.equals(""))) {
      return true;
    }
    return false;
  }

  public static boolean isEmpty(Object str) {
    if ((str == null) || (str.equals(""))) {
      return true;
    }
    return false;
  }

  public static boolean isNotEmpty(String str) {
    return !isEmpty(str);
  }

  public static boolean isNotEmpty(Object str) {
    return !isEmpty(str);
  }

  public static boolean isNull(String str) {
    if (str == null) {
      return true;
    }
    return false;
  }

  public static boolean isNotNull(String str) {
    return !isNull(str);
  }

  public static String getNotNullVal(String str) {
    if (isNull(str)) {
      return "";
    }
    return str;
  }

  public static String getNotNullVal(Integer str) {
    if (str == null) {
      return "";
    }
    return String.valueOf(str);
  }

  public static String listToString(List<?> list) {
    StringBuffer buf = new StringBuffer();
    if (list != null) {
      for (int i = 0; i < list.size(); i++) {
        buf.append(list.get(i).toString()).append("|");
      }
    }
    return buf.toString();
  }
}