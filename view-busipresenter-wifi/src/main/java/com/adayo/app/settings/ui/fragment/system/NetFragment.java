package com.adayo.app.settings.ui.fragment.system;

import android.Manifest;
import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.adayo.app.base.BaseFragment;
import com.adayo.app.settings.R;
import com.adayo.app.settings.adapter.WifiScanListAdapter;
import com.adayo.app.settings.fota.app.FotaApplication;
import com.adayo.app.settings.listener.WifiItemOnclickListener;
import com.adayo.app.settings.ui.view.ItemMenuView;

import com.adayo.app.settings.utils.WiFiUtil;
import com.adayo.commonui.tablayout.SegmentTabLayout;
import com.adayo.systemserviceproxy.SystemServiceManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuefengduan
 * @version 1.0
 * @date 2018/11/14.
 * @desc. 网络Fragment
 */
public class NetFragment extends BaseFragment {
    public static List<ScanResult> results = new ArrayList<>();
    public static ItemMenuView wifi_power;
    public static ListView listview;
    public static String TAG = "NetFragment-v1.2";
    private WifiReceiver receiver;
    public static NetFragment netfragment = null;
    public static ItemMenuView ap_power;
    public static WifiScanListAdapter adapter;
    public static final int WIFI_AP_STATE_DISABLING = 10;
    public static final int WIFI_AP_STATE_DISABLED = 11;
    public static final int WIFI_AP_STATE_ENABLING = 12;
    public static final int WIFI_AP_STATE_ENABLED = 13;
    public static final String WIFI_AP_STATE_CHANGED_ACTION = "android.net.wifi.WIFI_AP_STATE_CHANGED";
    public static final String EXTRA_WIFI_AP_STATE = "wifi_state";
    private WifiApReceiver mReceiver;
    public static boolean isSetting = false;

    public static SegmentTabLayout security_type;
//    public static TextView tv_sec_nopass;
//    public static TextView tv_sec_jiami;
    public static LinearLayout lin_wifiap_set;
    public static TextView tv_ap_ssid;
    public static TextView tv_ap_pass;
    private Button btn_set_pass;
    private Button btn_set_ssid;
    public static LinearLayout wifi_list;
    private TextView wifi_internet;
    private TextView wifi_internet1;
    public static   ImageView wifi_loading;
    private WiFiUtil instance;
    public static int currentwifiNightMode;
    private static ImageView wifi_refresh;
    private LinearLayout ap_part;
    public static LinearLayout wifi_tip;
    public static ImageView settting_loading1;


