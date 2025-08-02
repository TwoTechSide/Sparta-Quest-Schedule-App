package com.scheduleapp.controller;

import com.scheduleapp.dto.ScheduleDto;
import com.scheduleapp.entity.Schedule;
import com.scheduleapp.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleDto> createSchedule(@RequestBody Schedule schedule) {
        Schedule savedSchedule = scheduleService.saveSchedule(schedule);
        return new ResponseEntity<>(scheduleService.entityToDto(savedSchedule), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleDto>> findAllSchedules(@RequestParam(required = false) String userName) {
        return new ResponseEntity<>(scheduleService.findAllSchedule(userName), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ScheduleDto> findSchedules(@PathVariable Long userId) {
        Optional<Schedule> schedule = scheduleService.findScheduleById(userId);

        // 만약 userId 일정을 찾을 수 없는 경우 204 No Content 반환
        return schedule.map(value -> new ResponseEntity<>(scheduleService.entityToDto(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }
}