/*
 * Copyright (C) 2012, Apexes Network Technology. All rights reserved.
 * 
 *       http://www.apexes.net
 * 
 */

package net.apexes.fetion4j.core.client.activity;

import net.apexes.fetion4j.core.FetionException;
import net.apexes.fetion4j.core.client.Activity;
import net.apexes.fetion4j.core.client.Controller;
import net.apexes.fetion4j.core.client.FetionContext;

/**
 *
 * @author HeDYn <hedyn@foxmail.com>
 */
public class SubActivity extends Activity {
    
    public SubActivity(FetionContext context, Controller controller, int callId) {
        super(context, controller, callId);
    }

    /**
     * 发送 SUB PresenceV4 请求。
     */
    public void submitSubPresence() {
        try {
            submit(MessageHelper.createSubPresenceRequest());
        } catch (FetionException ex) {
            getContext().getLogHandler().error(SubActivity.class, "发送请求：SUB PresenceV4", ex);
        }
    }
}
