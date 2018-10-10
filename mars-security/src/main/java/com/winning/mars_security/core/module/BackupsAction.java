package com.winning.mars_security.core.module;

import android.util.Log;

import com.winning.mars_security.util.Constants;

import winning.com.mars_annotation.Action;

/**
 * 备份action
 */
@Action(value = Constants.ACTION_BACKUP)
public class BackupsAction implements BaseAction{
    @Override
    public void doAction() {
        Log.e("action", "备份数据");
    }
}
