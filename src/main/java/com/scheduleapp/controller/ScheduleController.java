package com.scheduleapp.controller;

import com.scheduleapp.dto.ScheduleDto;
import com.scheduleapp.entity.Schedule;
import com.scheduleapp.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

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

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleDto> findSchedules(@PathVariable Long scheduleId) {
        // userId 일정을 찾을 수 없는 경우 NOT FOUND 반환
        try {
            ScheduleDto foundScheduleDto = scheduleService.findScheduleById(scheduleId);
            return new ResponseEntity<>(foundScheduleDto, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}