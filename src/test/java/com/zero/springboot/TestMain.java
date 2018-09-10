package com.zero.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author Hxf
 * @version V1.0
 * @Title:
 * @Description: TODO
 * @date 2018/9/10 9:13
 */
@RunWith(JUnit4.class)
public class TestMain {

    @Test
    public void name() {
        long x = 15896636546352L;
            int d = 1;
            if (x >= 0) {
                d = 0;
                x = -x;
            }
            long p = -10;
            for (int i = 1; i < 19; i++) {
                if (x > p)
                    System.out.println(i + d);
                p = 10 * p;
            }
        System.out.println(19 + d);
    }
}
