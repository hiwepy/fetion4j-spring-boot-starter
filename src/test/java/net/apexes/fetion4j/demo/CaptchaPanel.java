/*
 * Copyright (C) 2013, Apexes Network Technology. All rights reserved.
 *
 *       http://www.apexes.net
 *
 */
package net.apexes.fetion4j.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.apexes.fetion4j.core.AuthFeedback;
import net.apexes.fetion4j.core.Captcha;

/**
 *
 * @author HeDYn <hedyn@foxmail.com>
 */
public class CaptchaPanel extends JPanel {

    private JLabel hintLabel;
    private JLabel imageLabel;
    private JTextField captchaCodeField;
    private JLabel errorLabel;
    private AuthFeedback feedback;

    public CaptchaPanel() {
        hintLabel = new JLabel();
        imageLabel = new JLabel();
        imageLabel.setToolTipText("点击换一个");
        imageLabel.setOpaque(true);
        imageLabel.setBackground(Color.white);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.green));
        imageLabel.setPreferredSize(new Dimension(100, 40));
        captchaCodeField = new JTextField(4);
        captchaCodeField.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        errorLabel = new JLabel("验证失败！");
        errorLabel.setForeground(Color.red);

        JPanel inputPanel = new JPanel(new BorderLayout(3, 0));
        inputPanel.add(new JLabel("验证码："), BorderLayout.WEST);
        inputPanel.add(captchaCodeField, BorderLayout.CENTER);
        inputPanel.add(errorLabel, BorderLayout.EAST);

        setLayout(new BorderLayout(0, 3));
        add(hintLabel, BorderLayout.NORTH);
        add(imageLabel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    captchaCodeField.setText("");
                    try {
                        Captcha captcha = feedback.tryAgain();
                        ImageIcon image = new ImageIcon(captcha.getImageData());
                        String verifyReason = captcha.getText();
                        int failCount = captcha.getFailCount();
                        set(image, verifyReason, failCount != 0);
                    } catch (IOException ex) {
                    }
                }
            }
        });
    }

    public void set(Captcha captcha, AuthFeedback feedback) {
        this.feedback = feedback;
        ImageIcon image = new ImageIcon(captcha.getImageData());
        String verifyReason = captcha.getText();
        int failCount = captcha.getFailCount();
        set(image, verifyReason, failCount != 0);
    }

    public void set(ImageIcon image, String verifyReason) {
        set(image, verifyReason, false);
    }

    public void set(ImageIcon image, String verifyReason, boolean verifyError) {
        String hint = String.format(
                "<html><body style='width:250' align='left'>"
                + "<font color='blue'>%s</font>"
                + "</html>", verifyReason);
        hintLabel.setText(hint);
        imageLabel.setIcon(image);
        errorLabel.setVisible(verifyError);
    }

    public void cancel() {
        feedback.cancel();
    }

    public void submit() {
        String code = captchaCodeField.getText();
        feedback.submit(code);
    }

    public static void main(String[] args) throws Exception {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    javax.swing.JFrame.setDefaultLookAndFeelDecorated(true);
                    javax.swing.JDialog.setDefaultLookAndFeelDecorated(true);
                    javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                    //javax.swing.UIManager.setLookAndFeel("com.lipstikLF.LipstikLookAndFeel");
                } catch (Exception e) {
                    System.out.println("使用默认样式");
                }
                ImageIcon image = new ImageIcon("verify.png");
                CaptchaPanel panel = new CaptchaPanel();
                panel.set(image,
                        "安全中心发现您的帐号存在异常。为了您的帐号安全，请输入验证字符，这可防止恶意程序自动登录。",
                        false);
                int optionValue = javax.swing.JOptionPane.showOptionDialog(null,
                        panel, "验证",
                        javax.swing.JOptionPane.OK_CANCEL_OPTION,
                        javax.swing.JOptionPane.PLAIN_MESSAGE, null, null, null);
            }
        });

    }
}
