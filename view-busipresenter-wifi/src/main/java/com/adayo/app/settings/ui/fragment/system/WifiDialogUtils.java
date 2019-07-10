package com.adayo.app.settings.ui.fragment.system;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.util.Log;
import android.view.View;

import com.adayo.app.base.BaseActivity;
import com.adayo.app.settings.R;
import com.adayo.app.settings.ui.view.dialog.Wifi_ConfirmDialog;
import com.adayo.app.settings.utils.WiFiUtil;

/**
 * Created by Administrator on 2018/12/20 0020.
 * 搜索列表中的点击弹框
 */

public class WifiDialogUtils extends otherDialog {
    public static WifiDialogUtils instance = null;
    private static BaseActivity mContext;
    private final WiFiUtil mWiFiUtil;
    private final NetFragment netfragment;
    private static String TAG = "WifiDialogUtils";

    WifiDialogUtils(Context context) {
        super(context);
        this.mContext = (BaseActivity) context;
        mWiFiUtil = WiFiUtil.getInstance(mContext);
        netfragment = NetFragment.getNetFragmentInstance();//获取netfragment的实例
    }

    public static WifiDialogUtils getDialoginstance(Context context) {
        synchronized (WifiDialogUtils.class) {
            instance = new WifiDialogUtils(context);

        }

        return instance;
    }


    //若点击热点已经连接上,可以选择手动断开热点
    public void disconnect_wifi(String title, String message, String ssid, final int networkId, boolean show) {
        final Wifi_ConfirmDialog wifi_confirmDialog = new Wifi_ConfirmDialog(mContext,3,"");
        wifi_confirmDialog.showDialog(NetFragment.currentwifiNightMode);
        wifi_confirmDialog.setCancelable(false);
        wifi_confirmDialog.alert_wifi_tip.setVisibility(View.GONE);
        if(wifi_confirmDialog.alert_wifi_name1 != null){
            Log.d(TAG, "disconnect_wifi: set wifi名称" );
            wifi_confirmDialog.alert_wifi_name1.setText(title+ssid+"?");
        }
        wifi_confirmDialog.setClicklistener(new Wifi_ConfirmDialog.ClickListenerInterface() {
            @Override
            public void doConfirm() {
                mWiFiUtil.disconnectWiFiNetWork(networkId);
                wifi_confirmDialog.dismissDialog();
            }

            @Override
            public void doCancel() {
                wifi_confirmDialog.dismissDialog();
            }

        });









//        View view = View.inflate(mContext, R.layout.disconnect_wifi, null);
//
//        TextView tv_connected_ssid = (TextView) view.findViewById(R.id.tv_disconnect);
//        Button bt_disconnect = (Button) view.findViewById(R.id.bt_disconnect);
//        Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
//
//        tv_connected_ssid.setText(title + ssid + "?");
//
//        setContentView(view);
//        setCancelable(false);
//
//        bt_disconnect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //  is_rc_wifi = false;//手动断开后就不会再自己强行连接上，需要点击
////                wifiModelInstance.disconnect();
//                mWiFiUtil.disconnectWiFiNetWork(networkId);
//                dismiss();
//
//            }
//        });
//        bt_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                dismiss();
//            }
//        });
//
//        showdialog(-1, -1, Gravity.CENTER, false, 550, 250);
    }



    public void doCon(ScanResult item,boolean isError){
        if (item.capabilities.contains("WPA2") || item.capabilities.contains("WPA-PSK")) {
            Log.d("TAG", "onClick: WPA2   WPA-PSK");
//            showDialog(item, WiFiUtil.WIFI_LOCK_TYPE_WPA2,isError);
            showDialog(item, WiFiUtil.Data.WIFI_CIPHER_WPA2,isError);
        } else if (item.capabilities.contains("WPA")) {
            Log.d("TAG", "onClick: WPA");
            showDialog(item, WiFiUtil.Data.WIFI_CIPHER_WPA,isError);
        } else if (item.capabilities.contains("WEP")) {
            Log.d("TAG", "onClick: WEP");
                    /* WIFICIPHER_WEP 加密 */
            showDialog(item, WiFiUtil.Data.WIFI_CIPHER_WEP,isError);
        } else {
            Log.d("TAG", "onClick: 开放无加密");
                    /* WIFICIPHER_OPEN NOPASSWORD 开放无加密 */
            mWiFiUtil.addWiFiNetwork(item.SSID, "", WiFiUtil.Data.WIFI_CIPHER_NOPASS);
        }
    }

