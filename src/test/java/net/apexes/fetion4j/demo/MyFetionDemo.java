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

/**
 *
 * @author HeDYn <hedyn@foxmail.com>
 */
public class MyFetionDemo {

    public static void main(String[] args) throws Exception {
        //Fetion fetion = new Fetion(13427600000L);
        //Fetion fetion = new Fetion(15817000000L);
        Fetion fetion = new Fetion(15820220000L);
        AuthSupportable support = new SimpleAuthSupport();
        fetion.setAuthSupportable(support);
        FetionConsole console = null;
        try {
            //console = fetion.login("123456");
            console = fetion.login("123456");
            //Buddy buddy = console.getUserInfo().getContact().findBuddy(472370001);
            //Buddy buddy = console.getUserInfo().getContact().findBuddy(463991000);
            //Result result = console.sendMessage(buddy, "测试信息");
            //System.out.println(result.getDescribe());
            //result = console.sendSMSMessage(buddy, "测试短信");
            //System.out.println(result.getDescribe());
            //console.removeBuddy(buddy, support);
            //console.addBuddy("13427600000", "abc", support);
            
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