 package com.hc.model;
 
 import com.hc.util.FileUtil;
 import java.util.HashSet;
 import java.util.Set;
 
 public class Project
 {
   private String name;
   private String workspace;
   private String output = "ROOT";
   private String root = "WebRoot";
 
   private Set<String> ignore = new HashSet();
 
   public String getName() {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
 
   public String getWorkspace() {
     return this.workspace;
   }
 
   public void setWorkspace(String workspace) {
     this.workspace = workspace;
   }
 
   public String getOutput() {
     return this.output;
   }
 
   public void setOutput(String output) {
     this.output = output;
   }
 
   public Set<String> getIgnore() {
     return this.ignore;
   }
 
   public void addIgnore(String ignore) {
     this.ignore.add(ignore);
   }
 
   public boolean isIgnore(String ignore) {
     if ((ignore.startsWith("/.")) || (this.ignore.contains(ignore))) {
       return true;
     }
     String path = FileUtil.toPath(new Object[] { "/", getRoot(), "WEB-INF", "classes" });
     return ignore.startsWith(path);
   }
 
   public boolean isDefaultIgnore(String ignore) {
     if (ignore.startsWith("/.")) {
       return true;
     }
     String path = FileUtil.toPath(new Object[] { "/", getRoot(), "WEB-INF", "classes" });
     return ignore.startsWith(path);
   }
 
   public String getRoot() {
     return this.root;
   }
 
   public void setRoot(String root) {
     this.root = root;
   }
 
   public String getSubFile(String real_path) {
     String root = FileUtil.toPath(new Object[] { getWorkspace(), getName() });
     return real_path.substring(real_path.indexOf(root) + root.length());
   }
 }
