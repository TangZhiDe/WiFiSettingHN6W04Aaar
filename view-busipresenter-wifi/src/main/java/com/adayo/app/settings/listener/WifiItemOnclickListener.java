package com.adayo.app.settings.listener;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.adayo.app.settings.R;
import com.adayo.app.settings.ui.fragment.system.NetFragment;
import com.adayo.app.settings.ui.fragment.system.WifiDialogUtils;
import com.adayo.app.settings.ui.fragment.system.WifiReceiver;
import com.adayo.app.settings.ui.view.dialog.SwitchButton;
import com.adayo.app.settings.utils.WiFiUtil;


/**
 * Created by Administrator on 2018/12/18 0018.
 */
//wifi列表点击事件
public class WifiItemOnclickListener implements View.OnClickListener, AdapterView.OnItemClickListener, SwitchButton.HydropowerListener, SwitchButton.SoftFloorListener {

    private static String TAG = "WifiItemOnclickListener";
    private Context mContex;
    private WifiDialogUtils dialoginstance;
    private NetFragment netfragment;
    private WifiReceiver receiver;
    private ScanResult scanResult_item;
    private final WiFiUtil mWiFiUtil;

    public WifiItemOnclickListener(WifiReceiver receiver, Context context) {
        this.receiver = receiver;
        this.mContex = context;
        netfragment = NetFragment.getNetFragmentInstance();//获取netfragment的实例
        dialoginstance = WifiDialogUtils.getDialoginstance(mContex);
        mWiFiUtil = WiFiUtil.getInstance(mContex);

    }

    private static long lastClickTime = 0;

    public boolean isFastDoubleClick(long diff) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (lastClickTime > 0 && timeD < diff) {
            Log.v("isFastDoubleClick", "短时间内按钮多次触发");
            return true;
        }
        lastClickTime = time;
        return false;
    }

    @Override
    public void onClick(View v) {
        if (isFastDoubleClick(1000)) {
            Log.d(TAG, "onClick:重复点击 ");
            return;
        }


        if (v.getId() == R.id.img_wifi_power) {/*wifi开关*/

            if (!mWiFiUtil.isWifiEnabled()) {
                Log.d(TAG, "run: 开启wifi");
                mWiFiUtil.wifi_open();//开启wifi
            } else if (mWiFiUtil.isWifiEnabled()) {
                mWiFiUtil.wifi_close();//关闭wifi
            }
        } else if (v.getId() == R.id.img_ap_power) {/*热点开关*/
            if (mWiFiUtil.getWifiApState() == WiFiUtil.WIFI_AP_STATE_DISABLED) {
                mWiFiUtil.wifiAP_open();//开启热点
                Log.d(TAG, "手动 热 点开启");

            } else if (mWiFiUtil.getWifiApState() == WiFiUtil.WIFI_AP_STATE_ENABLED) {
                mWiFiUtil.wifiAP_close();///关闭热点
                Log.d(TAG, "手动  热点关闭");
            }

        } else if (v.getId() == R.id.img_buttton_Setssid) {
            //TODO show Dialog to set ap ssid
            dialoginstance.DialogSetSSID();
        } else if (v.getId() == R.id.img_buttton_SetPass) {
            if (mWiFiUtil.getSecurityType() == 1) {
                Log.d(TAG, "不允许设置密码");
            } else {
                //TODO show dialog to set ap password
                dialoginstance.DialogSetPassWord();
            }

        }else if (v.getId() == R.id.wifi_refresh) {
            Log.d(TAG, "onClick: 开始搜索" );
            mWiFiUtil.startScan();
            netfragment.startRotate();
        }

    }


    //加密类型选择
    private void SelectType(boolean isChecked) {

        if (!isChecked) {/*选择无密码*/
            if (mWiFiUtil.getWifiApState() == WiFiUtil.WIFI_AP_STATE_ENABLED) {
                mWiFiUtil.wifiAP_close();
            }
            //设置无密码   只配置账号，密码为空
            mWiFiUtil.onlySetAPSSID(mWiFiUtil.getWifiAPconfigure().SSID, false);
            netfragment.tv_ap_pass.setText(mContex.getResources().getString(R.string.string20));
            Log.d(TAG, "设置免密码,热点即将重新开启...........");
            //开启热点
            netfragment.handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mWiFiUtil.wifiAP_open();
                }
            }, 3000);
        } else if (isChecked) {/*选择加密*/
            Log.d(TAG, "手动设置密码，热点将在设置成功后重新开启");
            dialoginstance.DialogSetPassWord();
        }


    }


    //lisview 中的搜索网络列表
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (NetFragment.results.size() < 0) {
            Log.d(TAG, "scan result null");
            return;
        }
        Log.d(TAG, "onItemClick: mContex == "+mContex );
        scanResult_item = NetFragment.results.get(position);
        mWiFiUtil.scanListResult = scanResult_item;
        WiFiUtil.Connection_status = 0;
        Log.d(TAG, "onItemClick: current_connected_ssid===" + mWiFiUtil.getSSID());
        if (mWiFiUtil.getSSID().equals("\"" + scanResult_item.SSID + "\"")) {
            //当前点击网络已连接
            Log.d(TAG, "onItemClick: 是否断开弹窗");
            dialoginstance.disconnect_wifi(mContex.getResources().getString(R.string.string9), null, scanResult_item.SSID, mWiFiUtil.getNetworkId(), true);
        } else {
            Log.d(TAG, "onItemClick: 去连接");
            int networkId = mWiFiUtil.getNetworkId(scanResult_item.SSID);
            if (networkId != -1) {
                //已保存的wifi
                Log.d(TAG, "onItemClick: 连接已保存的wifi networkId==" + networkId);
                mWiFiUtil.connectConfigurationByNetworkId(networkId);
            } else {
                dialoginstance.doCon(scanResult_item, false);
            }
        }
    }


    @Override
    public void softFloor() {
        //加密
        Log.d(TAG, "softFloor: 加密");
        SelectType(true);
    }

    @Override
    public void hydropower() {
        //免密
        Log.d(TAG, "hydropower: 免密");
        SelectType(false);
    }
}
