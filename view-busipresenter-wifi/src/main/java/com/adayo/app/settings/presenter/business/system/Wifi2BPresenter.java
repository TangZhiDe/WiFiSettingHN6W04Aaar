package com.adayo.app.settings.presenter.business.system;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;

import com.adayo.app.settings.presenter.business.SettingsBaseBPresenter;
import com.adayo.app.settings.pvcontract.system.IWifi2BPContract;
import com.adayo.component.settingsWifi.presenter.function.WifiFPresenter;

import java.util.List;

/**
 * Created by Administrator on 2018/12/13 0013.
 * 接收UI端的请求，并将请求传送给WifiFpresenter中
 */

public class Wifi2BPresenter extends SettingsBaseBPresenter<IWifi2BPContract.IWIFI2View,WifiFPresenter>implements IWifi2BPContract.IWIFI2ViewBusiPresenter {
private static String TAG = "Wifi2BPresenter";
    public Wifi2BPresenter(Context context){
        super(context);
        //init FPresenter
        mFPresenter =new WifiFPresenter(context);

    }

    @Override
    protected void dispatchHandlerMessage(int what, Bundle bundle) {


    }

    @Override
    public void unbindService() {
        mFPresenter.unbindService();
    }

    @Override
    public void WifiOn() {
    //mFPresenter 中实现
        mFPresenter.wifiopen();
    }

    @Override
    public void WifiOff() {
        mFPresenter.wificlose();

    }

    @Override
    public boolean isWifiEnabled() {
        return mFPresenter.isWifiEnabled();
    }

    @Override
    public void StartScan() {
        mFPresenter.StartScan();
    }

    @Override
    public List<ScanResult> getScanResult() {
        return mFPresenter.getScanResult();
    }

    @Override
    public int getWifiAPState() {
      //  Log.e(TAG,"getWifiAPState = "+mFPresenter.getWifiAPState());
        return mFPresenter.getWifiAPState();
    }

    @Override
    public int getSecurityType() {
        return mFPresenter.getSecurityType();
    }

    @Override
    public int connected_history_count() {
        return mFPresenter.connected_history_count();
    }

    @Override
    public void WifiAPOn() {
        mFPresenter.WifiAPOn();
    }

    @Override
    public void WifiAPOff() {
        mFPresenter.WifiAPOff();
    }

    @Override
    public void ui_notify() {
     mFPresenter.ui_notify();
    }

    @Override
    public void init_historyData() {
    mFPresenter.init_historyData();
    }

    @Override
    public WifiConfiguration getWifiAPconfigure() {
        return mFPresenter.getWifiAPconfigure();
    }
}
