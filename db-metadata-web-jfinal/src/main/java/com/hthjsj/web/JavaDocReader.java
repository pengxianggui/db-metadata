package com.hthjsj.web;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/11 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.RootDoc;

public class JavaDocReader {

    private static RootDoc root;

    // 一个简单Doclet,收到 RootDoc对象保存起来供后续使用
    // 参见参考资料6
    public static class Doclet {

        public Doclet() {
        }

        public static boolean start(RootDoc root) {
            JavaDocReader.root = root;
            return true;
        }
    }

    // 显示DocRoot中的基本信息
    public static void show() {
        ClassDoc[] classes = root.classes();
        for (int i = 0; i < classes.length; ++i) {
            System.out.println(classes[i]);
            System.out.println(classes[i].commentText());
            for (MethodDoc method : classes[i].methods()) {
                System.out.printf("\t%s\n", method.commentText());
            }
        }
    }

    public static RootDoc getRoot() {
        return root;
    }

    public JavaDocReader() {

    }

    public static void main(final String... args) throws Exception {
        // 调用com.sun.tools.javadoc.Main执行javadoc,参见 参考资料3
        // javadoc的调用参数，参见 参考资料1
        // -doclet 指定自己的docLet类名
        // -classpath 参数指定 源码文件及依赖库的class位置，不提供也可以执行，但无法获取到完整的注释信息(比如annotation)
        // -encoding 指定源码文件的编码格式
        com.sun.tools.javadoc.Main.execute(new String[] { "-doclet", Doclet.class.getName(),
                // 因为自定义的Doclet类并不在外部jar中，就在当前类中，所以这里不需要指定-docletpath 参数，
                //				"-docletpath",
                //				Doclet.class.getResource("/").getPath(),
                "-encoding", "utf-8", "-classpath",
                "D:/j/facelog/facelog-main/target/classes;D:/j/facelog/db/target/classes;D:/j/facelog/db/sql2java/lib/swift-annotations-0.14.2.jar",
                // 获取单个代码文件FaceLogDefinition.java的javadoc
                "J:/facelog/facelog-main/src/main/java/net/gdface/facelog/FaceLogDefinition.java" });
        show();
    }
}