package com.adayo.app.settings.ui.fragment.system;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.adayo.app.base.BaseActivity;

/**
 * Created by Administrator on 2019/1/5 0005.
 */

public class BaseDialog extends Dialog {
    protected BaseActivity mContext;
    private View.OnClickListener mlistener;

    public void setDialogOnClickListenner(View.OnClickListener mlistener) {
        this.mlistener = mlistener;
    }

    public BaseDialog(Context context) {
        super(context);
        this.mContext = (BaseActivity) context;

        setCanceledOnTouchOutside(true);
//        setOnDismissListener(new OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                dialog.dismiss();
//            }
//        });
    }

    public void showLeft() {

    }

   public void showdialog(int x,int y,int gravity,boolean isFilllwith,int width,int height){
       // 1 获取到dialog的窗口
       Window window = this.getWindow();
       WindowManager.LayoutParams attributes = window.getAttributes();

       //2 设置方向
       window.setGravity(gravity);

     //  if(x != -1) {
           attributes.x = x;
     //  }
     //  if(y != -1) {
           attributes.y = y;
     //  }
       
       if(isFilllwith) {
           attributes.width = ViewGroup.LayoutParams.MATCH_PARENT;
       }
        //3设置弹框宽高
       attributes.width =width;
       attributes.height = height;
        //串口设置参数
       window.setAttributes(attributes);
       //4背景暂定

       //5显示dialog
       this.show();
   }

}
