package com.winning.mars_security.core.module.lockscreen;

import android.os.Bundle;

import com.winning.mars_security.core.DirectiveManager;
import com.winning.mars_security.core.module.BaseAction;
import com.winning.mars_security.util.Constants;
import com.winning.mars_security.util.PermisionUtils;

import winning.com.mars_annotation.Action;

@Action(value = Constants.ACTION_LOCK_SCREEN)
public class ResetPasswordAction implements BaseAction<Bundle> {
    @Override
    public void doAction(Bundle bundle) {
        if (PermisionUtils.verifyLockScreenPermissions(DirectiveManager.mContext)){
            PermisionUtils.LockScreenNow();
        }
    }
}
