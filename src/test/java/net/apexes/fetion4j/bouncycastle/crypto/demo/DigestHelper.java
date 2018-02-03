/*
 * Copyright (C) 2011, Apexes Network Technology. All rights reserved.
 * 
 *       http://www.apexes.net
 * 
 */
package net.apexes.fetion4j.bouncycastle.crypto.demo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import net.apexes.fetion4j.bouncycastle.crypto.BufferedBlockCipher;
import net.apexes.fetion4j.bouncycastle.crypto.engines.AESLightEngine;
import net.apexes.fetion4j.bouncycastle.crypto.modes.CBCBlockCipher;
import net.apexes.fetion4j.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import net.apexes.fetion4j.bouncycastle.crypto.params.KeyParameter;
import net.apexes.fetion4j.bouncycastle.crypto.params.ParametersWithIV;

/**
 * 加密算法工具
 * 
 * @author HeDYn<hedyn@foxmail.com>
 */
public final class DigestHelper {

    /**
     * 计算MD5值
     * @param bytes		须计算的字节数组
     * @return			计算结果
     */
    public static byte[] MD5(byte[] bytes) {
        return digest("MD5", bytes);
    }

    /**
     * 计算SHA1值
     * @param bytes		须计算的字节数组
     * @return			计算结果
     */
    public static byte[] SHA1(byte[] bytes) {
        return digest("SHA-1", bytes);
    }

    /**
     * 计算HASH值
     * @param type		Hash类型
     * @param bytes		需计算的字节
     * @return			HASH结果
     */
    private static byte[] digest(String type, byte[] bytes) {
        try {
            MessageDigest dist = MessageDigest.getInstance(type);
            return dist.digest(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("Cannot find digest:" + type, e);
        }
    }

    /**
     * 生成256位AES对称加密算法的密钥
     * @return
     */
    public static byte[] createAESKey() {
        //java.security.SecureRandom random = new java.security.SecureRandom();
        //return random.generateSeed(32);
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(0x100);
            SecretKey skey = kgen.generateKey();
            return skey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("Cannot find key generator: AES", e);
        }
    }

    /**
     * 使用AES算法解密数据
     * @param encrypted
     * @param key			密钥为256位
     * @param iv			加密矢量，增加加密强度
     * @return
     */
    public static byte[] AESDecrypt(byte[] encrypted, byte[] key, byte[] iv) {
        try {
            CBCBlockCipher cbc = new CBCBlockCipher(new AESLightEngine());
            BufferedBlockCipher engine = new PaddedBufferedBlockCipher(cbc);
            engine.init(false, new ParametersWithIV(new KeyParameter(key), iv));
            byte[] dec = new byte[engine.getOutputSize(encrypted.length)];
            int size1 = engine.processBytes(encrypted, 0, encrypted.length, dec, 0);
            int size2 = engine.doFinal(dec, size1);
            byte[] decrypted = new byte[size1 + size2];
            System.arraycopy(dec, 0, decrypted, 0, decrypted.length);
            return decrypted;
        } catch (Exception e) {
            throw new IllegalArgumentException("AESDecrypt failed.", e);
        }
    }
}
