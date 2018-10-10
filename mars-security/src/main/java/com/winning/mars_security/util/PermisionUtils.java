package com.winning.mars_security.util;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

/**
 * Created by Admin on 2018/3/12.
 */


public class PermisionUtils {

    private static String[] PERMISSIONS_SMS = {
            Manifest.permission.READ_SMS};
    // Storage Permissions
    private static final int REQUEST_READ_SMS = 1;

    /**
     * Checks if the app has permission to write to device storage
     * If the app does not has permission then the user will be prompted to
     * grant permissions
     *
     * @param activity
     */
    public static boolean verifySmsPermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.READ_SMS);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            showMessageDialog(REQUEST_READ_SMS,activity);
            return false;
        }
        return true;
    }
    public static void showMessageDialog(int code, final Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("权限");
        switch (code){
            case REQUEST_READ_SMS:
                builder.setMessage("系统备份等需要获取读取短信权限");
                break;
        }
        builder.setPositiveButton("允许", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(activity, PERMISSIONS_SMS,
                        REQUEST_READ_SMS);
            }
        });
        builder.setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(activity, "您的软件将不能使用系统安全中绝大数功能!,请在[设置]-[授权管理]中打开", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();

    }
}