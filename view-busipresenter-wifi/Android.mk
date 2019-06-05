LOCAL_PATH:= $(call my-dir)

### BUILD Settings view-busipresenter
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

LOCAL_STATIC_JAVA_LIBRARIES := settings-funcpresenter-model
LOCAL_STATIC_JAVA_LIBRARIES += android-support-annotations
LOCAL_STATIC_JAVA_LIBRARIES += android-support-v4
LOCAL_STATIC_JAVA_LIBRARIES += android-support-v7-appcompat
LOCAL_STATIC_JAVA_LIBRARIES += android-support-v7-recyclerview

#引用声明aar变量
#LOCAL_STATIC_JAVA_AAR_LIBRARIES := BaseRecyclerViewAdapterHelper
#LOCAL_STATIC_JAVA_AAR_LIBRARIES += PickerCommon
#LOCAL_STATIC_JAVA_AAR_LIBRARIES += PickerWheelPicker
#LOCAL_STATIC_JAVA_AAR_LIBRARIES += kyleduo

LOCAL_SRC_FILES := $(call all-java-files-under, src/main/java)
LOCAL_SRC_FILES += $(call all-Iaidl-files-under, src/main)
LOCAL_SRC_FILES += $(call all-java-files-under, ../CommonUI/src/main/java)

LOCAL_MANIFEST_FILE := src/main/AndroidManifest.xml
LOCAL_RESOURCE_DIR := $(LOCAL_PATH)/src/main/res
LOCAL_RESOURCE_DIR += prebuilts/sdk/current/support/v7/appcompat/res
LOCAL_RESOURCE_DIR += prebuilts/sdk/current/support/v7/recyclerview/res
LOCAL_RESOURCE_DIR += adayo/Application/CommonUI/src/main/res
LOCAL_PACKAGE_NAME := AdayoSettings

LOCAL_AAPT_FLAGS := --auto-add-overlay
LOCAL_AAPT_FLAGS += --extra-packages android.support.v7.appcompat
LOCAL_AAPT_FLAGS += --extra-packages android.support.v7.recyclerview
LOCAL_AAPT_FLAGS += --extra-packages com.adayo.commonui
#LOCAL_AAPT_FLAGS += --extra-packages com.kyleduo.switchbutton
#LOCAL_AAPT_FLAGS += --extra-packages cn.qqtheme.framework
#LOCAL_AAPT_FLAGS += --extra-packages com.chad.library

LOCAL_CERTIFICATE := platform
#LOCAL_SDK_VERSION := current
LOCAL_PROGUARD_ENABLED := disabled
#LOCAL_PROGUARD_FLAG_FILES := proguard.cfg
 
include $(BUILD_PACKAGE)

### BUILD prebuilt jar/aar
include $(CLEAR_VARS)

#LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += xxx:libs/xxx.jar

#LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES := BaseRecyclerViewAdapterHelper:libs/BaseRecyclerViewAdapterHelper-2.9.40.aar
#LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += PickerCommon:libs/PickerCommon-1.5.6.aar
#LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += PickerWheelPicker:libs/PickerWheelPicker-1.5.6.aar
#LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES += kyleduo:libs/kyleduo-2.0.0.aar

include $(BUILD_MULTI_PREBUILT)

### Copy Config files from [assets] to [etc]
#include $(CLEAR_VARS)

#LOCAL_MODULE_TAGS := optional

#LOCAL_MODULE := AdayoSettingsConfig.json
#LOCAL_MODULE_CLASS := ETC
#LOCAL_MODULE_PATH := $(PRODUCT_OUT)/system/etc/adayo/Settings
#LOCAL_SRC_FILES := src/main/assets/$(LOCAL_MODULE)

#include $(BUILD_PREBUILT)
