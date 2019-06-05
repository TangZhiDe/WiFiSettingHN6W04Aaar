/**
 * Copyright (c) 2015 FORYOU GENERAL ELECTRONICS CO.,LTD. All Rights Reserved.
 */
package com.adayo.app.settings.constant;

import com.adayo.proxy.audio.constants.AudioDspConstantsDef;

/**
 * @author damanz
 * @className Constant
 * @date 2018-07-26.
 */
public final class Constant {

    private Constant() {
    }

    //----------------------------Fragment类型参数定义(1-100)预留100字段--------------------------------------
    //显示
    public static final int DISPLAY_FRAGMENT_ID = 0x11;
    //时间
    public static final int TIME_FRAGMENT_ID = 0x12;
    //声音
    public static final int VOLUME_FRAGMENT_ID = 0x13;
    //蓝牙
    public static final int BLUETOOTH_FRAGMENT_ID = 0x14;
    //网络
    public static final int NETWORK_FRAGMENT_ID = 0x15;
    //音效
    public static final int SOUND_EFFECT_FRAGMENT_ID = 0x16;
    //均衡
    public static final int EQ_FRAGMENT_ID = 0x17;
    //通用
    public static final int GENERAL_FRAGMENT_ID = 0x18;

    //驾驶辅助
    public static final int DRIVING_ASSISTANCE_FRAGMENT_ID = 0x19;
    //碰撞预警
    public static final int COLLISION_WARNING_FRAGMENT_ID = 0x20;
    //车道辅助
    public static final int LINE_ASSISTANCE_FRAGMENT = 0x21;
    //车身控制
    public static final int BODY_CONTROL_FRAGMENT_ID = 0x22;
    //HUD
    public static final int HUD_FRAGMENT_ID = 0x23;
    //灯光调节
    public static final int LIGHT_ADJUSTMENT_FRAGMENT_ID = 0x24;


    //----------------------------Dialog弹窗类型参数定义(101-200)预留100字段--------------------------------------
    //行车观看视频
    public static final int DIALOG_TYPE_TRAFFIC_WATCH_VIDEO = 0x101;
    //恢复出厂设置
    public static final int DIALOG_TYPE_RESTORE_FACTORY_SETTINGS = 0x102;


    //----------------------------自定义弹窗类型参数定义(201-300)预留100字段--------------------------------------
    /**
     * 导航混音播报
     */
    public static final int CUSTOM_POPWINDOWS_TYPE_NAIGATION_BROADCAST = 0x201;
    /**
     * 速度补偿音
     */
    public static final int CUSTOM_POPWINDOWS_TYPE_SPEED_COM_VOLUME = 0x202;
    /**
     * 系统语言
     */
    public static final int CUSTOM_POPWINDOWS_TYPE_SYSTEM_LANGUAGE = 0x203;
    /**
     * 待机显示
     */
    public static final int CUSTOM_POPWINDOWS_TYPE_STANDBY_DISPLAY = 0x204;
    /**
     * 时间制式
     */
    public static final int CUSTOM_POPWINDOWS_TYPE_TIME_DISPLAY = 0x205;
    /**
     * 时区
     */
    public static final int CUSTOM_POPWINDOWS_TYPE_TIME_ZONE = 0x206;
    /**
     * 日期制式
     */
    public static final int CUSTOM_POPWINDOWS_TYPE_DATE_DISPLAY = 0x207;
    /**
     * 音效模式
     */
    public static final int CUSTOM_POPWINDOWS_TYPE_SOUND_EFFECT_PATTERN = 0x208;
    /**
     * 亮度调节
     */
    public static final int CUSTOM_POPWINDOWS_TYPE_BRIGHTNESS_CONTROL = 0x209;
    /**
     * iphone启动应用
     */
    public static final int CUSTOM_POPWINDOWS_TYPE_IPHONE_START_APP = 0x210;
    /**
     * Android启动应用
     */
    public static final int CUSTOM_POPWINDOWS_TYPE_ANDROID_START_APP = 0x212;


