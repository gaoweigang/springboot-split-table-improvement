package com.gwg.orm.help;

import com.gwg.orm.env.EnvironmentManager;
import com.gwg.orm.util.RSAUtil;

/**
 * <p>Class: DBPasswordDecoder</p>
 * <p>Description: 用于对数据库密码解密操作的辅助类</p>
 */
public class DBPasswordDecoder {
    public static String decode(String password){
        String privateKey = EnvironmentManager.getProperty(EnvironmentManager.DB_PASSWORD_PRIVATE_KEY);
        if(privateKey!=null){
            String temp = RSAUtil.RSADecode(password,privateKey);
            if(temp!=null){
                return temp;
            }
        }
        return password;
    }
}
