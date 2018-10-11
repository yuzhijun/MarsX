package com.winning.mars_security.core.module;

import com.winning.mars_security.core.DirectiveManager;
import com.winning.mars_security.util.Constants;
import com.winning.mars_security.util.PermisionUtils;

import winning.com.mars_annotation.Action;
/**
 * 重置密码
 * @author sharkchao
 * password
 */
@Action(value = Constants.ACTION_RESET_PASSWORD)
public class ResetPasswordAction implements BaseAction{
    @Override
    public void doAction(String value) {
        if (PermisionUtils.verifyDeviceAdminPermissions(DirectiveManager.getContext())){
            if (value.length() >= 6){
                //使用自定义密码规则
                PermisionUtils.resetPassword(value);
                PermisionUtils.LockScreenNow();
            }
        }
    }
}
