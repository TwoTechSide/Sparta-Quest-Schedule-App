package com.scheduleapp.controller;

import com.scheduleapp.dto.ScheduleDto;
import com.scheduleapp.entity.Schedule;
import com.scheduleapp.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ScheduleDto createSchedule(@RequestBody Schedule schedule) {
        Schedule savedSchedule = scheduleService.saveSchedule(schedule);
        return scheduleService.entityToDto(savedSchedule);
    }

    @GetMapping
    public List<ScheduleDto> findAllSchedules(@RequestParam(required = false) String userName) {
        return scheduleService.findAllSchedule(userName);
    }
}