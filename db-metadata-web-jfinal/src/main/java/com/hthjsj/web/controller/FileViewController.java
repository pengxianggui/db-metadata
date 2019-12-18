package com.hthjsj.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.hthjsj.web.kit.tree.TreeBuilder;
import com.hthjsj.web.kit.tree.TreeNode;
import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Class title:  文件树  <br/>
 * Describe:
 * 1.配置controller  me.add("/file", FileViewController.class);
 * Created by konbluesky           <br/>
 * Date : 2016/12/20 上午1:29       <br/>
 * Project : oss    <br/>
 */
@Slf4j
public class FileViewController extends Controller {

    /** 指定构建树时 根的默认标识(TreeBuilder和前端ztree)都需要 */
    public static final String ROOTPATH = "ROOT_PATH";

    /** 默认文件过滤器 过滤linux 隐藏文件 */
    public FilenameFilter DEFAULT_FILTER = new FilenameFilter() {

        private String name = "default";

        @Override
        public boolean accept(File dir, String name) {
            return !name.startsWith(".");
        }
    };

    /**
     * <pre>
     *  外部访问方法
     *  参数试例:
     *      http://0.0.0.0:8888/fileview/view?path=/
     *      http://0.0.0.0:8888/fileview/view?path=/target/classes/com
     *  说明:
     *      path 默认 / ->  PathKit.getWebRootPath()
     * </pre>
     */
    public void view() {
        boolean refresh = getParaToBoolean("refresh", false);
        Object jsons = null;
        String path = PathKit.getWebRootPath() + getPara("path", "");

        jsons = getTreeJson(path, refresh, DEFAULT_FILTER);
        renderJson(jsons);
    }

    /**
     * 获取文件 json 数据
     *
     * @param filepath
     * @param isrefresh true 重新计算,false 缓存读取
     *
     * @return
     *
     * @description 缓存->查询
     */
    private Object getTreeJson(String filepath, boolean isrefresh, FilenameFilter filter) {
        Object jsons = null;
        if (isrefresh) {
            jsons = JSON.toJSON(buildTree(filepath, filter));
//            CacheKit.put(AppConst.DEFAULT_CACHE_NAME, filepath, jsons);//TODO cache
        } else {
//            jsons = CacheKit.get(AppConst.DEFAULT_CACHE_NAME, filepath);//TODO cache
            //如果缓存过期 获取null值 重新计算;
            if (jsons == null || (jsons instanceof JSONArray && ((JSONArray) jsons).size() == 0)) {
                jsons = JSON.toJSON(buildTree(filepath, filter));
//                CacheKit.put(AppConst.DEFAULT_CACHE_NAME, filepath, jsons);//TODO cache
            }
        }
        return jsons;
    }

    /**
     * 构建 file 树
     *
     * @param absolutePath 绝对路径
     * @param filter
     *
     * @return
     */
    private List buildTree(String absolutePath, FilenameFilter filter) {
        List files = null;
        try {
            files = ergodic(new File(absolutePath), Lists.newArrayList(), filter, absolutePath);
        } catch (Exception e) {
            throw new RuntimeException("文件列表获取失败");
        }
        return new TreeBuilder().getChildTreeObjects(files, ROOTPATH);
    }

    /**
     * 递归子方法:
     * 列出所有目录子文件添加到list中
     *
     * @param file
     * @param resultfiles
     * @param filter      文件过滤器
     * @param parentPath  默认以filepath路径作为根,但是经过parseFilePath后,rootid会被替换成空串
     *
     * @return
     *
     * @description 列出文件->过滤->排序->遍历
     */
    private List<FileView> ergodic(File file, List<FileView> resultfiles, FilenameFilter filter, String parentPath) {
        List<File> files = Arrays.asList(file.listFiles(filter));
        if (files == null)
            return resultfiles;// 判断目录下是不是空的
        //对文件列表进行排序
        //排序规则：目录->文件->按字母
        Collections.sort(files, new Comparator<File>() {

            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory() && o2.isFile())
                    return -1;
                if (o1.isFile() && o2.isDirectory())
                    return 1;
                return o1.getName().compareTo(o2.getName());
            }
        });
        for (File f : files) {
            String currPath = parseFilePath(f.getAbsolutePath(), parentPath);
            String parentpath = parseFilePath(f.getParent(), parentPath);

            //如果是根路径,指定固定字符
            if ("".equals(parentpath)) {
                parentpath = ROOTPATH;
            }
            if (f.isDirectory()) {
                //如执行过滤后无,文件夹下无文件,跳过
//                if(f.listFiles(filter).length==0){
//                    continue;
//                }
                resultfiles.add(new FileView(currPath, parentpath, f.getName()));
                ergodic(f, resultfiles, filter, parentPath);// 调用自身,查找子目录
            } else {
                resultfiles.add(new FileView(currPath, parentpath, f.getName()));
            }
        }
        return resultfiles;
    }

    /**
     * 因数据传入页面由前端解析，安全考虑替换根路径,防止通过该模块访问web系统以外的路径造成提权
     *
     * @param path
     * @param rootPath
     *
     * @return
     */
    private String parseFilePath(String path, String rootPath) {
        return path.replace(rootPath, "");
    }

    /**
     * 文件树对象
     */
    class FileView implements TreeNode {

        private String fname;

        private String pfname;

        private String name;

        private List children;

        public FileView(String fname, String pfname, String name) {
            this.fname = fname;
            this.pfname = pfname;
            this.name = name;
        }

        @Override
        public Object getId() {
            return fname;
        }

        @Override
        public void setId(Object id) {
            this.fname = (String) id;
        }

        @Override
        public Object getParentId() {
            return pfname;
        }

        @Override
        public void setParentId(Object parentId) {
            this.pfname = (String) parentId;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public void setName(String name) {
            this.name = name;
        }

        @Override
        public List getChildren() {
            return children;
        }

        @Override
        public void setChildren(List children) {
            this.children = children;
        }

        @Override
        public Object currNode() {
            return null;
        }
    }
}
