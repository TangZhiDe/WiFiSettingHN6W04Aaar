package com.adayo.app.settings.ui.fragment.system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.util.Log;
import android.view.View;

import com.adayo.app.settings.R;
import com.adayo.app.settings.utils.WiFiUtil;



/**
 * Created by Administrator on 2018/12/19 0019.
 */

public class WifiApReceiver extends BroadcastReceiver {
    private  String TAG = "WifiApReceiver";
    private  final int WIFI_AP_STATE_DISABLING = 10;
    private  final int WIFI_AP_STATE_DISABLED = 11;
    private  final int WIFI_AP_STATE_ENABLING = 12;
    private  final int WIFI_AP_STATE_ENABLED = 13;
    private  final String WIFI_AP_STATE_CHANGED_ACTION = "android.net.wifi.WIFI_AP_STATE_CHANGED";
    private  final String EXTRA_WIFI_AP_STATE = "wifi_state";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (WIFI_AP_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            int cstate = intent.getIntExtra(EXTRA_WIFI_AP_STATE, -1);
            if (cstate == WIFI_AP_STATE_ENABLED) {
                Log.d(TAG, "onReceive: 热点开启" );
                WiFiUtil.getInstance(context).wifi_close();
                initAPstatus(context);
                NetFragment.lin_wifiap_set.setVisibility(View.VISIBLE);
                NetFragment.ap_power.setImageResource(R.drawable.wifi_setting_btn_open);
                WifiConfiguration wifiAPconfigure = WiFiUtil.getInstance(context).getWifiAPconfigure();
                if (WiFiUtil.getInstance(context).getSecurityType()  == 1) {/*无密码类型*/
                    NetFragment.tv_ap_pass.setText(context.getResources().getString(R.string.string20));
                }else {
                    NetFragment.tv_ap_pass.setText(context.getResources().getString(R.string.string17)+": " + wifiAPconfigure.preSharedKey);
                }

                NetFragment.tv_ap_ssid.setText(context.getResources().getString(R.string.string15)+":" + wifiAPconfigure.SSID);
                Log.d(TAG, "onClick: preSharedKey=="+wifiAPconfigure.preSharedKey +"===name=="+wifiAPconfigure.SSID);
               // Log.d(TAG,"广播，热点已开启");
            }else if (cstate == WIFI_AP_STATE_ENABLING){
              //  Log.d(TAG,"广播，热点正在开启");

            }else if (cstate == WIFI_AP_STATE_DISABLED){
               // Log.d(TAG,"广播，热点已关闭");
                NetFragment.ap_power.setImageResource(R.drawable.wifi_setting_btn_close);
                NetFragment.lin_wifiap_set.setVisibility(View.GONE);
            }else if (cstate == WIFI_AP_STATE_DISABLING){
             // Log.d(TAG,"热点正在关闭");

            }

        }
    }

    //初始化热点UI状态
    public  void initAPstatus(Context context){
        //初始化加密类型
        //mWifi2bpresenter.getSecurityType()
        WiFiUtil instance = WiFiUtil.getInstance(context);
        if (instance.getSecurityType()  == 1) {/*无密码类型*/
            NetFragment.tv_ap_ssid.setText(context.getResources().getString(R.string.string15)+": " + instance.getWifiAPconfigure().SSID);
            NetFragment.tv_ap_pass.setText(context.getResources().getString(R.string.string20));
            NetFragment.security_type.setIsRight(true);
            NetFragment.security_type.flush(context);
            Log.d(TAG,"Checked(true)");
        } else  {/*加密类型*/
            NetFragment.security_type.setIsRight(false);
            NetFragment.security_type.flush(context);
            NetFragment.tv_ap_ssid.setText(context.getResources().getString(R.string.string15)+": " + instance.getWifiAPconfigure().SSID);
            NetFragment.tv_ap_pass.setText(context.getResources().getString(R.string.string17)+": " + instance.getWifiAPconfigure().preSharedKey);
            Log.d(TAG,"Checked(false)");
        }
        Log.d(TAG,"initAPstatus");
    }
}
