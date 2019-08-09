package com.gwg.test;

import org.junit.Test;

public class StringTest {

    /**
     * JAVA字符串格式化-String.format()的使用
     * String类的format()方法用于创建格式化的字符串以及连接多个字符串对象。
     * format(String format, Object ...args)新字符串使用本地语言环境，指定字符串格式和参数生成格式化的新字符串
     * 显示不同转换符 实现 不同数据类型 到 字符串的转换，如图所示。
     * 转换符      说明              示例     备注
     *  %d    整数类型(十进制)        99      整数类型到字符串的转换
     * 搭配转换符的标志，如图所示：
     * 标志        说明               示例                结果      备注
     *  +     为整数或负数添加符号    ("%+d", -15)         -15      标识负数15
     *  -     左对齐                ("前%-5d后", -15)   前-15  后
     *  0     数字前面补0            ("%05d",3)         00003      在这里5表示宽度
      */
    @Test
    public void testStringFormat(){

        System.out.println(String.format("%+d", -15));
        System.out.println(String.format("前分隔符%-5d后分隔符", -15));
        System.out.println(String.format("%05d", 3));
        System.out.println(String.format("%05d", 543216));


    }

}
