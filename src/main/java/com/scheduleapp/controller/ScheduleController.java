package com.scheduleapp.controller;

import com.scheduleapp.dto.schedule.EditScheduleTitleAndUsernameDto;
import com.scheduleapp.dto.schedule.ScheduleRequestDto;
import com.scheduleapp.dto.schedule.ScheduleResponseDto;
import com.scheduleapp.dto.schedule.ScheduleWithCommentResponseDto;
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
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        return new ResponseEntity<>(scheduleService.saveSchedule(scheduleRequestDto), HttpStatus.CREATED);
    }

    // userName이 있을 경우 필터링을 거치고 아닌 경우 모든 일정 반환
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules(@RequestParam(required = false) String userName) {
        return new ResponseEntity<>(scheduleService.findAllSchedule(userName), HttpStatus.OK);
    }

    // Schedule ID로 특정되는 일정 반환
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleWithCommentResponseDto> findSchedules(@PathVariable Long scheduleId) {
        ScheduleWithCommentResponseDto foundScheduleResponseDto = scheduleService.findScheduleById(scheduleId);
        return new ResponseEntity<>(foundScheduleResponseDto, HttpStatus.OK);
    }

    // Schedule의 Title, Username 수정
    @PatchMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> editSchedule(@PathVariable Long scheduleId, @RequestBody EditScheduleTitleAndUsernameDto editScheduleTitleAndUsernameDto) {
        ScheduleResponseDto updatedScheduleResponseDto = scheduleService.updateScheduleTitleAndUsername(scheduleId, editScheduleTitleAndUsernameDto);
        return new ResponseEntity<>(updatedScheduleResponseDto, HttpStatus.OK);
    }

    // 선택된 id의 Schedule 삭제 후 204 NO_CONTENT 반환
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId, @RequestParam String inputPassword) {
        scheduleService.deleteSchedule(scheduleId, inputPassword);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}