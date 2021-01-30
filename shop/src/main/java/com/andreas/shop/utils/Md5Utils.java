package com.andreas.shop.utils;

import com.andreas.shop.common.Constant;
import org.apache.tomcat.util.codec.binary.Base64;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * @Author andreaszhou
 * @ClassName Md5Utils
 * @Description MD5加密算法
 * @date 2021/1/27 12:56
 * @Version 1.0
 */
public class Md5Utils {

    public static String md5Utils(String resource) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("md5");
        String target = Base64.encodeBase64String(md5.digest((resource + Constant.SALT).getBytes()));
        return target;
    }
}
