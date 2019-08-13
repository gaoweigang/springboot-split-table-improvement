package com.gwg.test;

import org.junit.Test;

public class MathTest {


    @Test
    public void testMath(){


        System.out.println(System.currentTimeMillis());

        System.out.println(Long.valueOf(Math.abs("ZT39284326400".hashCode())));

        System.out.println("123455".hashCode());

    }
}
