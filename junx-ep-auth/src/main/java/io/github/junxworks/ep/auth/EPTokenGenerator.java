/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  TokenGenerator.java   
 * @Package com.yrxd.security.app.shiro   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2019-1-16 17:46:33   
 * @version V1.0 
 * @Copyright: 2019 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.auth;

import java.security.MessageDigest;
import java.util.UUID;

import io.github.junxworks.junx.core.exception.BaseRuntimeException;

/**
 * token生成器
 *
 * @ClassName:  TokenGenerator
 * @author: 王兴
 * @date:   2019-1-16 17:46:33
 * @since:  v1.0
 */
public class EPTokenGenerator {

    /**
     * Generate value.
     *
     * @return the string
     */
    public static String generateValue() {
        return generateValue(UUID.randomUUID().toString());
    }

    /** 常量 hexCode. */
    private static final char[] hexCode = "0123456789abcdef".toCharArray();

    /**
     * To hex string.
     *
     * @param data the data
     * @return the string
     */
    public static String toHexString(byte[] data) {
        if(data == null) {
            return null;
        }
        StringBuilder r = new StringBuilder(data.length*2);
        for ( byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }

    /**
     * Generate value.
     *
     * @param param the param
     * @return the string
     */
    public static String generateValue(String param) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(param.getBytes());
            byte[] messageDigest = algorithm.digest();
            return toHexString(messageDigest);
        } catch (Exception e) {
            throw new BaseRuntimeException("生成Token失败", e);
        }
    }
}
