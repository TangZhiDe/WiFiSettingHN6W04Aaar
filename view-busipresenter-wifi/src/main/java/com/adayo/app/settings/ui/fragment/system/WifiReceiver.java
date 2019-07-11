package com.adayo.app.settings.ui.fragment.system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.View;

import com.adayo.app.settings.utils.WiFiUtil;

import java.util.List;


/**
 * Created by Administrator on 2018/12/17 0017.
 */


/*接收wifi状态变更以及结果*/
public class WifiReceiver extends BroadcastReceiver {

    private Context mcontex;
    private NetFragment netFragment;
    private WifiManager wifimanager;
    private List<ScanResult> scanResult;
    public static String TAG = "WifiReceiver";
    public static WifiReceiver receiver = null;
    private WifiDialogUtils dialoginstance;
    private WiFiUtil instance;


    public WifiReceiver() {

    }


    public static WifiReceiver getWifiReceiverInstance() {
        if (receiver == null) {
            synchronized (WifiReceiver.class) {
                if (receiver == null) {
                    receiver = new WifiReceiver();
                }

            }
        }
        return receiver;
    }

    WifiReceiver(Context context) {
        this.mcontex = context;
        wifimanager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        dialoginstance = WifiDialogUtils.getDialoginstance(mcontex);
        instance = WiFiUtil.getInstance(context);
        netFragment = NetFragment.getNetFragmentInstance();
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null) {
            switch (action) {
                case WifiManager.WIFI_STATE_CHANGED_ACTION:
                    //WIFI打开还是关闭或者是位置状态
                    handlerWifiState(intent);
                    break;
                case WifiManager.SCAN_RESULTS_AVAILABLE_ACTION:
                    //扫描结果
                    handlerWifiScanResult();
                    break;
                case WifiManager.NETWORK_STATE_CHANGED_ACTION:
                    //用于判断连接状态
                    handlerWifiConnectState(intent);
                    break;
                case WifiManager.SUPPLICANT_STATE_CHANGED_ACTION:
                    //用于判断连接状态变化
                    handlerWifiStateChange(intent);
                    break;
            }

        }

    }


    /**
     * 这个方法用于处理wifi的连接状态发生改变
     *
     * @param intent
     */
    private void handlerWifiConnectState(Intent intent) {

        //拿到NetworkInfo
        NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        //判断连接上了哈
        if (null != networkInfo) {
            NetworkInfo.DetailedState detailedState = networkInfo.getDetailedState();
            int state = changeState(detailedState);
            WiFiUtil.Connection_status = state;
            Log.d(TAG, "handlerWifiConnectState: state==="+state);
            if(state == 2){
                instance.paixu();
            }
            updata();

        }
//        if (null != networkInfo && networkInfo.isConnected()) {
//            //连接上了,就把wifi的信息传出去
//            WifiInfo wifiInfo = intent.getParcelableExtra(WifiManager.EXTRA_WIFI_INFO);
//            if (wifiInfo != null) {
//                //把结果回传出去
//            }
//        }

    }


    /**
     * 这个方法用于通知连接状态变化
     */
    private void handlerWifiStateChange(Intent intent) {
        WifiInfo connectionInfo = wifimanager.getConnectionInfo();
//        SupplicantState state = connectionInfo.getSupplicantState();
//        if (state == SupplicantState.ASSOCIATED) {
//            //关联完成
//            WiFiUtil.Connection_status = 1;
//            updata();
//            Log.d(TAG, "onReceive: 关联AP完成");
//        } else if (state == SupplicantState.AUTHENTICATING) {
//            //正在验证
//            WiFiUtil.Connection_status = 1;
//            updata();
//            Log.d(TAG, "onReceive: 正在验证");
//        } else if (state == SupplicantState.ASSOCIATING) {
//            //正在关联....
//            WiFiUtil.Connection_status = 1;
//            updata();
//            Log.d(TAG, "onReceive: 正在关联AP");
//        } else if (state == SupplicantState.COMPLETED) {
//            Log.d(TAG, "onReceive: 已连接");
//            WiFiUtil.Connection_status = 2;
//            //连接成功：若是人为点击链接的，弹框消失，若自动链接，不提示弹框
//            Log.d(TAG, "complted sucess" + WiFiUtil.getInstance(mcontex).getSSID());
//            instance.paixu();
////            Wifi_ConfirmDialog.getInstance(mcontex,0,"").dismiss();
//            updata();
//
//        } else if (state == SupplicantState.DISCONNECTED) {
//            Log.d(TAG, "onReceive: 已断开");
//            Log.d(TAG, "disconnected");
//            updata();
//        } else if (state == SupplicantState.DORMANT) {
//            //暂停活动
//        } else if (state == SupplicantState.FOUR_WAY_HANDSHAKE) {
//            //四路握手
//        } else if (state == SupplicantState.GROUP_HANDSHAKE) {
//            //"GROUP_HANDSHAKE
//        } else if (state == SupplicantState.INACTIVE) {
//            //休眠中
//        } else if (state == SupplicantState.INVALID) {
//            //无效
//        } else if (state == SupplicantState.SCANNING) {
//            //扫描中
//        } else if (state == SupplicantState.UNINITIALIZED) {
//            //未初始化
//        }

        int error = intent.getIntExtra(WifiManager.EXTRA_SUPPLICANT_ERROR, -1);
        if (error == WifiManager.ERROR_AUTHENTICATING) {
            WiFiUtil.Connection_status = 0;
            if(instance.scanListResult != null){
                instance.removeNet(instance.scanListResult.SSID);
                dialoginstance.doCon(instance.scanListResult, true);
                instance.scanListResult = null;
                Log.d(TAG, "handlerWifiStateChange: 显示密码错误弹窗");
            }

        }
    }

    /**
     * 这个方法用于通知wifi扫描有结果
     */
    private void handlerWifiScanResult() {

        if (instance.isWifiEnabled()) {
            netFragment.stopRotate();
//            netFragment.handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Log.d(TAG, "run: 自动刷新 开启动画" );
//                    netFragment.startRotate();
//                }
//            },5000);
            scanResult = instance.getScanResult();
            if (scanResult != null) {
                NetFragment.results.clear();
                NetFragment.results.addAll(scanResult);
            }
            //1 置顶已连接 去除热点名称重复的热点
            instance.paixu();
            updata();
        }
    }


    /**
     * 这个方法用于处理wifi的状态,打开,打开中..关闭,关闭中..
     *
     * @param intent
     */
    private void handlerWifiState(Intent intent) {
        int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
        //对wifi的状态进行处理
        switch (wifiState) {
            case WifiManager.WIFI_STATE_ENABLED:
                //wifi已经打开..
                Log.d(TAG, "handlerWifiState: 开始搜索");
                netFragment.wifi_power.setSwitchButtonState(true);
                netFragment.wifi_list.setVisibility(View.VISIBLE);
                instance.wifiAP_close();
//                netFragment.handler.sendEmptyMessage(0x01);
                netFragment.startRotate();
                instance.startScan();
                break;
            case WifiManager.WIFI_STATE_ENABLING:
                //wifi打开中..
                break;
            case WifiManager.WIFI_STATE_DISABLED:
                //wifi关闭了..
                netFragment.stopRotate();
                netFragment.wifi_power.setSwitchButtonState(false);
                netFragment.wifi_list.setVisibility(View.GONE);
                break;
            case WifiManager.WIFI_STATE_DISABLING:
                //wifi关闭中..
                break;
            case WifiManager.WIFI_STATE_UNKNOWN:
                //未知状态..
                break;
        }
    }


    //适配器
    private void updata() {
        netFragment.adapter.notifyDataSetChanged();
        Log.d(TAG, "updata: ");
    }

    private int changeState(NetworkInfo.DetailedState detailedState) {
        int state = 0;
        switch (detailedState) {
            case IDLE:
                //空闲
                break;
            case FAILED:
                //失败
                break;
            case BLOCKED:
                //已阻止
                break;
            case SCANNING:
                //正在扫描
                break;
            case CONNECTED:
                //已连接
                state = 2;
                break;
            case SUSPENDED:
                //已暂停
                break;
            case CONNECTING:
                //连接中
                state = 1;
                break;
            case DISCONNECTED:
                //已断开
                state = 0;
                break;
            case DISCONNECTING:
                //正在断开连接
                break;
            case AUTHENTICATING:
                //正在进行身份验证
                state = 1;
                break;
            case OBTAINING_IPADDR:
                //正在获取Ip地址
                state = 1;
                break;
            case VERIFYING_POOR_LINK:
                //暂时关闭（网络状况不佳）
                break;
            case CAPTIVE_PORTAL_CHECK:
                //判断是否需要浏览器二次登录
                break;
        }
        return state;
    }


}
