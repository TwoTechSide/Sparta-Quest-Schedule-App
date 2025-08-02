package com.scheduleapp.controller;

import com.scheduleapp.dto.EditScheduleTitleAndUsernameDto;
import com.scheduleapp.dto.ScheduleDto;
import com.scheduleapp.entity.Schedule;
import com.scheduleapp.exception.InvalidPasswordException;
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
    // userId 일정을 찾을 수 없는 경우 NOT FOUND 반환
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleDto> findSchedules(@PathVariable Long scheduleId) {
        try {
            ScheduleDto foundScheduleDto = scheduleService.findScheduleById(scheduleId);
            return new ResponseEntity<>(foundScheduleDto, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Schedule의 Title, Username 수정
    // Schedule의 id를 찾을 수 없으면 BAD_REQUEST, 비밀번호가 일치하지 않으면 UNAUTHORIZED로 예외 처리
    @PatchMapping("/{scheduleId}")
    public ResponseEntity<ScheduleDto> editSchedule(@PathVariable Long scheduleId, @RequestBody EditScheduleTitleAndUsernameDto editScheduleTitleAndUsernameDto) {
        try {
            ScheduleDto updatedScheduleDto = scheduleService.updateScheduleTitleAndUsername(scheduleId, editScheduleTitleAndUsernameDto);
            return new ResponseEntity<>(updatedScheduleDto, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (InvalidPasswordException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}