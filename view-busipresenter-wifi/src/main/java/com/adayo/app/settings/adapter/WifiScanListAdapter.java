package com.adayo.app.settings.adapter;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adayo.app.settings.R;
import com.adayo.app.settings.ui.fragment.system.NetFragment;
import com.adayo.app.settings.utils.WiFiUtil;


/**
 * Created by Administrator on 2018/12/17 0017.
 */

public class WifiScanListAdapter extends android.widget.BaseAdapter {
    private final Context mContext;

    private ScanResult result;
    private wifi_info info;

    public WifiScanListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return NetFragment.results == null ? 0 : NetFragment.results.size();
    }

    @Override
    public Object getItem(int position) {
        return NetFragment.results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        result = NetFragment.results.get(position);
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.fragment_net_wifi, null);
            info = new wifi_info();
            info.tv_ssid = (TextView) convertView.findViewById(R.id.tv_ssid_item);//热点ssid
            info.img_sign = (ImageView) convertView.findViewById(R.id.img_sig_item);//信号强度图标
            info.tv_status = (TextView) convertView.findViewById(R.id.tv_status_item);//连接状态
            convertView.setTag(info);

        } else {
            info = (wifi_info) convertView.getTag();
        }

        if (result != null) {
            info.tv_ssid.setTextColor(mContext.getResources().getColor(R.color.normal_color));
            info.tv_ssid.setText(result.SSID.toString());//设置SSID
        }
        Log.d("TAG", "getView: getSSID==" + WiFiUtil.getInstance(mContext).getSSID());
        if (WiFiUtil.getInstance(mContext).getSSID().equals("\"" + result.SSID + "\"")) {
            Log.d("TAG", "getView:getSSID()== result.SSID ");
            info.tv_status.setVisibility(View.VISIBLE);
            /*让连接状态显示实时  2019 01 15*/
            if (WiFiUtil.Connection_status == 1) {
                info.tv_status.setText(mContext.getResources().getString(R.string.string6));
            } else if (WiFiUtil.Connection_status == 2) {
                info.tv_status.setText(mContext.getResources().getString(R.string.string7));
            }

        } else {
            info.tv_status.setVisibility(View.GONE);
        }

        updataUI();


        return convertView;
    }


    static class wifi_info {
        TextView tv_ssid;
        TextView tv_status;
        ImageView img_sign;
    }

    //更新每个item的信号强度，
    private void updataUI() {
        int level = WiFiUtil.getInstance(mContext).getLevel(result.level);
        Log.d("TAG", "updataUI:level=== " + level + "result.capabilities=" + result.capabilities);
        boolean noPass = isNoPass(result.capabilities);
        if (level == 0) {
//                if (!result.capabilities.equals( WiFiUtil.Data.WIFI_CIPHER_NOPASS)) {
//                    info.img_sign.setImageResource(R.drawable.wifi_ock_1);
//                } else {
//                    info.img_sign.setImageResource(R.drawable.wifi_open_1);
//                }
            if (noPass) {
                info.img_sign.setImageResource(R.drawable.wifi_open_1);
            } else {
                info.img_sign.setImageResource(R.drawable.wifi_ock_1);
            }

        } else if (level == 1) {

            if (noPass) {
                info.img_sign.setImageResource(R.drawable.wifi_open_2);
            } else {
                info.img_sign.setImageResource(R.drawable.wifi_ock_2);
            }
        } else if (level == 2) {

            if (noPass) {
                info.img_sign.setImageResource(R.drawable.wifi_open_3);
            } else {
                info.img_sign.setImageResource(R.drawable.wifi_ock_3);
            }
        } else if (level == 3) {

            if (noPass) {
                info.img_sign.setImageResource(R.drawable.wifi_open_4);
            } else {
                info.img_sign.setImageResource(R.drawable.wifi_ock_4);
            }

        }


    }

    public boolean isNoPass(String capabilities) {
        boolean isNoPass = false;
        if (capabilities.contains("WPA2") || capabilities.contains("WPA-PSK") || capabilities.contains("WPA") || capabilities.contains("WEP")) {
            isNoPass = false;
        } else {
            isNoPass = true;
        }
        Log.d("TAG", "isNoPass: " + isNoPass);
        return isNoPass;
    }


}
