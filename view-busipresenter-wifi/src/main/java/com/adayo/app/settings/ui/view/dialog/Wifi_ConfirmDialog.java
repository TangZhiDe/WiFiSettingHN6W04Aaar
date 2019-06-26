package com.adayo.app.settings.ui.view.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adayo.app.settings.R;

/**
 * @author tzd
 *
 */
public class Wifi_ConfirmDialog extends Dialog {

    public static Wifi_ConfirmDialog dialog;
    private Context context;
    private ClickListenerInterface clickListenerInterface;
    public TextView alert_wifi_tip;
    public ImageView alert_wifi_display;
    public EditText alert_wifi_psw;
    private Button alert_wifi_disconnect;
    public TextView alert_wifi_name;
    private Button alert_wifi_cancel;
    private LinearLayout alert_wifi_dialog;
    private LinearLayout alert_wifi_edit;
    private int type; //0:连接wifi  1:修改热点名称  2:修改热点密码 3:断开连接
    private RelativeLayout alert_wifi_model1;
    private LinearLayout alert_wifi_model2;
    public TextView alert_wifi_name1;
    private TextView alert_wifi_tip1;
    private String input;

    private Button alert_wifi_disconnect1;

    public interface ClickListenerInterface {

         void doConfirm();

         void doCancel();

    }

    /**
     * 单例方法
     *
     * @param context
     * @return
     */
    public static Wifi_ConfirmDialog getInstance(Context context,int type,String input) {
        if (dialog == null) {
            synchronized (Wifi_ConfirmDialog.class) {
                Log.d("TAG", "getInstance: 创建新的dialog");
                dialog = new Wifi_ConfirmDialog(context,type,input);
            }
        }
        return dialog;
    }

