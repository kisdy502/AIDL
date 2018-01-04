package cn.sdt.cardview;

/**
 * Created by SDT13411 on 2018/1/4.
 */

public class FileUtil {

    //目录层级
    public static int getFileLevel(FileBean fileBean) {
        int level = 0;
        FileBean bean = fileBean;
        while (bean.parent != null) {
            level++;
            bean = bean.parent;
        }
        return level;
    }
}
