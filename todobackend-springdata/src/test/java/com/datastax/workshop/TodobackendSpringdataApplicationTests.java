package com.datastax.workshop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TodobackendSpringdataApplicationTests {

    @Test
    void contextLoads() {
        // Spring context should have been loading at that time
        Assertions.assertTrue(true);
    }

}