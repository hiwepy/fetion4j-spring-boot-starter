/*
 * Copyright (C) 2013, Apexes Network Technology. All rights reserved.
 *
 *       http://www.apexes.net
 *
 */
package net.apexes.fetion4j.demo;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import net.apexes.fetion4j.core.AuthFeedback;
import net.apexes.fetion4j.core.AuthSupportable;
import net.apexes.fetion4j.core.Captcha;

/**
 *
 * @author HeDYn <hedyn@foxmail.com>
 */
public class SimpleAuthSupport implements AuthSupportable {
    
    private CaptchaPanel captchaPanel = null;
    
    @Override
    public void needAuth(final Captcha captcha, final AuthFeedback feedback) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (captchaPanel == null) {
                    captchaPanel = new CaptchaPanel();
                }
                captchaPanel.set(captcha, feedback);
                int optionValue = JOptionPane.showOptionDialog(null,
                        captchaPanel, "验证",
                        javax.swing.JOptionPane.OK_CANCEL_OPTION,
                        javax.swing.JOptionPane.PLAIN_MESSAGE, null, null, null);
                if (optionValue == JOptionPane.OK_OPTION) {
                    captchaPanel.submit();
                } else {
                    captchaPanel.cancel();
                }
            }
        });
    }
}
