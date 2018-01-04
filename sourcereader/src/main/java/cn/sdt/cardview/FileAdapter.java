package cn.sdt.cardview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by SDT13411 on 2018/1/4.
 */

public class FileAdapter extends BaseAdapter {

    private static final String TAG = "FileAdapter";
    LinkedList<FileBean> fileBeans;
    Context context;
    LayoutInflater inflater;

    public FileAdapter(LinkedList<FileBean> fileBeans, Context context) {
        this.fileBeans = fileBeans;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return fileBeans != null ? fileBeans.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return fileBeans != null ? fileBeans.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.file_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imgExpand = (ImageView) convertView.findViewById(R.id.expand_icon);
            viewHolder.imgFileType = (ImageView) convertView.findViewById(R.id.file_type);
            viewHolder.fileName = (TextView) convertView.findViewById(R.id.file_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        FileBean fileBean = fileBeans.get(position);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewHolder.imgExpand.getLayoutParams();
        Log.d(TAG, "leftMargin::" + layoutParams.leftMargin);
        layoutParams.setMargins(30 + 30 * fileBean.level, layoutParams.topMargin,
                layoutParams.rightMargin, layoutParams.bottomMargin);
        viewHolder.imgExpand.setLayoutParams(layoutParams);
        Log.d(TAG, "leftMargin::" + layoutParams.leftMargin);
        if (fileBean.expand) {
            viewHolder.imgExpand.setBackgroundResource(R.drawable.expand_selector);
        } else {
            viewHolder.imgExpand.setBackgroundResource(R.drawable.up_selector);
        }

        if (fileBean.isDirectry) {
            viewHolder.imgFileType.setBackgroundResource(R.drawable.dir_bg_selector);
        } else {
            viewHolder.imgFileType.setBackgroundResource(getResIdByType(fileBean.fileType));
        }
        viewHolder.fileName.setText(fileBean.fileName);
        return convertView;
    }

    private int getResIdByType(String type) {
        if (FileBean.FILE_IMAGE.equals(type)) {
            return R.drawable.ic_image_black_48dp;
        } else if (FileBean.FILE_VIDOE.equals(type)) {
            return R.drawable.ic_videocam_black_48dp;
        } else if (FileBean.FILE_AUDIO.equals(type)) {
            return R.drawable.ic_insert_drive_file_black_48dp;
        } else if (FileBean.FILE_PACKAGE.equals(type)) {
            return R.drawable.ic_insert_drive_file_black_48dp;
        } else if (FileBean.FILE_WEBTEXT.equals(type)) {
            return R.drawable.ic_insert_drive_file_black_48dp;
        } else {
            return R.drawable.ic_sd_storage_black_48dp;
        }
    }

    static class ViewHolder {
        ImageView imgExpand;
        ImageView imgFileType;
        TextView fileName;
    }
}
