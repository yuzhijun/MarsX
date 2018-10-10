package com.winning.mars_security.core.strategy.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.text.TextUtils;

import com.winning.mars_security.core.ActionData;
import com.winning.mars_security.core.module.BaseAction;

public class SmsReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        //获取短信内容
        Object[] objs=(Object[]) intent.getExtras().get("pdus");
        for(Object obj: objs) {
            SmsMessage sms = SmsMessage.createFromPdu((byte[]) obj);
            String body = sms.getMessageBody();
            if (!TextUtils.isEmpty(body)){
                BaseAction action = (BaseAction) ActionData.map.get(body);
                if (null != action){
                    action.doAction();
                }
            }
            abortBroadcast();
        }
    }
}
