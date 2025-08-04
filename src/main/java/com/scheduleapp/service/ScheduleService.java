package com.scheduleapp.service;

import com.scheduleapp.dto.schedule.EditScheduleTitleAndUsernameDto;
import com.scheduleapp.dto.schedule.ScheduleRequestDto;
import com.scheduleapp.dto.schedule.ScheduleResponseDto;
import com.scheduleapp.dto.schedule.ScheduleWithCommentResponseDto;
import com.scheduleapp.entity.Comment;
import com.scheduleapp.entity.Schedule;
import com.scheduleapp.exception.CustomException;
import com.scheduleapp.repository.CommentRepository;
import com.scheduleapp.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

import static com.scheduleapp.exception.ErrorCode.INVALID_PASSWORD;
import static com.scheduleapp.exception.ErrorCode.SCHEDULE_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    // 새로운 일정 생성
    @Transactional
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = scheduleRequestDto.toEntity();
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(savedSchedule);
    }

    // userName == null 이면 전체 일정, userName != null 이면 필터 조건으로 일부 일정을 반환
    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> findAllSchedule(String userName) {
        return scheduleRepository.findAll().stream()
                .filter(schedule -> userName == null || schedule.getUserName().equals(userName))
                .sorted(Comparator.comparing(Schedule::getModifiedAt).reversed())
                .map(ScheduleResponseDto::new)
                .toList();
    }

    // id 값이 맞는 특정 일정을 Dto로 반환
    @Transactional(readOnly = true)
    public ScheduleWithCommentResponseDto findScheduleById(Long scheduleId) {
        Schedule schedule = findScheduleByIdOrThrow(scheduleId);
        List<Comment> comments = commentRepository.findAllByScheduleId(schedule.getId());

        return new ScheduleWithCommentResponseDto(schedule, comments);
    }

    // 일정의 제목과 작성자명 수정
    @Transactional
    public ScheduleResponseDto updateScheduleTitleAndUsername(Long scheduleId, EditScheduleTitleAndUsernameDto editScheduleTitleAndUsernameDto) {
        Schedule schedule = findScheduleByIdOrThrow(scheduleId);

        checkSchedulePasswordIsCorrect(schedule, editScheduleTitleAndUsernameDto.getPassword());

        schedule.updateTitleAndUsername(editScheduleTitleAndUsernameDto.getTitle(), editScheduleTitleAndUsernameDto.getUserName());
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(savedSchedule);
    }

    // 일정 삭제
    @Transactional
    public void deleteSchedule(Long scheduleId, String inputPassword) {
        Schedule schedule = findScheduleByIdOrThrow(scheduleId);

        checkSchedulePasswordIsCorrect(schedule, inputPassword);

        scheduleRepository.deleteById(scheduleId);
    }

    // 비밀번호가 일치하지 않으면 InvalidPasswordException 예외 처리
    public void checkSchedulePasswordIsCorrect(Schedule schedule, String inputPassword) {
        String storedPassword = schedule.getPassword();

        if (!inputPassword.equals(storedPassword))
            throw new CustomException(INVALID_PASSWORD);
    }

    // Id로 일정 검색, 없으면 ScheduleNotFoundException 예외 처리
    public Schedule findScheduleByIdOrThrow(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CustomException(SCHEDULE_NOT_FOUND));
    }
}