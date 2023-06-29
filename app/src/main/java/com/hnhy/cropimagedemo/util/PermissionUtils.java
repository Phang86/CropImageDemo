package com.hnhy.cropimagedemo.util;

import android.content.pm.PackageManager;
import android.os.Build;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.Map;

public class PermissionUtils {

    /**
     * 动态申请权限
     * @param permissions 权限组
     * @return
     */
    public static boolean addPermission(FragmentActivity activity, String[] permissions, ActivityResultLauncher<String[]> resultLauncher){
        boolean result = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissionList = new ArrayList<>();
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(activity,permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(permission);
                }
            }
            if (!permissionList.isEmpty()) {
                result = false;
                String[] permissionNew = permissionList.toArray(new String[0]);
                resultLauncher.launch(permissionNew);
            }
        }
        return result;
    }

    /**
     * 申请权限后回调判断用户是否同意权限
     * @param result 返回内容
     * @param permission 要判断的权限
     */
    public static boolean requestPermissionResult(Map<String,Boolean> result, String permission){
        Boolean granted = result.get(permission);
        return granted != null && granted;
    }

}
