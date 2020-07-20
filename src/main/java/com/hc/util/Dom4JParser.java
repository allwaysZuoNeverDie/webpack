package com.hc.util;

import com.hc.model.Project;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class Dom4JParser {

    public static List<Project> parserDocument() {
        List projescts = null;
        try {
            File file = new File("E:\\Workspace\\webpack\\src\\main\\resources\\project.xml");
            if (file.exists()) {
                SAXReader saxReader = new SAXReader();
                Document document = saxReader.read(file);
                projescts = reader(document);
                document.clearContent();
            } else {
                System.out.println("找不到项目配置文件: ./project.xml");
            }
        } catch (Exception e) {
            System.out.println("读取配置文件异常.");
        }
        return projescts;
    }

    private static List<Project> reader(Document document) {
        List projescts = null;
        Element root = document.getRootElement();

        List projectsel = root.element("projects").elements();
        if ((projectsel != null) && (!projectsel.isEmpty())) {
            projescts = new ArrayList();
            for (int i = 0; i < projectsel.size(); i++) {
                Element e = (Element) projectsel.get(i);
                String name = e.element("name").getStringValue();
                String workspace = e.element("workspace").getStringValue();

                Project project = new Project();
                project.setName(StringUtil.getNotNullVal(name).trim());
                project.setWorkspace(workspace);

                Element o = e.element("output");
                if (o != null) {
                    String output = e.element("output").getStringValue();
                    if (StringUtil.isNotEmpty(output)) {
                        project.setOutput(output);
                    }
                }
                o = e.element("ignore");
                if (o != null) {
                    String ignore = e.element("ignore").getStringValue();
                    if (StringUtil.isNotEmpty(ignore)) {
                        String[] temp = ignore.replaceAll("；", ";").split(";");
                        for (int j = 0; j < temp.length; j++) {
                            if (temp[j] != null) {
                                String v = temp[j].trim();
                                if (!v.startsWith("/")) {
                                    v = FileUtil.toPath(new Object[]{"/", v});
                                }
                                project.getIgnore().add(v);
                            }
                        }
                    }
                }
                String webroot = getRootFolder(project);
                if (webroot != null) {
                    project.setRoot(webroot);
                }
                projescts.add(project);
            }
        }
        return projescts;
    }

    private static String getRootFolder(Project pj) {
        String root = null;
        String work = FileUtil.toPath(new Object[]{pj.getWorkspace(), pj.getName()});
        File folder = new File(work);
        if ((folder.exists()) && (folder.isDirectory())) {
            File[] t = folder.listFiles(new FileFilter() {
                public boolean accept(File pathname) {
                    return pathname.isDirectory();
                }
            });
            for (int i = t.length - 1; i >= 0; i--) {
                File f = new File(t[i], "WEB-INF");
                if (f.exists()) {
                    root = t[i].getName();
                    break;
                }
            }
        }
        return root;
    }
}
