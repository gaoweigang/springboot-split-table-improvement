package com.gwg.orm.util;


import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Random;

/**
 * 订单生成工具: 时间戳(13位)+userId后4位+4位随机数，但是这样生成的订单号orderNo有21位
 * 为了缩短订单号长度，可以可以时间戳后8位，这个订单号长度就只有16位了
 */
public class OrderNoUtil {

    public static String generateOrderNo(String userId){
        Assert.isMatchUserIdFormat(userId);
        StringBuffer sb = new StringBuffer();
        int length = userId.length();
        //时间戳
        long ts = System.currentTimeMillis();
        String userIdLast = StringUtils.substring(userId, length -4, length);//截取后4位

        int rand = RandomUtils.nextInt(1000, 10000);//生成[0, 9999]之间的随机数
        sb.append(ts).append(userIdLast).append(rand);
        return sb.toString();
    }






}
