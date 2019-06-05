package com.adayo.app.settings.utils;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import static com.adayo.app.settings.ui.fragment.system.NetFragment.results;

/**
 * WiFi帮助类
 */
public class WiFiUtil {
    // 定义WifiManager对象
    private WifiManager mWifiManager;
    // 定义WifiInfo对象
    private WifiInfo mWifiInfo;
    // 扫描出的网络连接列表
    private List<ScanResult> mWifiList;
    // 网络连接列表
    private List<WifiConfiguration> mWifiConfiguration;
    // 定义一个WifiLock
    private WifiManager.WifiLock mWifiLock;
    public ScanResult scanListResult = null; //点击的wifi列表下标
    private String TAG = "TAG";
    private static WiFiUtil util;
    public static final int WIFI_AP_STATE_DISABLING = 10;
    public static final int WIFI_AP_STATE_DISABLED = 11;
    public static final int WIFI_AP_STATE_ENABLING = 12;
    public static final int WIFI_AP_STATE_ENABLED = 13;

    private static final int ONLY_SSID = 1;
    private static final int ONLY_PASS = 2;

    public static int Connection_status = 0;
    public static final int WIFI_LOCK_TYPE_WPA = 1;
    public static final int WIFI_LOCK_TYPE_WPA2 = 2;
    public static final int WIFI_LOCK_TYPE_WEP = 3;
    public static final int WIFI_LOCK_TYPE_OPEN = 0;

    /**
     * 单例方法
     *
     * @param context
     * @return
     */
    public static WiFiUtil getInstance(Context context) {
        if (util == null) {
            synchronized (WiFiUtil.class) {
                util = new WiFiUtil(context);
            }
        }
        return util;
    }

    // 构造器
    private WiFiUtil(Context context) {
        // 取得WifiManager对象
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        // 取得WifiInfo对象
        mWifiInfo = mWifiManager.getConnectionInfo();
    }




    // 打开WIFI
    public void openWifi() {
        if (!mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(true);
        }
    }

    // 关闭WIFI
    public void closeWifi() {
        if (mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(false);
        }
    }

    // 检查当前WIFI状态
    public int checkState() {
        return mWifiManager.getWifiState();
    }

    // 锁定WifiLock
    public void acquireWifiLock() {
        mWifiLock.acquire();
    }

    // 解锁WifiLock
    public void releaseWifiLock() {
        // 判断时候锁定
        if (mWifiLock.isHeld()) {
            mWifiLock.acquire();
        }
    }

    // 创建一个WifiLock
    public void creatWifiLock(String lockName) {
        mWifiLock = mWifiManager.createWifiLock(lockName);
    }

    // 得到配置好的网络
    public List<WifiConfiguration> getConfiguration() {
        return mWifiConfiguration;
    }

    // 指定配置好的网络进行连接
    public void connectConfiguration(int index) {
        // 索引大于配置好的网络索引返回
        if (index > mWifiConfiguration.size()) {
            return;
        }
        // 连接配置好的指定ID的网络
        mWifiManager.enableNetwork(mWifiConfiguration.get(index).networkId, true);
    }
    // 指定配置好的网络进行连接
    public void connectConfigurationByNetworkId(int networkId) {
        // 索引大于配置好的网络索引返回
        // 连接配置好的指定ID的网络
        disconnectWiFiNetWork();
        mWifiManager.enableNetwork(networkId, true);
    }
//    public void startScan() {
//        mWifiManager.startScan();
//        // 得到扫描结果
//        mWifiList = mWifiManager.getScanResults();
//        // 得到配置好的网络连接
//        mWifiConfiguration = mWifiManager.getConfiguredNetworks();
//    }

    // 得到网络列表
    public List<ScanResult> getWifiList() {
        return mWifiList;
    }

