package com.adayo.app.settings.pvcontract.system;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;

import java.util.List;

/**
 * Created by Administrator on 2018/12/13 0013.
 */

public interface IWifi2BPContract {
    interface IWIFI2View {

    }

    interface IWIFI2ViewBusiPresenter {
        void  unbindService();

        //开启wifi
        void WifiOn();

        //关闭wifi
        void WifiOff();

        //wifi状态
        boolean isWifiEnabled();

        void StartScan();

        //获取搜索结果
        List<ScanResult> getScanResult();

        //查询热点状态
        int getWifiAPState();

        //获取加密类型:有密码，无密码
        int getSecurityType();

        //获取连接历史记录数量
        int  connected_history_count();

        //开启热点
        void WifiAPOn();

        //关闭热点
       void WifiAPOff();


       /*过滤掉搜索结果中重名的SSID*/
       void ui_notify();
        /*初始化历史记录*/
        void init_historyData();


         WifiConfiguration getWifiAPconfigure();

    }

}