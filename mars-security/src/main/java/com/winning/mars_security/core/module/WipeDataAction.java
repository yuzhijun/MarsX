package com.winning.mars_security.core.module;

import com.winning.mars_security.core.DirectiveManager;
import com.winning.mars_security.util.Constants;
import com.winning.mars_security.util.PermisionUtils;

import winning.com.mars_annotation.Action;

/**
 * @author sharkchao
 * 恢复出厂设置,双清存储数据（包括外置sd卡），wipeData后重启
 */
@Action(value = Constants.ACTION_WIPE_DATA)
public class WipeDataAction implements BaseAction {
    @Override
    public void doAction(String t) {
        if (PermisionUtils.verifyDeviceAdminPermissions(DirectiveManager.getContext())){
            PermisionUtils.wipeData();
        }
    }
}