    // 查看扫描结果
    public StringBuilder lookUpScan() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mWifiList.size(); i++) {
            stringBuilder
                    .append("Index_" + new Integer(i + 1).toString() + ":");
            // 将ScanResult信息转换成一个字符串包
            // 其中把包括：BSSID、SSID、capabilities、frequency、level
            stringBuilder.append((mWifiList.get(i)).toString());
            stringBuilder.append("/n");
        }
        return stringBuilder;
    }

    public int getLevel(int mRssi) {
        if (mRssi == Integer.MAX_VALUE) {
            return -1;
        }
        return WifiManager.calculateSignalLevel(mRssi, 4);
    }

    // 得到MAC地址
    public String getMacAddress() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getMacAddress();
    }

    // 得到接入点的BSSID
    public String getBSSID() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getBSSID();
    }

    // 得到IP地址
    public int getIPAddress() {
        return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
    }

    // 得到连接的ID
    public int getNetworkId() {
        mWifiInfo =  mWifiManager.getConnectionInfo();
        return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();
    }

    public int getNetworkId(String ssid) {
        int networkId = -1;
        List<WifiConfiguration> configs = mWifiManager.getConfiguredNetworks();
        for (int i = 0; i < configs.size(); i++) {
            WifiConfiguration wifiConfiguration = configs.get(i);
            Log.d(TAG, "getNetworkId: "+wifiConfiguration.toString());
            if (wifiConfiguration.SSID.equals("\"" + ssid + "\"")) {
                networkId = wifiConfiguration.networkId;
            }
        }
        return networkId;
    }

    // 得到连接的SSID
    public String getSSID() {
        mWifiInfo =  mWifiManager.getConnectionInfo();
        return (mWifiInfo == null) ? "" : mWifiInfo.getSSID();
    }

    // 得到WifiInfo的所有信息包
    public String getWifiInfo() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.toString();
    }

    public  void paixu(){
        if( results != null){
            //去重
            for  ( int i = 0; i < results.size() - 1;i++ )  {
                for  ( int j = results.size()-1; j > i;j--) {
                    if  (results.get(j).SSID.equals(results.get(i).SSID)) {
                        results.remove(j);
                    }
                }
            }
            //置顶
            for (int i = 0; i < results.size(); i++) {
                ScanResult scanResult = results.get(i);
                if (("\"" + scanResult.SSID + "\"").equals(getSSID())) {
                    results.remove(i);
                    results.add(0, scanResult);
                    //   Log.d(TAG, "ui_notify，有热点连接成功，排序置顶");
                    break;
                }
            }
        }
    }

    // 添加一个网络并连接
    public void addNetwork(WifiConfiguration wcg) {
        int wcgID = mWifiManager.addNetwork(wcg);
        boolean b = mWifiManager.enableNetwork(wcgID, true);
        System.out.println("a--" + wcgID);
        System.out.println("b--" + b);
    }

    /**
     * 添加WiFi网络
     *
     * @param SSID
     * @param password
     * @param type
     */
    public int addWiFiNetwork(String SSID, String password, Data type) {
        // 创建WiFi配置
        disconnectWiFiNetWork();
        WifiConfiguration configuration = createWifiConfig(SSID, password, type);
        // 添加WIFI网络
        int networkId = mWifiManager.addNetwork(configuration);

        Log.d(TAG, "addWiFiNetwork: networkId=="+networkId);
        if (networkId == -1) {
            return -1;
        }
        // 使WIFI网络有效
        mWifiManager.enableNetwork(networkId, true);

        return networkId;
    }

    /**
     * 断开WiFi连接
     *
     * @param networkId
     */
    public void disconnectWiFiNetWork(int networkId) {
        // 设置对应的wifi网络停用
        mWifiManager.disableNetwork(networkId);
        // 断开所有网络连接
        mWifiManager.disconnect();
    }

    public void disconnectWiFiNetWork() {

        // 设置对应的wifi网络停用

//        int id = getNetworkId();
//        Log.d(TAG, "disconnectWiFiNetWork: NetworkId="+id );
//
//        if(id != 0 && id != -1){
//            mWifiManager.disableNetwork(id);
//        }
        List<WifiConfiguration> configuredNetworks = mWifiManager.getConfiguredNetworks();
        if(configuredNetworks != null){
            for (int i = 0; i < configuredNetworks.size(); i++) {
                mWifiManager.disableNetwork(configuredNetworks.get(i).networkId);
            }
        }
        // 断开所有网络连接 防止用户手动断开wifi后 又自动连接别的wifi
        mWifiManager.disconnect();
    }


    public void removeNet(String ssid){
        List<WifiConfiguration> configuredNetworks = mWifiManager.getConfiguredNetworks();
        Log.d(TAG, "removeNet: " );
        if(configuredNetworks != null){
            for (int i = 0; i < configuredNetworks.size(); i++) {
                WifiConfiguration wifiConfiguration = configuredNetworks.get(i);
                Log.d(TAG, "removeNet: SSID=== "+wifiConfiguration.SSID );
                if(wifiConfiguration.SSID.equals("\"" +ssid+ "\"")){
                    Log.d(TAG, "removeNet: 删除网络 ssid=="+ ssid+"---networkId="+wifiConfiguration.networkId);
                    mWifiManager.removeNetwork(wifiConfiguration.networkId);
                    break;
                }
            }
        }
    }

    /**
     * 创建WifiConfiguration
     * 三个安全性的排序为：WEP<WPA<WPA2。
     * WEP是Wired Equivalent Privacy的简称，有线等效保密（WEP）协议是对在两台设备间无线传输的数据进行加密的方式，
     * 用以防止非法用户窃听或侵入无线网络
     * WPA全名为Wi-Fi Protected Access，有WPA和WPA2两个标准，是一种保护无线电脑网络（Wi-Fi）安全的系统，
     * 它是应研究者在前一代的系统有线等效加密（WEP）中找到的几个严重的弱点而产生的
     * WPA是用来替代WEP的。WPA继承了WEP的基本原理而又弥补了WEP的缺点：WPA加强了生成加密密钥的算法，
     * 因此即便收集到分组信息并对其进行解析，也几乎无法计算出通用密钥；WPA中还增加了防止数据中途被篡改的功能和认证功能
     * WPA2是WPA的增强型版本，与WPA相比，WPA2新增了支持AES的加密方式
     *
     * @param SSID
     * @param password
     * @param type
     * @return
     **/
    private WifiConfiguration createWifiConfig(String SSID, String password, int type) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + SSID + "\"";

        if (type == WIFI_LOCK_TYPE_OPEN) {
            config.wepKeys[0] = "";
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        } else if (type == WIFI_LOCK_TYPE_WEP) {
            config.hiddenSSID = true;
            config.wepKeys[0] = "\"" + password + "\"";
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        } else if (type == WIFI_LOCK_TYPE_WPA) {
            config.preSharedKey = "\"" + password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.status = WifiConfiguration.Status.ENABLED;
        } else if (type == WIFI_LOCK_TYPE_WPA2) {
            config.preSharedKey = "\"" + password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            config.status = WifiConfiguration.Status.ENABLED;
        }

        return config;
    }

    private WifiConfiguration createWifiConfig(String SSID, String password, Data type) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + SSID + "\"";

        if (type == Data.WIFI_CIPHER_NOPASS) {
            config.wepKeys[0] = "";
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        } else if (type == Data.WIFI_CIPHER_WEP) {
            config.hiddenSSID = true;
            config.wepKeys[0] = "\"" + password + "\"";
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        } else if (type == Data.WIFI_CIPHER_WPA) {
            config.preSharedKey = "\"" + password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.status = WifiConfiguration.Status.ENABLED;
        } else if (type == Data.WIFI_CIPHER_WPA2) {
            config.preSharedKey = "\"" + password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            config.status = WifiConfiguration.Status.ENABLED;
        }

        return config;
    }

    /**
     * 密码加密类型
     */
    public enum Data {
        WIFI_CIPHER_NOPASS(0), WIFI_CIPHER_WEP(1), WIFI_CIPHER_WPA(2), WIFI_CIPHER_WPA2(3);

        private final int value;

        //构造器默认也只能是private, 从而保证构造函数只能在内部使用
        Data(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    //========================wifi======================
    public void wifi_open() {
        if (!mWifiManager.isWifiEnabled()) {
//            wifiAP_close();
            mWifiManager.setWifiEnabled(true);
            Log.d(TAG, " wifi on");
        } else {
            Log.d(TAG, " wifi has open");
        }
    }


    public void wifi_close()  {
        if (mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(false);
            Log.d(TAG, " wifi off");
        }
    }
    private void onscanresult(List<ScanResult> scanResults) {
        // 将每个搜索到的热点和其他热点进行对比，有重名就去除
        if (scanResults.size() > 0) {
            for (int i = 0; i < scanResults.size(); i++) {
                ScanResult scanResult = scanResults.get(i);//先取出一个，和后面的进行对比
                for (int to = i + 1; to < scanResults.size(); to++) {
                    ScanResult result = scanResults.get(to);
                    if (scanResult.SSID.equals(result.SSID)) {
                        scanResults.remove(to);
                    }
                }

            }
        }

    }
    public List getScanResult() {
        if (mWifiManager.isWifiEnabled()) {
            List<ScanResult> scanResults = mWifiManager.getScanResults();
            onscanresult(scanResults);
            results.clear();
            results.addAll(scanResults);
            return scanResults;
        }
        return results;
    }

    public void startScan()  {
        if (mWifiManager.isWifiEnabled()) {
            mWifiManager.startScan();//开始搜索
        }
    }
    public boolean isWifiEnabled(){
        // Log.d(TAG,"isWifiEnabled");
        return mWifiManager.isWifiEnabled();
    }


    public void disconnect() {
        Log.d(TAG, "disconnect >>>>>>>>success");
        mWifiManager.disconnect();
    }
    public void connectNoPassNet(String ssid)  {
        WifiConfiguration configuration = creatWifiInfo(ssid, "", 1);
            int net_id = mWifiManager.addNetwork(configuration);
            mWifiManager.updateNetwork(configuration);
            mWifiManager.enableNetwork(net_id, true);
            mWifiManager.saveConfiguration();
            mWifiManager.reconnect();
            Log.d(TAG, "connectNoPassNet----->>>---success");
    }
    private WifiConfiguration creatWifiInfo(String SSID, String password, int type) {
        WifiConfiguration configuration = new WifiConfiguration();
        configuration.allowedAuthAlgorithms.clear();
        configuration.allowedGroupCiphers.clear();
        configuration.allowedKeyManagement.clear();
        configuration.allowedPairwiseCiphers.clear();
        configuration.allowedProtocols.clear();
        configuration.SSID = "\"" + SSID + "\"";

        WifiConfiguration tempConfig = IsExsits(SSID);
        if (tempConfig != null) {
            mWifiManager.removeNetwork(tempConfig.networkId);
        }

        if (type == 1) {/*无密码*/
            //  configuration.wepKeys[0] = "";
            configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            //  configuration.wepTxKeyIndex = 0;
        }
        if (type == 2) {/*WEP*/
            configuration.hiddenSSID = true;
            configuration.wepKeys[0] = "\"" + password + "\"";
            configuration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            configuration.wepTxKeyIndex = 0;

        }
        if (type == 3) {/*wpa2/ psk*/
            configuration.preSharedKey = "\"" + password + "\"";
            configuration.hiddenSSID = true;
            configuration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            configuration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            //config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            configuration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            configuration.status = WifiConfiguration.Status.ENABLED;
        }
        return configuration;
    }
    private WifiConfiguration IsExsits(String SSID) {
        List<WifiConfiguration> existingConfigs = mWifiManager.getConfiguredNetworks();
        for (WifiConfiguration existingConfig : existingConfigs) {
            if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
                return existingConfig;
            }
        }
        return null;
    }

    //========================热点======================


    public void wifiAP_open() {
        if (getWifiApState() != WIFI_AP_STATE_ENABLED) {
//            if (mWifiManager.isWifiEnabled()) {
//                mWifiManager.setWifiEnabled(false);///先关闭wifi 2018 12 11
//            }
            //Android8.0及以上版本
            if (Build.VERSION.SDK_INT >= 26) {
//                if(connectivitymanager == null) {
//                    Log.d(TAG,"connectivitymanager == null");
//                    return;
//                }
//                connectivitymanager.startTethering(ConnectivityManager.TETHERING_WIFI, true, new ONStartTetheringCallback());
                mWifiManager.startSoftAp(getWifiAPconfigure());
            }
        }
    }

    public void wifiAP_close() {
        //TODO 关闭热点
        if (getWifiApState() != WIFI_AP_STATE_DISABLED) {
            if (Build.VERSION.SDK_INT >= 26) {
//                if(connectivitymanager == null) {
//                    Log.d(TAG,"setwifiAPdisable  connectivitymanager == null");
//                    return;
//                }
//                connectivitymanager.stopTethering(ConnectivityManager.TETHERING_WIFI);
                    mWifiManager.stopSoftAp();
            }
        }
    }

    public WifiConfiguration getWifiAPconfigure(){
        return mWifiManager.getWifiApConfiguration();
    }


    public int getSecurityType()  {
        //TODO  加密类型  无密码类型
        WifiConfiguration wifiAPconfigure = getWifiAPconfigure();
        if (wifiAPconfigure.preSharedKey == null) {
            Log.d(TAG, "当前热点加密类型：无密码");
            return 1;
        } else {
            Log.d(TAG, "当前热点加密类型：加密热点");
            return 2;
        }
    }

    public void onlySetAPSSID(String ssid,boolean wpa_psk) {
        setSSIDorPassword(ssid, "", ONLY_SSID, wpa_psk);

    }

    public void onlySetAPPassword(String password,boolean wpa_psk)  {
        setSSIDorPassword("", password, ONLY_PASS,wpa_psk);
    }



     /*
    * 针对不同的UI端设置需求，提供两个接口，接口1适用于单独设置ssid或密码。接口2适用于同时设置ssid与密码
    *
    * 接口1 单独设置
    *
    * 接口2  账号密码一次修改
    * */

    private void setSSIDorPassword(String ssid, String password, int tag,Boolean wpa_psk) {
        WifiConfiguration apconf = new WifiConfiguration();
        if (tag == ONLY_SSID) {/*只修改ssid，不修改密码*/
            apconf.SSID = ssid;

            try {
                if (!wpa_psk) {/*无密码状态*/
                    apconf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                    mWifiManager.setWifiApConfiguration(apconf);//保存配置
                    Log.d(TAG, "保存无密码热点SSID");
                    return;
                } else if (wpa_psk) {/*有密码*/
                    apconf.preSharedKey = getWifiAPconfigure().preSharedKey;
                    Log.d(TAG, "保存加密热点SSID");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (tag == ONLY_PASS) {/*只修改密码，不修改ssid*/
            apconf.SSID = getWifiAPconfigure().SSID;
            apconf.preSharedKey = password;
            Log.d(TAG, "保存PASS修改");
        }

        apconf.hiddenSSID = false;
        apconf.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
        apconf.allowedKeyManagement.set(4);//wpa2_psk
        apconf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        apconf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        apconf.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        apconf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        apconf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        apconf.status = WifiConfiguration.Status.ENABLED;

        mWifiManager.setWifiApConfiguration(apconf);//修改hostapd.conf设置SSID和pass
    }

    public int getWifiApState(){
        return mWifiManager.getWifiApState();
    }

    public boolean setWifiAp(String apName, String apPassword) {

        try {
            //热点的配置类
            WifiConfiguration apConfig = new WifiConfiguration();
            //配置热点的名称(可以在名字后面加点随机数什么的)
            apConfig.SSID = apName;
            apConfig.preSharedKey = apPassword;
            //不设置密码
            apConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            //android 8.0开启wifi热点
            if (Build.VERSION.SDK_INT >= 26) {
                Method configMethod = mWifiManager.getClass().getMethod("setWifiApConfiguration", WifiConfiguration.class);
                try {
                    boolean isConfigured = (Boolean) configMethod.invoke(mWifiManager, apConfig);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                Method method = mWifiManager.getClass().getMethod("startSoftAp", WifiConfiguration.class);
                //返回热点打开状态
                return (Boolean) method.invoke(mWifiManager, apConfig);
            } else {
                //通过反射调用设置热点
                Method method = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);
                //返回热点打开状态
                return (Boolean) method.invoke(mWifiManager, apConfig, true);
            }
        } catch (NoSuchMethodException e) {
            e.getMessage();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
    }




}
