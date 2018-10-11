package com.winning.mars_security.core.module.lockscreen;

import android.content.Context;

import com.winning.mars_security.core.module.BaseAction;
import com.winning.mars_security.util.Constants;
import com.winning.mars_security.util.PermisionUtils;

import winning.com.mars_annotation.Action;

@Action(value = Constants.ACTION_LOCK_SCREEN)
public class LockScreenAction implements BaseAction<Context> {
    @Override
    public void doAction(Context context) {
        if (PermisionUtils.verifyLockScreenPermissions(context)){
            PermisionUtils.LockScreenNow();
        }
    }
}
