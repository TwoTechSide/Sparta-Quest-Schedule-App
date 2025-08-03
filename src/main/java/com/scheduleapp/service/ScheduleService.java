package com.scheduleapp.service;

import com.scheduleapp.dto.EditScheduleTitleAndUsernameDto;
import com.scheduleapp.dto.ScheduleDto;
import com.scheduleapp.entity.Schedule;
import com.scheduleapp.exception.CustomException;
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

    // 새로운 일정 생성
    @Transactional
    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    // userName == null 이면 전체 일정, userName != null 이면 필터 조건으로 일부 일정을 반환
    @Transactional(readOnly = true)
    public List<ScheduleDto> findAllSchedule(String userName) {
        return scheduleRepository.findAll().stream()
                .filter(schedule -> userName == null || schedule.getUserName().equals(userName))
                .sorted(Comparator.comparing(Schedule::getModifiedAt).reversed())
                .map(this::entityToDto)
                .toList();
    }

    // id 값이 맞는 특정 일정을 Dto로 반환
    @Transactional(readOnly = true)
    public ScheduleDto findScheduleById(Long scheduleId) {
        Schedule schedule = findScheduleByIdOrThrow(scheduleId);
        return entityToDto(schedule);
    }

    // 일정의 제목과 작성자명 수정
    @Transactional
    public ScheduleDto updateScheduleTitleAndUsername(Long scheduleId, EditScheduleTitleAndUsernameDto editScheduleTitleAndUsernameDto) {
        Schedule schedule = findScheduleByIdOrThrow(scheduleId);

        checkSchedulePasswordIsCorrect(schedule, editScheduleTitleAndUsernameDto.getPassword());

        schedule.updateTitleAndUsername(editScheduleTitleAndUsernameDto.getTitle(), editScheduleTitleAndUsernameDto.getUserName());
        return entityToDto(scheduleRepository.save(schedule));
    }

    // 일정 삭제
    @Transactional
    public void deleteSchedule(Long scheduleId, String inputPassword) {
        Schedule schedule = findScheduleByIdOrThrow(scheduleId);

        checkSchedulePasswordIsCorrect(schedule, inputPassword);

        scheduleRepository.deleteById(scheduleId);
    }

    public ScheduleDto entityToDto(Schedule schedule) {
        return ScheduleDto.builder()
                .id(schedule.getId())
                .title(schedule.getTitle())
                .content(schedule.getContent())
                .userName(schedule.getUserName())
                .createdAt(schedule.getCreatedAt())
                .modifiedAt(schedule.getModifiedAt()).build();
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