    public static final String OPEN_FONT_PROTOCAL =
            "思源黑体是Adobe和Google所领导开发的开源字型之一，其遵循SIL 开源字体授权。\n" +
                    "\n" +
                    "免责声明详情：\n" +
                    "Copyright (c) <dates>, <Copyright Holder> (<URL|email>),\n" +
                    "with Reserved Font Name <Reserved Font Name>.\n" +
                    "Copyright (c) <dates>, <additional Copyright Holder> (<URL|email>),\n" +
                    "with Reserved Font Name <additional Reserved Font Name>.\n" +
                    "Copyright (c) <dates>, <additional Copyright Holder> (<URL|email>).\n" +
                    "\n" +
                    "This Font Software is licensed under the SIL Open Font License, Version 1.1.\n" +
                    "This license is copied below, and is also available with a FAQ at:\n" +
                    "http://scripts.sil.org/OFL\n" +
                    "\n" +
                    "-----------------------------------------------------------\n" +
                    "SIL OPEN FONT LICENSE Version 1.1 - 26 February 2007\n" +
                    "-----------------------------------------------------------\n" +
                    "\n" +
                    "PREAMBLE\n" +
                    "The goals of the Open Font License (OFL) are to stimulate worldwide\n" +
                    "development of collaborative font projects, to support the font creation\n" +
                    "efforts of academic and linguistic communities, and to provide a free and\n" +
                    "open framework in which fonts may be shared and improved in partnership\n" +
                    "with others.\n" +
                    "\n" +
                    "The OFL allows the licensed fonts to be used, studied, modified and\n" +
                    "redistributed freely as long as they are not sold by themselves. The\n" +
                    "fonts, including any derivative works, can be bundled, embedded, \n" +
                    "redistributed and/or sold with any software provided that any reserved\n" +
                    "names are not used by derivative works. The fonts and derivatives,\n" +
                    "however, cannot be released under any other type of license. The\n" +
                    "requirement for fonts to remain under this license does not apply\n" +
                    "to any document created using the fonts or their derivatives.\n" +
                    "\n" +
                    "DEFINITIONS\n" +
                    "\"Font Software\" refers to the set of files released by the Copyright\n" +
                    "Holder(s) under this license and clearly marked as such. This may\n" +
                    "include source files, build scripts and documentation.\n" +
                    "\n" +
                    "\"Reserved Font Name\" refers to any names specified as such after the\n" +
                    "copyright statement(s).\n" +
                    "\n" +
                    "\"Original Version\" refers to the collection of Font Software components as\n" +
                    "distributed by the Copyright Holder(s).\n" +
                    "\n" +
                    "\"Modified Version\" refers to any derivative made by adding to, deleting,\n" +
                    "or substituting -- in part or in whole -- any of the components of the\n" +
                    "Original Version, by changing formats or by porting the Font Software to a\n" +
                    "new environment.\n" +
                    "\n" +
                    "\"Author\" refers to any designer, engineer, programmer, technical\n" +
                    "writer or other person who contributed to the Font Software.\n" +
                    "\n" +
                    "PERMISSION & CONDITIONS\n" +
                    "Permission is hereby granted, free of charge, to any person obtaining\n" +
                    "a copy of the Font Software, to use, study, copy, merge, embed, modify,\n" +
                    "redistribute, and sell modified and unmodified copies of the Font\n" +
                    "Software, subject to the following conditions:\n" +
                    "\n" +
                    "1) Neither the Font Software nor any of its individual components,\n" +
                    "in Original or Modified Versions, may be sold by itself.\n" +
                    "\n" +
                    "2) Original or Modified Versions of the Font Software may be bundled,\n" +
                    "redistributed and/or sold with any software, provided that each copy\n" +
                    "contains the above copyright notice and this license. These can be\n" +
                    "included either as stand-alone text files, human-readable headers or\n" +
                    "in the appropriate machine-readable metadata fields within text or\n" +
                    "binary files as long as those fields can be easily viewed by the user.\n" +
                    "\n" +
                    "3) No Modified Version of the Font Software may use the Reserved Font\n" +
                    "Name(s) unless explicit written permission is granted by the corresponding\n" +
                    "Copyright Holder. This restriction only applies to the primary font name as\n" +
                    "presented to the users.\n" +
                    "\n" +
                    "4) The name(s) of the Copyright Holder(s) or the Author(s) of the Font\n" +
                    "Software shall not be used to promote, endorse or advertise any\n" +
                    "Modified Version, except to acknowledge the contribution(s) of the\n" +
                    "Copyright Holder(s) and the Author(s) or with their explicit written\n" +
                    "permission.\n" +
                    "\n" +
                    "5) The Font Software, modified or unmodified, in part or in whole,\n" +
                    "must be distributed entirely under this license, and must not be\n" +
                    "distributed under any other license. The requirement for fonts to\n" +
                    "remain under this license does not apply to any document created\n" +
                    "using the Font Software.\n" +
                    "\n" +
                    "TERMINATION\n" +
                    "This license becomes null and void if any of the above conditions are\n" +
                    "not met.\n" +
                    "\n" +
                    "DISCLAIMER\n" +
                    "THE FONT SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND,\n" +
                    "EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO ANY WARRANTIES OF\n" +
                    "MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT\n" +
                    "OF COPYRIGHT, PATENT, TRADEMARK, OR OTHER RIGHT. IN NO EVENT SHALL THE\n" +
                    "COPYRIGHT HOLDER BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,\n" +
                    "INCLUDING ANY GENERAL, SPECIAL, INDIRECT, INCIDENTAL, OR CONSEQUENTIAL\n" +
                    "DAMAGES, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING\n" +
                    "FROM, OUT OF THE USE OR INABILITY TO USE THE FONT SOFTWARE OR FROM\n" +
                    "OTHER DEALINGS IN THE FONT SOFTWARE.";

    //音效 标准 爵士 流行 摇滚 古典 打击 自定义
    public static final int[] SOUND_EFFECT_MODEL = {AudioDspConstantsDef.EQ_TYPE.flat.mIndex,
            AudioDspConstantsDef.EQ_TYPE.jazz.mIndex,
            AudioDspConstantsDef.EQ_TYPE.pop.mIndex,
            AudioDspConstantsDef.EQ_TYPE.rock.mIndex,
            AudioDspConstantsDef.EQ_TYPE.classic.mIndex,
            AudioDspConstantsDef.EQ_TYPE.user.mIndex};

}



