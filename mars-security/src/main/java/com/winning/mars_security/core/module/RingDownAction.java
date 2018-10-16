package com.winning.mars_security.core.module;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import com.winning.mars_security.core.DirectiveManager;
import com.winning.mars_security.util.Constants;

import winning.com.mars_annotation.Action;

/**
 * 响铃(系统铃音)
 * @author sharkchao
 *
 */
@Action(value = Constants.ACTION_RING_DOWN)
public class RingDownAction implements BaseAction{
    @Override
    public void doAction(String t) {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone rt = RingtoneManager.getRingtone(DirectiveManager.getContext(), uri);
        rt.play();
    }
}
