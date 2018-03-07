package com.khcm.user.web.common.utils;

import com.khcm.user.common.constant.Constants;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * 安全工具类
 * @author wangtao
 * @date 2017/12/27.
 */
public class SecurityUtils {

    public static final int CIPHER_KEY_LENGTH = 128;

    /**
     * 根据盐值及明文密码生成密文密码
     *
     * @param credentials 密码
     * @param saltSource 密码盐
     * @return
     */
    public static String md5(String credentials, String saltSource) {
        return new SimpleHash(Constants.HASH_ALGORITHM_NAME, credentials, ByteSource.Util.bytes(saltSource), Constants.HASH_ITERATIONS).toString();
    }

    /**
     * 生成密钥
     *
     * @return 16进制编码的密钥
     */
    public static String generateKey() throws NoSuchAlgorithmException {
        AesCipherService aesCipherService = new AesCipherService();
        aesCipherService.setKeySize(CIPHER_KEY_LENGTH); //设置key长度
        Key key = aesCipherService.generateNewKey();
        return Base64.encodeToString(key.getEncoded());
    }

    public static void main(String[] args) throws Exception {
        System.out.println(generateKey());
    }
}
