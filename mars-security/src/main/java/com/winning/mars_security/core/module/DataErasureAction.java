package com.winning.mars_security.core.module;

import com.winning.mars_security.core.DirectiveManager;
import com.winning.mars_security.util.Constants;
import com.winning.mars_security.util.DataCleanUtils;

import winning.com.mars_annotation.Action;

/**
 * 数据擦除action
 * @author shakchao
 * value
 */
@Action(value = Constants.ACTION_DATA_ERASURE)
public class DataErasureAction implements BaseAction{
    @Override
    public void doAction(String value) {
        DataCleanUtils.cleanApplicationData(DirectiveManager.getContext());
    }
}