    //提供给外部的实例
    public static NetFragment getNetFragmentInstance() {
        if (netfragment == null) {
            synchronized (NetFragment.class) {
                if (netfragment == null) {
                    netfragment = new NetFragment();
                }
            }
        }
        return netfragment;
    }
    public  Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 0x01:
                    Log.d(TAG, "handleMessage: 0x01" );
//                    startRotate();
                    break;
                case 0x02:
                    Log.d(TAG, "handleMessage: 0x02" );
                    security_type.setCurrentTab(1);
                    break;
            }
            return false;
        }
    });

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: context=="+context );
    }

    @Override
    public int getLayout() {
        Log.d(TAG,"getlayout");
        return R.layout.fragment_net;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged: uiMode===" +newConfig.uiMode+"---locale="+newConfig.locale.toString());
        currentwifiNightMode = newConfig.uiMode;
        if(newConfig.uiMode == 19 || newConfig.uiMode == 35){
            updateImg();
            updateText();
        }

        if(newConfig.locale.toString().equals("zh_CN") || newConfig.locale.toString().equals("en")){
            updateText();
        }
    }



    private void updateImg() {
//        security_type.flush(getMContext());
        btn_set_ssid.setBackground(getMContext().getDrawable(R.drawable.setting_button_bg_p));
        btn_set_pass.setBackground(getMContext().getDrawable(R.drawable.setting_button_bg_p));
//        security_type.setIndicatorColor(getResources().getColor(R.color.tl_indicator_color));
        security_type.setTextSelectColor(getResources().getColor(R.color.switch_select_color));
        security_type.setTextUnselectColor(getResources().getColor(R.color.switch_unselect_color));
//        security_type.setBarColor(getResources().getColor(R.color.tl_bar_color));
//        security_type.setBarStrokeColor(getResources().getColor(R.color.tl_stoke_color));
        security_type.setBarBackgroud(getResources().getDrawable(R.drawable.button_bg));
        security_type.setIndicatorBackground(getResources().getDrawable(R.drawable.button_p));
        int i = instance.checkState();
        Log.d(TAG, "initWifiUI: wifi状态=="+i );
        wifi_power.setSwitchBg();
        ap_power.setSwitchBg();
    }

    public  void updateText(){
        Log.d(TAG, "updateText: " );
        wifi_internet.setText(getMContext().getResources().getString(R.string.string1));
        wifi_power.setText(getMContext().getResources().getString(R.string.string2));
        ap_power.setText(getMContext().getResources().getString(R.string.string26));
        wifi_internet1.setText(getMContext().getResources().getString(R.string.string1));
        security_type.setTabData(getMContext().getResources().getStringArray(R.array.tab_layout));
//        security_type.flush(getMContext());
        btn_set_ssid.setText(getMContext().getResources().getString(R.string.string16));
        btn_set_pass.setText(getMContext().getResources().getString(R.string.string16));
        if (instance.getSecurityType()  == 1) {/*无密码类型*/
            tv_ap_pass.setText(getMContext().getResources().getString(R.string.string20));
        }else {
            tv_ap_pass.setText(getMContext().getResources().getString(R.string.string17)+": " + instance.getWifiAPconfigure().preSharedKey);
        }
//        wifi_refresh.setText(getMContext().getResources().getString(R.string.string28));
//        wifi_refresh.setTextColor(getMContext().getResources().getColor(R.color.normal_color));
        tv_ap_ssid.setText(getMContext().getResources().getString(R.string.string15)+": " + instance.getWifiAPconfigure().SSID);
        wifi_internet.setTextColor(getMContext().getResources().getColor(R.color.normal_color));
        wifi_internet1.setTextColor(getMContext().getResources().getColor(R.color.normal_color));
        btn_set_ssid.setTextColor(getMContext().getResources().getColor(R.color.again_color));
        btn_set_pass.setTextColor(getMContext().getResources().getColor(R.color.again_color));
        tv_ap_pass.setTextColor(getMContext().getResources().getColor(R.color.normal_color));
        tv_ap_ssid.setTextColor(getMContext().getResources().getColor(R.color.normal_color));
        adapter.notifyDataSetChanged();

    }


    /**
     * 开启动画
     */
    public  void startRotate() {
        Context appContext = FotaApplication.getAppContext();
        Log.d(TAG, "startRotate:appContext=== "+appContext+"--wifi_loading=="+wifi_loading );
        if(appContext != null && wifi_loading != null && wifi_refresh != null){
                Animation operatingAnim = AnimationUtils.loadAnimation(appContext, R.anim.wifi_version_image_rotate);
                LinearInterpolator lin = new LinearInterpolator();
                operatingAnim.setInterpolator(lin);
                if (operatingAnim != null) {
                    wifi_refresh.setVisibility(View.GONE);
                    wifi_loading.setVisibility(View.VISIBLE);
                    wifi_loading.startAnimation(operatingAnim);
                }
        }

    }

    /**
     * 关闭动画
     */
    public void stopRotate() {
        if(wifi_loading != null && wifi_refresh != null){
            wifi_loading.clearAnimation();
            wifi_refresh.setVisibility(View.VISIBLE);
            wifi_loading.setVisibility(View.GONE);
        }

    }

    /**
     * 开启动画
     */
    public static void startRotate1() {
        Context appContext = FotaApplication.getAppContext();
        if(appContext != null && settting_loading1 != null ){
            Animation operatingAnim = AnimationUtils.loadAnimation(appContext, R.anim.wifi_version_image_rotate);
            LinearInterpolator lin = new LinearInterpolator();
            operatingAnim.setInterpolator(lin);
            if (operatingAnim != null) {
                settting_loading1.startAnimation(operatingAnim);
            }
        }

    }

    /**
     * 关闭动画
     */
    public static void stopRotate1() {
        if(settting_loading1 != null){
            settting_loading1.clearAnimation();
        }

    }

    private static SystemServiceManager mSystemServiceManager = SystemServiceManager.getInstance();
    @Override
    public void initView() {
        currentwifiNightMode = getResources().getConfiguration().uiMode;
        ap_power =  mContentView.findViewById(R.id.img_ap_power);
        wifi_refresh = mContentView.findViewById(R.id.wifi_refresh);
        wifi_power =  mContentView.findViewById(R.id.img_wifi_power);
        wifi_loading = (ImageView) mContentView.findViewById(R.id.wifi_loading_img);
        listview = (ListView) mContentView.findViewById(R.id.lsv_wifi);
        wifi_list = (LinearLayout) mContentView.findViewById(R.id.wifi_list);
        security_type = mContentView.findViewById(R.id.switch_ap_security_select);
        lin_wifiap_set = (LinearLayout) mContentView.findViewById(R.id.lin_ap);
        tv_ap_ssid = (TextView) mContentView.findViewById(R.id.tv_ssid);
        tv_ap_pass = (TextView) mContentView.findViewById(R.id.tv_pass);
        btn_set_pass = (Button) mContentView.findViewById(R.id.img_buttton_SetPass);
        btn_set_ssid = (Button) mContentView.findViewById(R.id.img_buttton_Setssid);
        wifi_internet = (TextView) mContentView.findViewById(R.id.wifi_internet);
        wifi_internet1 = (TextView) mContentView.findViewById(R.id.wifi_internet1);
        ap_part = (LinearLayout) mContentView.findViewById(R.id.ap_part);
        wifi_tip = mContentView.findViewById(R.id.wifi_tip);
        settting_loading1 = mContentView.findViewById(R.id.wifi_loading);
        security_type.setTabData(getMContext().getResources().getStringArray(R.array.tab_layout));
        mSystemServiceManager.conectsystemService();
        try {
            Byte TBOX = mSystemServiceManager.getOffLineConfigInfo("HN6W04A_IsTBOX");
//             TBOXString = Integer.toBinaryString((TBOX & 0xFF)+0x100).substring(1); //转换成二进制字符串,根据自己需要使用
            Log.d(TAG, "initView: TBOX = "+TBOX );
            if(TBOX == 0x0){
                //没有TBOX
                ap_part.setVisibility(View.GONE);
            }else if(TBOX == 0x1){
                //有TBOX
                ap_part.setVisibility(View.VISIBLE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        adapter = new WifiScanListAdapter(getActivity());
        listview.setAdapter(adapter);
        instance = WiFiUtil.getInstance(getActivity());

        initWifiUI();
//        startRotate();
        Log.d(TAG, "initView");

    }

    //设置wifi开关状态
    private void initWifiUI() {
        int i = instance.checkState();
        Log.d(TAG, "initWifiUI: wifi状态=="+i +"--getWifiApState=="+instance.getWifiApState());
        if(i == WifiManager.WIFI_STATE_DISABLED){
            //关闭
            wifi_power.setSwitchButtonState(false);
            wifi_list.setVisibility(View.GONE);
            //TODO  wifi处于关闭状态时候，热点有可能是开启的
            if (instance.getWifiApState() == WIFI_AP_STATE_ENABLED) {
                ap_power.setSwitchButtonState(true);
                lin_wifiap_set.setVisibility(View.VISIBLE);
            } else if (instance.getWifiApState() == WIFI_AP_STATE_DISABLED) {
                ap_power.setSwitchButtonState(false);
                lin_wifiap_set.setVisibility(View.GONE);
            }
        }else if(i == WifiManager.WIFI_STATE_ENABLED){
            //开启
            wifi_power.setSwitchButtonState(true);
            ap_power.setSwitchButtonState(false);//开启wifi就关闭热点
            lin_wifiap_set.setVisibility(View.GONE);
            instance.startScan();
        }
        Log.d(TAG, "init wifi ui ");
    }





    @Override
    public void initData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initapUI();
            }
        },10);

        RegisterBroadcast();
        initlistenner();
    }
    public void initapUI(){
        if (instance.getWifiApState() == WIFI_AP_STATE_ENABLED) {
            Log.d(TAG, "初始化热点UI开启");
            ap_power.setSwitchButtonState(true);//开启状态
            lin_wifiap_set.setVisibility(View.VISIBLE);
            if(instance.getSecurityType()  == 1){
                security_type.setCurrentTab(1);
            } else {
                security_type.setCurrentTab(0);
            }
        } else if (instance.getWifiApState() == WIFI_AP_STATE_DISABLED) {
            Log.d(TAG, "初始化热点UI关闭");
            ap_power.setSwitchButtonState(false);//关闭状态
            lin_wifiap_set.setVisibility(View.GONE);
        }

        Log.d(TAG, "init ap ui ");
    }


    private void RegisterBroadcast() {
        //wifi
        receiver = new WifiReceiver(getMContext());
        IntentFilter intentFilter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
        getActivity().registerReceiver(receiver, intentFilter);//注册广播接收驱动状态
        //热点
        mReceiver = new WifiApReceiver();
        IntentFilter filter = new IntentFilter(WIFI_AP_STATE_CHANGED_ACTION);
        getActivity().registerReceiver(mReceiver, filter);
    }


    //初始化UI点击监听和搜索列表监听
    private void initlistenner() {
        WifiItemOnclickListener listener = new WifiItemOnclickListener(receiver,  getMContext());
        wifi_power.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: wifi_power");
                if(instance != null){
                    if (!instance.isWifiEnabled()) {
                        instance.wifi_open();//开启wifi
                    } else if (instance.isWifiEnabled()) {
                        instance.wifi_close();//关闭wifi
                    }
                }
            }
        });
        ap_power.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(instance != null){
                    if (instance.getWifiApState() == WiFiUtil.WIFI_AP_STATE_DISABLED) {
                        instance.wifiAP_open();//开启热点
                        Log.d(TAG, "手动 热 点开启");

                    } else if (instance.getWifiApState() == WiFiUtil.WIFI_AP_STATE_ENABLED) {
                        instance.wifiAP_close();///关闭热点
                        Log.d(TAG, "手动  热点关闭");
                    }
                }

            }
        });
        listview.setOnItemClickListener(listener);
        btn_set_pass.setOnClickListener(listener);
        btn_set_ssid.setOnClickListener(listener);

//        security_type.setHydropowerListener(listener);
//        security_type.setSoftFloorListener(listener);
        security_type.setOnTabSelectListener(listener);



//        wifi_loading.setOnClickListener(listener);
        wifi_refresh.setOnClickListener(listener);

    }

    @Override
    public void setBPresenter(Object o) {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: " );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
        getActivity().unregisterReceiver(mReceiver);
    }


    static final String[] permission = new String[]{
            Manifest.permission.WRITE_SETTINGS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_SECURE_SETTINGS,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CHANGE_CONFIGURATION,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_MULTICAST_STATE,
            Manifest.permission.READ_PHONE_STATE};

    //请求获取手机状态的先关权限，否则AP模式无法开启
//    public void write_settings() {
//
//        if (ContextCompat.checkSelfPermission(getMContext(), PermissionUtils.WRITE_SETTINGS) != PackageManager.PERMISSION_GRANTED) {
//            Log.d(TAG, "request permission:because no permission");//app 启动时候会请求权限进去
//            getActivity().requestPermissions(permission, PermissionUtils.CODE_MULTI_PERMISSION);
//            //Log.d(TAG, "request permission:have into ");
//        } else {
//            Log.d(TAG, "request permission:have have ");
//        }
//    }
}


