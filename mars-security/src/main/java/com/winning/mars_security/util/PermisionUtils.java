package com.winning.mars_security.util;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.winning.mars_security.core.module.MarsDeviceAdminReceiver;

import static android.app.admin.DevicePolicyManager.PASSWORD_QUALITY_UNSPECIFIED;
import static android.app.admin.DevicePolicyManager.WIPE_EXTERNAL_STORAGE;

/**
 * Created by Admin on 2018/3/12.
 */


public class PermisionUtils {

    private static String[] PERMISSIONS_SMS = {
            Manifest.permission.RECEIVE_SMS};
    // Storage Permissions
    private static final int REQUEST_RECEIVE_SMS = 1;
    private static final int REQUEST_LOCK_SCREEN = 2;
    private static DevicePolicyManager policyManager;
    private static ComponentName componentName;

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
                Manifest.permission.RECEIVE_SMS);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            showMessageDialog(REQUEST_RECEIVE_SMS,activity);
            return false;
        }
        return true;
    }

    public static boolean verifyDeviceAdminPermissions(Context context){
        policyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(context, MarsDeviceAdminReceiver.class);
        if (!policyManager.isAdminActive(componentName)){
            showLockScreenMessageDialog(context, componentName);
            return false;
        }else {
            return true;
        }
    }
    public static void  LockScreenNow(){
        if (policyManager != null){
            policyManager.lockNow();
        }
    }
    public static void wipeData(){
        if (policyManager != null){
            policyManager.wipeData(WIPE_EXTERNAL_STORAGE);
        }
    }
    /**
     //没有什么特殊规则，但字符串长度不能小于4
     public static final int PASSWORD_QUALITY_UNSPECIFIED = 0;
     //只能使用PIN码、密码与图案锁，其它不能使用
     public static final int PASSWORD_QUALITY_BIOMETRIC_WEAK = 32768;
     //只能使用PIN码、密码与图案锁，其它不能使用
     public static final int PASSWORD_QUALITY_SOMETHING = 65536;
     //只能使用PIN码与密码
     public static final int PASSWORD_QUALITY_NUMERIC = 131072;
     //只能使用PIN码与密码，并且PIN码禁止以顺序排列且禁止重复
     public static final int PASSWORD_QUALITY_NUMERIC_COMPLEX = 196608;
     //只能使用密码
     public static final int PASSWORD_QUALITY_ALPHABETIC = 262144;
     //只能使用密码，并且密码至少要包含一个数字
     public static final int PASSWORD_QUALITY_ALPHANUMERIC = 327680;
     //只能使用密码，并且密码至少要包含一个数字和一个特殊字符
     public static final int PASSWORD_QUALITY_COMPLEX = 393216;
     */
    public static void resetPassword(String password){
        if (policyManager != null){
            policyManager.setPasswordQuality(componentName,PASSWORD_QUALITY_UNSPECIFIED);
            policyManager.resetPassword(password, PASSWORD_QUALITY_UNSPECIFIED );
        }
    }


    public static void showLockScreenMessageDialog( final Context context,ComponentName componentName){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("权限");
        builder.setMessage("系统锁屏等需要获取设备管理器权限");
        builder.setPositiveButton("允许", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //激活设备管理器
                Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
                intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "锁屏");
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "您的软件将不能使用系统安全中绝大数功能!,请在[设置]-[授权管理]中打开", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();

    }
    public static void showMessageDialog(int code, final Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("权限");
        builder.setMessage("系统备份等需要获取读取短信权限");
        builder.setPositiveButton("允许", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(activity, PERMISSIONS_SMS,
                        REQUEST_RECEIVE_SMS);
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