    private Wifi_ConfirmDialog(Context context,int type,String input) {
        super(context, R.style.dialog);
        this.context = context;
        this.type = type;
        this.input = input;
        init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    private boolean isShow = false;


    public void showDialog( int uiMode){
        Log.d("TAG", "showDialog1: "+uiMode );
        if(context != null){
            if(!((Activity) context).isFinishing())
            {
                //show dialog
                setUimod(uiMode);
                show();
                Log.d("TAG", "showDialog1: activity存在 ");
            }
        }

    }
    public void dismissDialog(){
        Log.d("TAG", "dismissDialog: ");
        dismiss();
        if(dialog !=null){
            Log.d("TAG", "dismissDialog:dialog = null " );
            dialog = null;
        }
    }

    public void setUimod(int uiMode){
        // 黑夜模式 和中英文切换
        Log.d("TAG", "setUimod: uiMode=="+uiMode+"--type="+type );
//        if(uiMode != 19){
//            alert_wifi_dialog.setBackgroundResource(R.drawable.wifi_popup_bg1);
//        }else {
//            alert_wifi_dialog.setBackgroundResource(R.drawable.wifi_popup_bg);
//        }
//        alert_wifi_edit.setBackgroundColor(context.getResources().getColor(R.color.edit_color));
        alert_wifi_psw.setBackground(context.getResources().getDrawable(R.drawable.shape_wifi_dialog));
        alert_wifi_name.setTextColor(context.getResources().getColor(R.color.dialog_color));
        alert_wifi_cancel.setTextColor(context.getResources().getColor(R.color.dialog_color));
        alert_wifi_psw.setTextColor(context.getResources().getColor(R.color.black_color));
        alert_wifi_tip1.setTextColor(context.getResources().getColor(R.color.unselect_color));
        alert_wifi_name1.setTextColor(context.getResources().getColor(R.color.black_color));
        alert_wifi_psw.setHintTextColor(context.getResources().getColor(R.color.hint_color));
//        alert_wifi_disconnect.setTextColor(context.getResources().getColor(R.color.hint_color));
        alert_wifi_cancel.setText(context.getResources().getString(R.string.string5));

        if(type == 0){
            alert_wifi_dialog.setBackgroundResource(R.drawable.popup_bg3);
            alert_wifi_disconnect.setText(context.getResources().getString(R.string.string4));
            alert_wifi_disconnect1.setText(context.getResources().getString(R.string.string4));
            alert_wifi_psw.setHint(context.getResources().getString(R.string.string3));
        }else if(type == 1){
            alert_wifi_dialog.setBackgroundResource(R.drawable.popup_bg3);
            alert_wifi_name.setText(context.getResources().getString(R.string.string15));
            alert_wifi_psw.setText(input);
            alert_wifi_disconnect.setText(context.getResources().getString(R.string.string18));
            alert_wifi_disconnect1.setText(context.getResources().getString(R.string.string18));
        }else if(type == 2){
            alert_wifi_dialog.setBackgroundResource(R.drawable.popup_bg3);
            alert_wifi_name.setText(context.getResources().getString(R.string.string17));
            alert_wifi_psw.setText(input);
            alert_wifi_disconnect.setText(context.getResources().getString(R.string.string18));
            alert_wifi_disconnect1.setText(context.getResources().getString(R.string.string18));
        }else if(type == 3){
//            ColorStateList colorStateList = new ColorStateList(states, colors);
            alert_wifi_dialog.setBackgroundResource(R.drawable.wifi_popup_bg);
//            alert_wifi_disconnect.setTextColor(colorStateList);
            alert_wifi_tip1.setText(context.getResources().getString(R.string.string10));
            alert_wifi_disconnect.setText(context.getResources().getString(R.string.string11));
            alert_wifi_disconnect1.setText(context.getResources().getString(R.string.string11));
        }
    }


    public  void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.wifi_alertdialog_layout, null);
        setContentView(view);
        alert_wifi_cancel = view.findViewById(R.id.alert_wifi_cancel);
        alert_wifi_dialog = view.findViewById(R.id.alert_wifi_dialog);
        alert_wifi_edit = view.findViewById(R.id.alert_wifi_edit);
        alert_wifi_disconnect = view.findViewById(R.id.alert_wifi_disconnect);
        alert_wifi_disconnect1 = view.findViewById(R.id.alert_wifi_disconnect1);
        alert_wifi_name = view.findViewById(R.id.alert_wifi_name);
        alert_wifi_tip = view.findViewById(R.id.alert_wifi_tip);
        alert_wifi_display = view.findViewById(R.id.alert_wifi_display);
        alert_wifi_psw = view.findViewById(R.id.alert_wifi_psw);
        alert_wifi_disconnect.setOnClickListener(new clickListener());
        alert_wifi_cancel.setOnClickListener(new clickListener());
        alert_wifi_model1 = view.findViewById(R.id.alert_wifi_model1);
        alert_wifi_model2 = view.findViewById(R.id.alert_wifi_model2);
        alert_wifi_name1 = view.findViewById(R.id.alert_wifi_name1);
        alert_wifi_tip1 = view.findViewById(R.id.alert_wifi_tip1);
        if(type == 3){
            alert_wifi_model1.setVisibility(View.VISIBLE);
            alert_wifi_model2.setVisibility(View.GONE);
            alert_wifi_disconnect.setText(context.getResources().getString(R.string.string11));
            alert_wifi_disconnect1.setVisibility(View.GONE);
            alert_wifi_disconnect.setVisibility(View.VISIBLE);
//            alert_wifi_disconnect1.setText(context.getResources().getString(R.string.string11));
        }else {
            alert_wifi_model1.setVisibility(View.GONE);
            alert_wifi_model2.setVisibility(View.VISIBLE);
        }
        initByType();


        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = 640;
        lp.height = 330;
        //-250
        lp.x = 360;
//        lp.y = 50;
        lp.gravity = Gravity.CENTER_VERTICAL | Gravity.START;
        dialogWindow.setAttributes(lp);
    }

    private void initByType() {
        Log.d("TAG", "initByType: "+type );
        if(type == 0){
            //连接wifi
            alert_wifi_display.setVisibility(View.VISIBLE);
            alert_wifi_display.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isShow){
                        Log.d("TAG", "onClick: 影藏密码" );
                        alert_wifi_display.setImageResource(R.drawable.ic_hide);//隐藏图片
                        alert_wifi_psw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }else {
                        Log.d("TAG", "onClick: 显示密码" );
                        alert_wifi_display.setImageResource(R.drawable.ic_display);//显式图片
                        alert_wifi_psw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }
                    alert_wifi_psw.setSelection(alert_wifi_psw.getText().length());
                    isShow = !isShow;
                }
            });
            alert_wifi_psw.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (alert_wifi_psw.getText().toString().length() >= 8) {
//                        alert_wifi_disconnect.setClickable(true);
                        alert_wifi_disconnect1.setVisibility(View.GONE);
                        alert_wifi_disconnect.setVisibility(View.VISIBLE);
//                        ColorStateList colorStateList = new ColorStateList(states, colors);
//                        alert_wifi_disconnect.setTextColor(colorStateList);
                    } else {
//                        alert_wifi_disconnect.setClickable(false);

                        alert_wifi_disconnect.setVisibility(View.GONE);
                        alert_wifi_disconnect1.setVisibility(View.VISIBLE);
//                        alert_wifi_disconnect.setTextColor(context.getResources().getColor(R.color.hint_color));
                    }
                }
            });
            alert_wifi_psw.setHint(context.getResources().getString(R.string.string3));
            alert_wifi_disconnect.setText(context.getResources().getString(R.string.string4));
            alert_wifi_disconnect1.setText(context.getResources().getString(R.string.string4));
            //置灰