    public void showDialog(final ScanResult scanresult, final WiFiUtil.Data type, boolean isError){
        final Wifi_ConfirmDialog wifi_confirmDialog = new Wifi_ConfirmDialog(mContext,0,"");
        wifi_confirmDialog.showDialog(NetFragment.currentwifiNightMode);
        wifi_confirmDialog.setCancelable(false);
        if(wifi_confirmDialog.alert_wifi_name != null){
            Log.d(TAG, "showDialog: set wifi名称" );
            wifi_confirmDialog.alert_wifi_name.setText(scanresult.SSID);
        }
        if(wifi_confirmDialog.alert_wifi_psw != null){
            wifi_confirmDialog.alert_wifi_psw.setText("");
        }
        if(isError){
            Log.d(TAG, "showDialog: isError 显示错误提示" );
            wifi_confirmDialog.alert_wifi_tip.setVisibility(View.VISIBLE);
        }else {
            wifi_confirmDialog.alert_wifi_tip.setVisibility(View.GONE);
        }
        wifi_confirmDialog.setClicklistener(new Wifi_ConfirmDialog.ClickListenerInterface() {
            @Override
            public void doConfirm() {
                String password = wifi_confirmDialog.alert_wifi_psw.getText().toString();//从输入框获取到密码
                if(password != null && password.length()>=8){
                    wifi_confirmDialog.alert_wifi_tip.setVisibility(View.GONE);
                    Log.d(TAG, "doConfirm: 开始连接wifi --"+scanresult.SSID+"---密码==="+ password);
                    mWiFiUtil.addWiFiNetwork(scanresult.SSID, password, type);
                    wifi_confirmDialog.dismissDialog();
                }
            }

            @Override
            public void doCancel() {
                wifi_confirmDialog.dismissDialog();
            }

        });
    }



    //修改热点账号弹框
    public void DialogSetSSID() {

        final Wifi_ConfirmDialog wifi_confirmDialog = new Wifi_ConfirmDialog(mContext,1,mWiFiUtil.getWifiAPconfigure().SSID);
        wifi_confirmDialog.showDialog(NetFragment.currentwifiNightMode);
        wifi_confirmDialog.setCancelable(false);
        wifi_confirmDialog.alert_wifi_tip.setVisibility(View.GONE);
        wifi_confirmDialog.setClicklistener(new Wifi_ConfirmDialog.ClickListenerInterface() {
            @Override
            public void doConfirm() {
                String ssid = wifi_confirmDialog.alert_wifi_psw.getText().toString();
                if(ssid !=null && ssid != ""){
                    //配置账号
                    Log.d(TAG, "onClick: 修改账号 ssid==" +ssid);
                    netfragment.isSetting = true;
                    mWiFiUtil.wifiAP_close();
                    mWiFiUtil.onlySetAPSSID(ssid,true);
                    wifi_confirmDialog.dismissDialog();
                    //TODO 先关闭热点，再打开热点

                    netfragment.handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "run: 开启热点" );
                            mWiFiUtil.wifiAP_open();
                        }
                    }, 2000);
                }
            }

            @Override
            public void doCancel() {
                wifi_confirmDialog.dismissDialog();
            }

        });


    }


    //修改密码
    public void DialogSetPassWord(final boolean isUpdateType) {
        final Wifi_ConfirmDialog wifi_confirmDialog = new Wifi_ConfirmDialog(mContext,2,mWiFiUtil.getWifiAPconfigure().preSharedKey);
        wifi_confirmDialog.showDialog(NetFragment.currentwifiNightMode);
        wifi_confirmDialog.setCancelable(false);
        wifi_confirmDialog.alert_wifi_tip.setVisibility(View.GONE);
        wifi_confirmDialog.setClicklistener(new Wifi_ConfirmDialog.ClickListenerInterface() {
            @Override
            public void doConfirm() {
                String pass =  wifi_confirmDialog.alert_wifi_psw.getText().toString();
                if (pass.length() < 8) {
                    wifi_confirmDialog.alert_wifi_psw.setText("");
                    wifi_confirmDialog.alert_wifi_psw.setHint(mContext.getResources().getString(R.string.string22));
                    Log.d(TAG, "onClick: 密码小于8位" );
                } else {
                    Log.d(TAG, "onClick: 修改密码 pass==" +pass);
                    netfragment.isSetting = true;
                    mWiFiUtil.wifiAP_close();
                    mWiFiUtil.onlySetAPPassword(pass,true);
                    wifi_confirmDialog.dismissDialog();
                    netfragment.handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "run: 开启热点" );
                            mWiFiUtil.wifiAP_open();
                        }
                    }, 2000);

                }
            }

            @Override
            public void doCancel() {
                wifi_confirmDialog.dismissDialog();
                if(isUpdateType){
                    netfragment.handler.sendEmptyMessage(0x02);
                }
            }

        });

    }


}
