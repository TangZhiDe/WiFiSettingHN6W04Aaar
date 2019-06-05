package com.adayo.app.settings.ui.fragment.system;

import android.content.Context;
import android.view.View;

/**
 * Created by Administrator on 2019/1/5 0005.
 */

public class otherDialog extends BaseDialog {

    public otherDialog(Context context) {
        super(context);
        initDilaogView();
    }

    private void initDilaogView() {
        //连接错误警告框
        connect_error_dialog();
        //当前有一个已连接的热点，点击提示是否断开当前，连接新的热点
        checkNewAP_dialog();

        //断开当前连接
        disconnect_dialog();

        //手动添加网络
        showdialog_addNet();
    }

    private void showdialog_addNet() {

    }

    private void disconnect_dialog() {
        //预加载视图
    }

    private void checkNewAP_dialog() {

    }

    private void connect_error_dialog() {

    }




    public View getView(int layout){
       return  View.inflate(mContext,layout,null);
    }
}