//            alert_wifi_disconnect.setTextColor(context.getResources().getColor(R.color.hint_color));
        }else if(type == 1){
            //修改热点名称
            alert_wifi_display.setVisibility(View.GONE);
            alert_wifi_psw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            alert_wifi_name.setText(context.getResources().getString(R.string.string15));
            alert_wifi_disconnect.setText(context.getResources().getString(R.string.string18));
            alert_wifi_disconnect1.setText(context.getResources().getString(R.string.string18));
//            alert_wifi_disconnect.setTextColor(context.getResources().getColor(R.color.dialog_color));
            alert_wifi_psw.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (alert_wifi_psw.getText().toString().length() > 0) {

                        alert_wifi_disconnect1.setVisibility(View.GONE);
                        alert_wifi_disconnect.setVisibility(View.VISIBLE);
//                        alert_wifi_disconnect.setClickable(true);
//                        ColorStateList colorStateList = new ColorStateList(states, colors);
//                        alert_wifi_disconnect.setTextColor(colorStateList);
                    } else {
//                        alert_wifi_disconnect.setClickable(false);
//                        alert_wifi_disconnect.setTextColor(context.getResources().getColor(R.color.hint_color));
                        alert_wifi_disconnect.setVisibility(View.GONE);
                        alert_wifi_disconnect1.setVisibility(View.VISIBLE);
                    }
                }
            });
        }else if(type == 2){
            // 修改热点密码
            alert_wifi_display.setVisibility(View.GONE);
            alert_wifi_psw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            alert_wifi_name.setText(context.getResources().getString(R.string.string17));
            alert_wifi_disconnect.setText(context.getResources().getString(R.string.string18));
            alert_wifi_disconnect1.setText(context.getResources().getString(R.string.string18));
            //置灰
//            alert_wifi_disconnect.setTextColor(context.getResources().getColor(R.color.hint_color));
            alert_wifi_psw.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (alert_wifi_psw.getText().toString().length() >= 8) {
                        alert_wifi_disconnect1.setVisibility(View.GONE);
                        alert_wifi_disconnect.setVisibility(View.VISIBLE);
//                        alert_wifi_disconnect.setClickable(true);
//                        ColorStateList colorStateList = new ColorStateList(states, colors);
//                        alert_wifi_disconnect.setTextColor(colorStateList);
                    } else {
                        alert_wifi_disconnect1.setVisibility(View.VISIBLE);
                        alert_wifi_disconnect.setVisibility(View.GONE);
//                        alert_wifi_disconnect.setClickable(false);
//                        alert_wifi_disconnect.setTextColor(context.getResources().getColor(R.color.hint_color));
                    }
                }
            });
        }
    }


    private class AsteriskPasswordTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new PasswordCharSequence(source);
        }

        private class PasswordCharSequence implements CharSequence {
            private CharSequence mSource;

            public PasswordCharSequence(CharSequence source) {
                mSource = source; // Store char sequence
            }

            public char charAt(int index) {
        /*
        当在编辑框中输入1的时候，会连续打印0...
        当在编辑框中继续输入2的时候，会连续01...
        不影响功能使用，但是出现原因不知，待解决
         */
                System.out.println("-----" + index + "-----");
                //这里返回的char，就是密码的样式，注意，是char类型的
                return '$'; // This is the important part
            }

            public int length() {
                return mSource.length(); // Return default
            }

            public CharSequence subSequence(int start, int end) {
                return mSource.subSequence(start, end); // Return default
            }
        }
    }
    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    private class clickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            int id = v.getId();
            if (id == R.id.alert_wifi_disconnect) {
                clickListenerInterface.doConfirm();

            } else if (id == R.id.alert_wifi_cancel) {
                clickListenerInterface.doCancel();

            }
        }

    };

}