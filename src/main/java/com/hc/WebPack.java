//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hc;

import com.hc.model.Project;
import com.hc.util.Dom4JParser;
import com.hc.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebPack {
    static String TIME = "";

    public WebPack() {
    }

    public static void main(String[] args) {


        List<Project> pjs = Dom4JParser.parserDocument();
        if (pjs != null && !pjs.isEmpty()) {
            System.out.println("从清单文件打包...");
             DateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
            TIME = format.format(new Date());

            try {
                Iterator var7 = pjs.iterator();

                while (var7.hasNext()) {
                    Project pj = (Project) var7.next();
                    System.out.println(String.format("[%s] start...", pj.getName()));
                    diffPack(pj);


                    System.out.println(String.format("[%s] finish!", pj.getName()));
                    System.out.println(String.format("***********************", pj.getName()));
                }
            } catch (Exception var8) {
                var8.printStackTrace();
                System.out.println(var8);
            }
        }

    }

    public static void diffPack(Project pj) {
        FileWriter writer = null;
        try {
            Set<String> set = getFiles(pj);
            if (set != null) {
                List<String> changelist = new ArrayList();
                changelist.addAll(set);
                if (changelist != null && !changelist.isEmpty()) {
                    System.out.println(String.format("[%s] 已从清单文件中读取到 %d 条记录, 文件清单如下↓", pj.getName(), changelist.size()));
                    Iterator var5 = changelist.iterator();

                    while (var5.hasNext()) {
                        String e = (String) var5.next();
                        String path = getSubFile(FileUtil.toPath(new Object[]{pj.getWorkspace(), pj.getName()}), e);
                        if (pj.isIgnore(path)) {
                            System.out.println(String.format("**ignore**\t%s", path));
                        } else {
                            boolean flag = copyFiles(pj, e);
                            if (flag) {
                                if (writer == null) {
                                    File file = new File(FileUtil.toPath(new Object[]{"target", TIME}));
                                    if (!file.exists()) {
                                        file.mkdirs();
                                    }

                                    writer = new FileWriter(FileUtil.toPath(new Object[]{"target", TIME, "文件清单_" + pj.getName() + ".txt"}));
                                }

                                writer.append(path + "\r\n");
                                System.out.println(String.format("**ok**\t%s", path));
                            }
                        }
                    }

                    return;
                }

                System.out.println(String.format("[%s] 清单文件中找不到相关记录!", pj.getName()));
                return;
            }
        } catch (IOException var18) {
            System.out.println(var18);
            return;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException var17) {
                    ;
                }
            }
            
        }

    }

    private static Set<String> getFiles(Project pj) {
        FileReader fr = null;
        BufferedReader bf = null;
        TreeSet set = null;

        try {
            File txt = new File(FileUtil.toPath(new Object[]{"E:\\Workspace\\webpack\\src\\main\\resources\\", "文件清单[" + pj.getName() + "].txt"}));
            if (txt.exists()) {
                System.out.println(String.format("[%s] 读取清单文件: %s", pj.getName(), FileUtil.toPath(new Object[]{"target", "文件清单[" + pj.getName() + "].txt"})));
                fr = new FileReader(txt);
                bf = new BufferedReader(fr);
                set = new TreeSet();
                String str = null;

                while ((str = bf.readLine()) != null) {
                    str = str.trim();
                    if (!str.equals("") && !str.startsWith("#")) {
                        set.add(FileUtil.toPath(new Object[]{FileUtil.toPath(new Object[]{pj.getWorkspace(), pj.getName()}), str.trim()}));
                    }
                }
            } else {
                System.out.println(String.format("[%s] 找不到文件: %s", pj.getName(), FileUtil.toPath(new Object[]{"target", "文件清单[" + pj.getName() + "].txt"})));
            }
        } catch (IOException var18) {
            System.out.println(var18);
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException var17) {
                    ;
                }
            }

            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException var16) {
                    ;
                }
            }

        }

        return set;
    }

    public static String getSubFile(String root, String real_path) {
        return real_path.substring(real_path.indexOf(root) + root.length());
    }

    public static boolean copyFiles(Project pj, String file) {
        boolean flag = false;
        String path = getSubFile(FileUtil.toPath(new Object[]{pj.getWorkspace(), pj.getName()}), file);
        FileUtil.copy(file, FileUtil.toPath(new Object[]{"target", TIME, pj.getName(), path}));
        String subFile;
        if (isSrcPath(path)) {
            if (isJavaFile(path)) {
                subFile = getFilePath(path, pj.getRoot());
                String temp_file = subFile.replace(".java", ".class");
                String packageName = subFile.substring(0, subFile.lastIndexOf("/") + 1);
                /*Collection<File> files =
                        listClassFiles(FileUtil.toPath(new Object[]{pj.getWorkspace(), pj.getName(), "out\\artifacts\\" + pj.getName() + "_Web_exploded", "WEB-INF", "classes", temp_file}));*/
                String a = FileUtil.toPath(new Object[]{pj.getWorkspace(), pj.getName(), "target/" + pj.getName() , "WEB-INF", "classes", temp_file});
                Collection<File> files = listClassFiles(a);
                if (files != null) {
                    Iterator var9 = files.iterator();

                    while (var9.hasNext()) {
                        File e = (File) var9.next();
                        FileUtil.copy(e, FileUtil.toPath(new Object[]{"target", TIME, pj.getOutput(), "WEB-INF", "classes", packageName, e.getName()}));
                    }
                }
            } else {
                subFile = getFilePath(path, pj.getRoot());
                /*String b = FileUtil.toPath(new Object[]{"target", TIME, pj.getOutput(), "WEB-INF", "classes", subFile});*/
                String b = FileUtil.toPath(new Object[]{"target", TIME, pj.getOutput(), "WEB-INF", "classes", subFile});

                FileUtil.copy(file, b);
            }

            flag = true;
        } else if (isWebRootPath(path, pj.getRoot())) {
            subFile = getFilePath(path, pj.getRoot());
            FileUtil.copy(file, FileUtil.toPath(new Object[]{"target", TIME, pj.getOutput(), subFile}));
            flag = true;
        }

        return flag;
    }

    public static String getFilePath(String path, String root) {
        Pattern pattern = Pattern.compile("^\\/(src|" + root + ")\\/(.*)");
        Matcher matcher = pattern.matcher(path);
        matcher.find();
        return matcher.group(2);
    }

    public static String getLocation(String path, String root) {
        Pattern pattern = Pattern.compile("^\\/(src|" + root + ")\\/.*");
        Matcher matcher = pattern.matcher(path);
        matcher.find();
        return matcher.group(1);
    }

    public static boolean isWebRootPath(String path, String root) {
        Pattern pattern = Pattern.compile("^\\/(" + root + ")\\/.*");
        Matcher matcher = pattern.matcher(path);
        return matcher.find();
    }

    public static boolean isSrcPath(String path) {
        Pattern pattern = Pattern.compile("^\\/src\\/.*");
        Matcher matcher = pattern.matcher(path);
        return matcher.find();
    }

    public static boolean isJavaFile(String path) {
        return path.endsWith(".java");
    }

    public static Collection<File> listClassFiles(String path) {
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            final String filename = file.getName();
            final String str = filename.substring(0, filename.lastIndexOf("."));
            final String suffix = filename.substring(filename.lastIndexOf("."));
            IOFileFilter fileFilter = new AbstractFileFilter() {
                public boolean accept(File dir, String name) {
                    return name.equals(filename) || name.startsWith(str + "$") && name.endsWith(suffix);
                }
            };
            Collection<File> list = FileUtils.listFiles(file.getParentFile(), fileFilter, (IOFileFilter) null);
            return list;
        } else {
            return null;
        }
    }
}
