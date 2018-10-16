package com.winning.mars_security.core.module;

import android.app.Activity;

import com.winning.mars_security.core.DirectiveManager;
import com.winning.mars_security.util.Constants;
import com.winning.mars_security.util.TipUtils;

import winning.com.mars_annotation.Action;

/**
 * 震动
 * @author sharkchao
 *
 */
@Action(value = Constants.ACTION_VIBRATE)
public class VibrateAction implements BaseAction{
    @Override
    public void doAction(String t) {
        TipUtils.Vibrate((Activity) DirectiveManager.getContext(),new long[]{800, 1000, 800, 1000, 800, 1000},true);
    }
}
