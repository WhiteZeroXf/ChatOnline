package com.zero.springboot;

import com.zero.springboot.dao.TestMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private TestMapper testMapper;

    @Test
    public void contextLoads() {
        System.out.println(testMapper);
    }

}
