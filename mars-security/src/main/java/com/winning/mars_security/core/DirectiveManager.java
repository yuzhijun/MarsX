package com.winning.mars_security.core;

import android.app.Activity;
import android.content.Context;

import com.winning.mars_security.core.strategy.mail.MailWorker;
import com.winning.mars_security.util.PermisionUtils;

import java.util.concurrent.TimeUnit;

import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class DirectiveManager {
    private static DirectiveManager mInstance;
    private DirectiveManager(Context context){
        PermisionUtils.verifySmsPermissions((Activity) context);
        ActionData.init();
        initMailWorker();
    }
    public static DirectiveManager getInstance(Context context){
        if (mInstance == null){
            synchronized (DirectiveManager.class){
                if (mInstance == null){
                    mInstance = new DirectiveManager(context);
                }
            }
        }
        return mInstance;
    }
    public void initMailWorker(){
        PeriodicWorkRequest mailWork = new PeriodicWorkRequest.Builder(MailWorker.class,1,
                TimeUnit.MINUTES).build();
        WorkManager.getInstance().enqueue(mailWork);
    }

}
