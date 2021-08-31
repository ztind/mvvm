package com.ztind.mvvm.common.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.TextUtils

object PermissionUtil {
    /**
     * **Title: **跳转到权限设置页面<br></br>
     * **Description: **这里写方法的详细描述
     */
    fun gotoPermission(context: Context,packageName:String) {
        val brand = Build.BRAND //手机厂商
        if (TextUtils.equals(brand.toLowerCase(), "redmi") || TextUtils.equals(
                brand.toLowerCase(),
                "xiaomi"
            )
        ) {
            gotoMiuiPermission(context) //小米
        } else if (TextUtils.equals(brand.toLowerCase(), "meizu")) {
            gotoMeizuPermission(context,packageName)
        } else if (TextUtils.equals(
                brand.toLowerCase(),
                "huawei"
            ) || TextUtils.equals(brand.toLowerCase(), "honor")
        ) {
            gotoHuaweiPermission(context)
        } else {
            context.startActivity(getAppDetailSettingIntent(context))
        }
    }

    /**
     * **Title: **跳转到miui的权限管理页面<br></br>
     * **Description: **这里写方法的详细描述
     */
    private fun gotoMiuiPermission(context: Context) {
        try { // MIUI 8
            val localIntent = Intent("miui.intent.action.APP_PERM_EDITOR")
            localIntent.setClassName(
                "com.miui.securitycenter",
                "com.miui.permcenter.permissions.PermissionsEditorActivity"
            )
            localIntent.putExtra("extra_pkgname", context.packageName)
            context.startActivity(localIntent)
        } catch (e: Exception) {
            try { // MIUI 5/6/7
                val localIntent = Intent("miui.intent.action.APP_PERM_EDITOR")
                localIntent.setClassName(
                    "com.miui.securitycenter",
                    "com.miui.permcenter.permissions.AppPermissionsEditorActivity"
                )
                localIntent.putExtra("extra_pkgname", context.packageName)
                context.startActivity(localIntent)
            } catch (e1: Exception) { // 否则跳转到应用详情
                context.startActivity(getAppDetailSettingIntent(context))
            }
        }
    }

    /**
     * **Title: **跳转到魅族的权限管理系统<br></br>
     * **Description: **这里写方法的详细描述
     */
    private fun gotoMeizuPermission(context: Context,packageName:String) {
        try {
            val intent = Intent("com.meizu.safe.security.SHOW_APPSEC")
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.putExtra("packageName", packageName)
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            context.startActivity(getAppDetailSettingIntent(context))
        }
    }

    /**
     * **Title: **华为的权限管理页面<br></br>
     * **Description: **这里写方法的详细描述
     */
    private fun gotoHuaweiPermission(context: Context) {
        try {
            val intent = Intent()
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            val comp = ComponentName(
                "com.huawei.systemmanager",
                "com.huawei.permissionmanager.ui.MainActivity"
            ) //华为权限管理
            intent.component = comp
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            context.startActivity(getAppDetailSettingIntent(context))
        }
    }

    /**
     * **Title: **获取应用详情页面intent（如果找不到要跳转的界面，也可以先把用户引导到系统设置页面）<br></br>
     * **Description: **这里写方法的详细描述
     */
    private fun getAppDetailSettingIntent(context: Context): Intent {
        val localIntent = Intent()
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        localIntent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
        localIntent.data = Uri.fromParts("package", context.packageName, null)
        return localIntent
    }
}
