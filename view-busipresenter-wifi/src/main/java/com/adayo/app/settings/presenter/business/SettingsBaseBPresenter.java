package com.adayo.app.settings.presenter.business;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.adayo.app.base.BaseBusinessPresenter;
import com.adayo.app.base.BaseFunctionPresenter;
import com.adayo.app.settings.constant.Constant;
import com.adayo.component.settingsWifi.bfpcontract.IBusinessFuncpresenter;
import com.adayo.component.settingsWifi.constant.EnumConstant;
import com.adayo.component.settingsWifi.model.bean.DialogItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuefengduan
 * @version 1.0
 * @date 2018/11/8.
 * @desc. Setting中的BP
 */
public abstract class SettingsBaseBPresenter<V, FP extends BaseFunctionPresenter<IBusinessFuncpresenter>>
        extends BaseBusinessPresenter<V> implements IBusinessFuncpresenter {

    public static final String TAG = "SettingsBaseBPresenter";

    protected Handler mHandler;
    protected Context mContext;
    protected V mView;
    protected FP mFPresenter;

    public SettingsBaseBPresenter(Context context) {
        this.mContext = context;
        mHandler = new Handler(mContext.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                try {
                    Log.d("ssdd", "handleMessage: "+msg.what);
                    Bundle bundle = (Bundle) msg.obj;
                    dispatchHandlerMessage(msg.what, bundle);
//                    Intent intent = (Intent) msg.obj;
//                    dispatchHandlerMessage(msg.what, intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    public void registerView(V v) {
        mView = v;
        mFPresenter.registerBusinessPresenter(this);
    }

    @Override
    public void init() {
        mFPresenter.init();
    }

    @Override
    public void unregisterView() {
        if (mHandler != null) {
            mHandler = null;
        }
        mView = null;
        mFPresenter.unregisterBusinessPresenter();
    }

    @Override
    public void handleEvent(Message msg) {
        mHandler.obtainMessage(msg.what, msg.obj).sendToTarget();
    }

    protected abstract void dispatchHandlerMessage(int what, Bundle bundle);

    public List<DialogItem> getCustomPopWindowsData(int type) {
        List<DialogItem> list = new ArrayList<>();
        //导航混音
        if (type == Constant.CUSTOM_POPWINDOWS_TYPE_NAIGATION_BROADCAST) {
            for (EnumConstant.NAIGATION_BROADCAST info : EnumConstant.NAIGATION_BROADCAST.values()) {
                DialogItem item = new DialogItem(info.getName(), info.getValue());
                list.add(item);
            }
        }
        //速度补偿音
        else if (type == Constant.CUSTOM_POPWINDOWS_TYPE_SPEED_COM_VOLUME) {
            for (EnumConstant.SPEED_COM_VOLUME info : EnumConstant.SPEED_COM_VOLUME.values()) {
                DialogItem item = new DialogItem(info.getName(), info.getValue());
                list.add(item);
            }
        }
        //亮度调节
        else if (type == Constant.CUSTOM_POPWINDOWS_TYPE_BRIGHTNESS_CONTROL) {
            for (EnumConstant.BRIGHTNESS_CONTROL info : EnumConstant.BRIGHTNESS_CONTROL.values()) {
                DialogItem item = new DialogItem(info.getName(), info.getValue());
                list.add(item);
            }
        } //系统语言
        else if (type == Constant.CUSTOM_POPWINDOWS_TYPE_SYSTEM_LANGUAGE) {
            for (EnumConstant.SYSTEM_LANGUAGE info : EnumConstant.SYSTEM_LANGUAGE.values()) {
                DialogItem item = new DialogItem(info.getName(), info.getValue());
                list.add(item);
            }
        } //待机显示
        else if (type == Constant.CUSTOM_POPWINDOWS_TYPE_STANDBY_DISPLAY) {
            for (EnumConstant.STANDBY_DISPLAY info : EnumConstant.STANDBY_DISPLAY.values()) {
                DialogItem item = new DialogItem(info.getName(), info.getValue());
                list.add(item);
            }
        } //时间制式
        else if (type == Constant.CUSTOM_POPWINDOWS_TYPE_TIME_DISPLAY) {
            for (EnumConstant.TIME_DISPLAY info : EnumConstant.TIME_DISPLAY.values()) {
                DialogItem item = new DialogItem(info.getName(), info.getValue());
                list.add(item);
            }
        } //时区
        else if (type == Constant.CUSTOM_POPWINDOWS_TYPE_TIME_ZONE) {
            for (EnumConstant.TIME_ZONE info : EnumConstant.TIME_ZONE.values()) {
                DialogItem item = new DialogItem(info.getName(), info.getValue());
                list.add(item);
            }
        }//日期制式
        else if (type == Constant.CUSTOM_POPWINDOWS_TYPE_DATE_DISPLAY) {
            for (EnumConstant.DATE_DISPLAY info : EnumConstant.DATE_DISPLAY.values()) {
                DialogItem item = new DialogItem(info.getName(), info.getValue());
                list.add(item);
            }
        }
        return list;
    }


}