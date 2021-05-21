package com.zt.mvvm.common.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

/**
 * <b>Title: </b>系统小工具<br>
 * <b>Description: </b>获取系统相关属性，如：IMEI
 *
 * @author 赵伟
 * <p>
 * 修订历史:
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2019-09-26&nbsp;&nbsp;&nbsp;&nbsp;赵伟&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public class MySystemUtil {

//    private static final String CLIENT_ID_PREFIX = "Android-";
    private static final String CLIENT_ID_PREFIX = "android";

    /**
     * <b>Title: </b>获取客户端ID<br>
     * <b>Description: </b>这里写方法的详细描述
     *
     * @param context 上下文
     * @return 客户端ID
     * @author 赵伟
     * <p>
     * 修订历史:
     * <ul>
     * <li>修改日期&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
     * <hr>
     * <li>20190926&nbsp;&nbsp;&nbsp;&nbsp;赵伟&nbsp;&nbsp;&nbsp;&nbsp;创建方法</li>
     * </ul>
     */
    public static String getClientId(Activity context) {
        return CLIENT_ID_PREFIX + getIMEI(context, 0);
    }

    /**
     * <b>Title: </b>获取IMEI<br>
     * <b>Description: </b>这里写方法的详细描述
     *
     * @param context 上下文
     * @param slotId  卡槽Id，它的值为 0、1；
     * @return IMEI
     * @author 赵伟
     * <p>
     * 修订历史:
     * <ul>
     * <li>修改日期&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
     * <hr>
     * <li>20190926&nbsp;&nbsp;&nbsp;&nbsp;赵伟&nbsp;&nbsp;&nbsp;&nbsp;创建方法</li>
     * </ul>
     */
    public static String getIMEI(Activity context, int slotId) {
        String deviceId = loadDeviceUUID(context);

        if (StringUtils.isNotBlank(deviceId)) {
            return deviceId;
        }

        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            if (manager != null && !isEmulator()) {//如果是谷歌模拟器直接传个假的
                Method method = manager.getClass().getMethod("getImei", int.class);
                deviceId = (String) method.invoke(manager, slotId);
            } else {
                deviceId = "00001898869530";
                Log.d("SystemUtil", deviceId);
            }
        } catch (Exception e) {
            Log.e("SystemUtil", "第一次获取IMEI发生错误", e);

            try {
                Method method = manager.getClass().getMethod("getDeviceId");
                try {
                    deviceId = (String) method.invoke(manager);
                } catch (Exception ex) {
                    Log.e("SystemUtil", "第三次获取IMEI发生错误", ex);
                }
            } catch (Exception ex) {
                Log.e("SystemUtil", "第二次获取IMEI发生错误", ex);
                deviceId = getDeviceUUID(context);
            }
        }

        if (StringUtils.isNotBlank(deviceId)) {
            saveDeviceUUID(context, deviceId);
        }

        return deviceId;
    }

    /**
     * <b>Title: </b>获取Android Id<br>
     * <b>Description: </b>这里写方法的详细描述
     *
     * @param context 上下文
     * @return Android ID
     * @author 赵伟
     * <p>
     * 修订历史:
     * <ul>
     * <li>修改日期&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
     * <hr>
     * <li>20190926&nbsp;&nbsp;&nbsp;&nbsp;赵伟&nbsp;&nbsp;&nbsp;&nbsp;创建方法</li>
     * </ul>
     */
    @SuppressLint("HardwareIds")
    private static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * <b>Title: </b>获取Build的部分信息<br>
     * <b>Description: </b>这里写方法的详细描述
     *
     * @return Build信息
     * @author 赵伟
     * <p>
     * 修订历史:
     * <ul>
     * <li>修改日期&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
     * <hr>
     * <li>20190926&nbsp;&nbsp;&nbsp;&nbsp;赵伟&nbsp;&nbsp;&nbsp;&nbsp;创建方法</li>
     * </ul>
     */
    private static String getBuildInfo() {
        //这里选用了几个不会随系统更新而改变的值
        StringBuffer buildSB = new StringBuffer();
        buildSB.append(Build.BRAND).append("/");
        buildSB.append(Build.PRODUCT).append("/");
        buildSB.append(Build.DEVICE).append("/");
        buildSB.append(Build.ID).append("/");
        buildSB.append(Build.VERSION.INCREMENTAL);
        return buildSB.toString();
    }

    /***
     * <b>Title: </b>最终方案，获取设备ID<br>
     * <b>Description: </b>这里写方法的详细描述
     *
     * @param context 上下文
     * @return 设备UUID
     * @author 赵伟
     * <p>
     * 修订历史:
     * <ul>
     * <li>修改日期&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
     * <hr>
     * <li>20190926&nbsp;&nbsp;&nbsp;&nbsp;赵伟&nbsp;&nbsp;&nbsp;&nbsp;创建方法</li>
     * </ul>
     */
    private static String getDeviceUUID(Context context) {
        return buildDeviceUUID(context);
    }

    /**
     * <b>Title: </b>生成设备UUID<br>
     * <b>Description: </b>这里写方法的详细描述
     *
     * @param context 上下文
     * @return 设备UUID
     * @author 赵伟
     * <p>
     * 修订历史:
     * <ul>
     * <li>修改日期&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
     * <hr>
     * <li>20190926&nbsp;&nbsp;&nbsp;&nbsp;赵伟&nbsp;&nbsp;&nbsp;&nbsp;创建方法</li>
     * </ul>
     */
    public static String buildDeviceUUID(Context context) {
        String androidId = getAndroidId(context);
        if (!"9774d56d682e549c".equals(androidId)) {
            Random random = new Random();
            androidId = Integer.toHexString(random.nextInt())
                    + Integer.toHexString(random.nextInt())
                    + Integer.toHexString(random.nextInt());
        }
        return new UUID(androidId.hashCode(), getBuildInfo().hashCode()).toString();
    }

    /**
     * <b>Title: </b>保存设备UUID<br>
     * <b>Description: </b>这里写方法的详细描述
     *
     * @param context 上下文
     * @param uuid    UUID
     * @author 赵伟
     * <p>
     * 修订历史:
     * <ul>
     * <li>修改日期&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
     * <hr>
     * <li>20190926&nbsp;&nbsp;&nbsp;&nbsp;赵伟&nbsp;&nbsp;&nbsp;&nbsp;创建方法</li>
     * </ul>
     */
    private static void saveDeviceUUID(Context context, String uuid) {
        context.getSharedPreferences("device_uuid", Context.MODE_PRIVATE)
                .edit()
                .putString("uuid", uuid)
                .apply();
    }

    /**
     * <b>Title: </b>读取设备UUID<br>
     * <b>Description: </b>这里写方法的详细描述
     *
     * @param context 上下文
     * @return 设备ID
     * @author 赵伟
     * <p>
     * 修订历史:
     * <ul>
     * <li>修改日期&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
     * <hr>
     * <li>20190926&nbsp;&nbsp;&nbsp;&nbsp;赵伟&nbsp;&nbsp;&nbsp;&nbsp;创建方法</li>
     * </ul>
     */
    @Nullable
    public static String loadDeviceUUID(Context context) {
        return context.getSharedPreferences("device_uuid", Context.MODE_PRIVATE)
                .getString("uuid", null);
    }

    /**
     * <b>Title: </b>判断是否是模拟器<br>
     * <b>Description: </b>这里写方法的详细描述
     *
     * @return {@link boolean} true为是模拟器
     * @author 赵伟
     * <p>
     * 修订历史:
     * <ul>
     * <li>修改日期&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
     * <hr>
     * <li>20190929&nbsp;&nbsp;&nbsp;&nbsp;赵伟&nbsp;&nbsp;&nbsp;&nbsp;创建方法</li>
     * </ul>
     */
    private static boolean isEmulator() {
        return (Build.MODEL.equals("sdk")) || (Build.MODEL.equals("google_sdk"));
    }

    /**
     * <b>Title: </b>判断服务是否启动,context上下文对象 ，className服务的name<br>
     * <b>Description: </b>这里写方法的详细描述
     *
     * @param mContext  上下文
     * @param className 类名
     * @return {@link boolean} 服务是否在运行
     * @author 赵伟
     * <p>
     * 修订历史:
     * <ul>
     * <li>修改日期&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
     * <hr>
     * <li>20190929&nbsp;&nbsp;&nbsp;&nbsp;赵伟&nbsp;&nbsp;&nbsp;&nbsp;创建方法</li>
     * </ul>
     */
    public static boolean isServiceRunning(Context mContext, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);

        if (activityManager != null) {
            List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                    .getRunningServices(Integer.MAX_VALUE);

            if (!(serviceList.size() > 0)) {
                return false;
            }
            Log.e("OnlineService：", className);
            for (int i = 0; i < serviceList.size(); i++) {
                Log.e("serviceName：", serviceList.get(i).service.getClassName());
                if (serviceList.get(i).service.getClassName().contains(className)) {
                    isRunning = true;
                    break;
                }
            }

        }
        return isRunning;
    }


    /**
     * <b>Title: </b>这里写方法的主要功能<br>
     * <b>Description: </b>这里写方法的详细描述
     *
     * @param
     * @return {@link }
     * @author 李树华
     * <p>
     * 修订历史:
     * <ul>
     * <li>修改日期&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
     * <hr>
     * <li>20191021&nbsp;&nbsp;&nbsp;&nbsp;李树华&nbsp;&nbsp;&nbsp;&nbsp;创建方法</li>
     * </ul>
     */
    public static int getStateBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

}
