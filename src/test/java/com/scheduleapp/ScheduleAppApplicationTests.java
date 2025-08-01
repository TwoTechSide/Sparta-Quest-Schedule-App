package com.scheduleapp;

import com.scheduleapp.entity.ScheduleEntity;
import com.scheduleapp.service.ScheduleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ScheduleAppApplicationTests {

    @Autowired
    private ScheduleService scheduleService;

    @Test
    void contextLoads() {

        ScheduleEntity scheduleEntity = ScheduleEntity.builder()
                .title("abcd")
                .content("hi")
                .userName("2TS")
                .password("1234")
                .build();

        scheduleService.save(scheduleEntity);
    }

}
