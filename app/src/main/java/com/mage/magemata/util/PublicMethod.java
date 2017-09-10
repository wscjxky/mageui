package com.mage.magemata.util;

import android.Manifest;
import android.app.Activity;

import com.yanzhenjie.permission.AndPermission;

/**
 * Created by Administrator on 2017/9/7.
 */

public  class PublicMethod {
    public static void requestPermission(Activity activity) {
        if (!AndPermission.hasPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            AndPermission.with(activity)
                    .requestCode(100)
                    .permission(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                            Manifest.permission.MANAGE_DOCUMENTS,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA,
                            Manifest.permission.INTERNET)
                    .send();
        }
    }
}
