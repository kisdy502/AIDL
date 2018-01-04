package cn.sdt.cardview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.LinkedList;


public class MainActivity extends Activity {
    final static String TAG = "MainActivity";

    LinkedList<FileBean> fileBeans;
    ListView mListView;
    FileAdapter mAdapter;
    TextView currentSelecteFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.file_list);
        mListView.setOnItemClickListener(listener);
        currentSelecteFile = (TextView) findViewById(R.id.title);
        init();
        mAdapter = new FileAdapter(fileBeans, getApplicationContext());
        mListView.setAdapter(mAdapter);
    }


    private void init() {
        fileBeans = new LinkedList<>();
        FileBean rootBean = new FileBean();
        rootBean.isDirectry = true;
        rootBean.fileName = "/";
        rootBean.fullPath = "/";
        rootBean.fileType = "";
        rootBean.parent = null;
        rootBean.level = FileUtil.getFileLevel(rootBean);
        File file = new File("/");
        fill(rootBean, file.listFiles());
        currentSelecteFile.setText("当前位置:/");
    }

    private void fill(FileBean rootBean, File[] files) {
        FileBean fileBean;
        if (files != null) {
            for (File f : files) {
                fileBean = new FileBean();
                fileBean.parent = rootBean;
                fileBean.isDirectry = f.isDirectory();
                fileBean.fullPath = f.getAbsolutePath();
                fileBean.fileName = f.getName();
                fileBean.fileType = getFileType(f.getName());
                fileBean.level = FileUtil.getFileLevel(fileBean);
                fileBeans.add(fileBean);
            }
        }
    }

    //有bug需要递归计算
    private void append(FileBean bean, File[] files) {
        LinkedList<FileBean> childrenList = null;
        FileBean fileBean;
        if (files != null) {
            childrenList = new LinkedList<>();
            int index = fileBeans.indexOf(bean);
            for (File f : files) {
                fileBean = new FileBean();
                fileBean.parent = bean;
                fileBean.isDirectry = f.isDirectory();
                fileBean.fullPath = f.getAbsolutePath();
                fileBean.fileName = f.getName();
                fileBean.fileType = getFileType(f.getName());
                fileBean.level = FileUtil.getFileLevel(fileBean);
                childrenList.add(fileBean);
            }
            bean.childrenList = childrenList;
            fileBeans.addAll(++index, bean.childrenList);
        }


    }

    //有bug，需要修改方法的实现
    private void remove(FileBean bean, File[] files) {
        int level = FileUtil.getFileLevel(bean);
        int start = fileBeans.indexOf(bean);
        int end = getEndNodeByLevel(start, level);
        Log.d(TAG, "start:" + start);
        Log.d(TAG, "end:" + end);
        if (start + 1 < end) {
            for (int i = start + 1; i < end; i++) {
                fileBeans.remove(start + 1);
            }
        } else if (start + 1 == end) {
            fileBeans.remove(start + 1);
        }
    }

    int getEndNodeByLevel(int start, int level) {
        int i, size = fileBeans.size();
        int end = start + 1;
        FileBean temp;
        int templevel;
        for (i = start + 1; i < size; i++) {
            temp = fileBeans.get(i);
            templevel = FileUtil.getFileLevel(temp);
            if (templevel <= level) {
                end = i;
                return end;
            }

        }
        //一直到最后都没找到
        return size;
    }

    public String getFileType(String fileName) {
        String type = null;
        if (checkExtension(fileName, getResources().getStringArray(R.array.extensionImage))) {
            type = FileBean.FILE_IMAGE;
        } else if (checkExtension(fileName, getResources().getStringArray(R.array.extensionVideo))) {
            type = FileBean.FILE_VIDOE;
        } else if (checkExtension(fileName, getResources().getStringArray(R.array.extensionAudio))) {
            type = FileBean.FILE_AUDIO;
        } else if (checkExtension(fileName, getResources().getStringArray(R.array.extensionWebText))) {
            type = FileBean.FILE_WEBTEXT;
        } else if (checkExtension(fileName, getResources().getStringArray(R.array.extensionPackage))) {
            type = FileBean.FILE_PACKAGE;
        } else {
            type = FileBean.FILE_DEFAULT;
        }
        return type;
    }


    //通过文件名判断是什么类型的文件
    private boolean checkExtension(String fileName, String[] fileExtensions) {
        for (String aEnd : fileExtensions) {
            if (fileName.endsWith(aEnd))
                return true;
        }
        return false;
    }


    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            FileBean bean = fileBeans.get(position);
            if (bean.isDirectry && !bean.expand) {
                File f = new File(bean.fullPath);
                if (f.canRead()) {
                    bean.expand = true;
                    File[] files = f.listFiles();
                    append(bean, files);
                    currentSelecteFile.setText("当前位置:" + f.getPath());
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "没有权限", Toast.LENGTH_SHORT).show();
                }
            } else if (bean.isDirectry && bean.expand) {
                File f = new File(bean.fullPath);
                if (f.canRead()) {
                    bean.expand = false;
                    File[] files = f.listFiles();
                    remove(bean, files);
                    currentSelecteFile.setText("当前位置:" + f.getPath());
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "没有权限", Toast.LENGTH_SHORT).show();
                }
            } else {

            }
        }
    };
}
