package cn.sdt.cardview;

import java.util.LinkedList;

/**
 * Created by SDT13411 on 2018/1/4.
 */

public class FileBean {

    public final static String FILE_VIDOE = "video";
    public final static String FILE_IMAGE = "image";
    public final static String FILE_PACKAGE = "package";
    public final static String FILE_AUDIO = "audio";
    public final static String FILE_WEBTEXT = "web";
    public final static String FILE_DEFAULT = "default";

    String fullPath;
    String fileName;
    boolean isDirectry;
    String fileType;
    FileBean parent;
    boolean expand;
    int level;
    LinkedList<FileBean> childrenList;
}
