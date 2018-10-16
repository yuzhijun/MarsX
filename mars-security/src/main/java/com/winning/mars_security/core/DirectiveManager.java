package com.winning.mars_security.core;

import android.app.Activity;
import android.content.Context;

import com.winning.mars_security.core.strategy.mail.MailWorker;
import com.winning.mars_security.util.PermisionUtils;

import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class DirectiveManager {
    private static DirectiveManager mInstance;
    private static Context mContext;
    private static String mAppkey;
    private DirectiveManager(Context context,String appkey){
        mContext = context;
        mAppkey = appkey;
        PermisionUtils.verifySmsPermissions((Activity) context);
        PermisionUtils.verifyDeviceAdminPermissions(context);

        ActionData.init();
        initMailWorker();
    }
    public static DirectiveManager getInstance(Context context,String appkey){
        if (mInstance == null){
            synchronized (DirectiveManager.class){
                if (mInstance == null){
                    mInstance = new DirectiveManager(context,appkey);
                }
            }
        }
        return mInstance;
    }
    private void initMailWorker(){
        Constraints jobConstraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .setRequiresCharging(false)
                .build();

        PeriodicWorkRequest jobWorkManager =
                new PeriodicWorkRequest.Builder(MailWorker.class, PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS,
                        TimeUnit.MILLISECONDS)
                        .setConstraints(jobConstraints)
                        .build();

        WorkManager.getInstance().enqueue(jobWorkManager);
    }

    public static Context getContext(){
        return mContext;
    }

    public static String getAppkey() {
        return mAppkey;
    }

}
