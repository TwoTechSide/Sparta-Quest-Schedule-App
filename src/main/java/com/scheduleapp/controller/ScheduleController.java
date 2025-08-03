package com.scheduleapp.controller;

import com.scheduleapp.dto.EditScheduleTitleAndUsernameDto;
import com.scheduleapp.dto.ScheduleDto;
import com.scheduleapp.entity.Schedule;
import com.scheduleapp.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 새로운 일정 추가
    @PostMapping
    public ResponseEntity<ScheduleDto> createSchedule(@RequestBody Schedule schedule) {
        Schedule savedSchedule = scheduleService.saveSchedule(schedule);
        return new ResponseEntity<>(scheduleService.entityToDto(savedSchedule), HttpStatus.CREATED);
    }

    // userName이 있을 경우 필터링을 거치고 아닌 경우 모든 일정 반환
    @GetMapping
    public ResponseEntity<List<ScheduleDto>> findAllSchedules(@RequestParam(required = false) String userName) {
        return new ResponseEntity<>(scheduleService.findAllSchedule(userName), HttpStatus.OK);
    }

    // Schedule ID로 특정되는 일정 반환
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleDto> findSchedules(@PathVariable Long scheduleId) {
        ScheduleDto foundScheduleDto = scheduleService.findScheduleById(scheduleId);
        return new ResponseEntity<>(foundScheduleDto, HttpStatus.OK);
    }

    // Schedule의 Title, Username 수정
    @PatchMapping("/{scheduleId}")
    public ResponseEntity<ScheduleDto> editSchedule(@PathVariable Long scheduleId, @RequestBody EditScheduleTitleAndUsernameDto editScheduleTitleAndUsernameDto) {
        ScheduleDto updatedScheduleDto = scheduleService.updateScheduleTitleAndUsername(scheduleId, editScheduleTitleAndUsernameDto);
        return new ResponseEntity<>(updatedScheduleDto, HttpStatus.OK);
    }

    // 선택된 id의 Schedule 삭제 후 204 NO_CONTENT 반환
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId, @RequestParam String inputPassword) {
        scheduleService.deleteSchedule(scheduleId, inputPassword);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}