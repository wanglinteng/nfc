package wang.linteng.tool;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

public class Permission {

    // sd卡读写权限
    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    public static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    public static boolean verifyStoragePermissions(Activity activity) {

        //检测是否有写的权限
        int permission = ActivityCompat.checkSelfPermission(activity,
                "android.permission.WRITE_EXTERNAL_STORAGE");
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // 没有权限
            return false;
        } else {
            // 有权限
            return true;
        }
    }

    public static void grantedStoragePermissions(Activity activity){
        ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
    }
}
