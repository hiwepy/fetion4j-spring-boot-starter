/*
 * Copyright (C) 2012, Apexes Network Technology. All rights reserved.
 * 
 *       http://www.apexes.net
 * 
 */
package net.apexes.fetion4j.demo;

import net.apexes.fetion4j.core.AuthSupportable;
import net.apexes.fetion4j.core.Fetion;
import net.apexes.fetion4j.core.FetionConsole;
import net.apexes.fetion4j.core.Result;
import net.apexes.fetion4j.core.user.Buddy;

/**
 *
 * @author HeDYn <hedyn@foxmail.com>
 */
public class FetionDemo {

    public static void main(String[] args) throws Exception {
        Fetion fetion = new Fetion(13800138000L);//注册飞信的手机号码
        AuthSupportable support = new SimpleAuthSupport();
        fetion.setAuthSupportable(support);
        FetionConsole console = null;
        try {
            console = fetion.login("password");//登录密码
            Buddy buddy = console.getUserInfo().getContact().findBuddy(123456789);//根据飞信号码查询好友
            Result result = console.sendMessage(buddy, "测试信息");//发送聊天消息
            System.out.println(result.getDescribe());
            result = console.sendSMSMessage(buddy, "测试短信");//发送手机短信
            System.out.println(result.getDescribe());
            //result = console.removeBuddy(buddy);//删除好友
            //result = console.addBuddy(13688888888L, "备注名称");//添加好友
        } catch (Exception ex) {
            //System.out.println(ex.getMessage());
            ex.printStackTrace();
        } finally {
            Thread.sleep(10000);
            System.out.println(console.getUserInfo());
            try {
                if (console != null) {
                    console.close();
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                //ex.printStackTrace();
            }
            try {
                fetion.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                //ex.printStackTrace();
            }
            System.out.println("结束。");
        }
    }
    
}
