package com.scheduleapp.service;

import com.scheduleapp.dto.EditScheduleTitleAndUsernameDto;
import com.scheduleapp.dto.ScheduleDto;
import com.scheduleapp.entity.Schedule;
import com.scheduleapp.exception.InvalidPasswordException;
import com.scheduleapp.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

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

    // Schedule의 id 값에 맞는 특정 일정을 반환
    @Transactional(readOnly = true)
    public ScheduleDto findScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);

        return entityToDto(schedule);
    }

    // scheduleId로 일정을 찾을 수 없는 경우 -> NoSuchElementException
    // 패스워드가 일치하지 않는 경우 -> InvalidPasswordException
    @Transactional
    public ScheduleDto updateScheduleTitleAndUsername(Long scheduleId, EditScheduleTitleAndUsernameDto editScheduleTitleAndUsernameDto) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(NoSuchElementException::new);

        if (isInvalidPassword(schedule, editScheduleTitleAndUsernameDto.getPassword()))
            throw new InvalidPasswordException();

        schedule.updateTitleAndUsername(editScheduleTitleAndUsernameDto.getTitle(), editScheduleTitleAndUsernameDto.getUserName());
        return entityToDto(scheduleRepository.save(schedule));
    }

    // 일정 삭제, 특정 id의 일정이 존재하지 않거나 password가 일치하지 않으면 예외처리
    @Transactional
    public void deleteSchedule(Long scheduleId, String inputPassword) {
        try {
            Schedule schedule = scheduleRepository.findById(scheduleId)
                    .orElseThrow(NoSuchElementException::new);

            if (isInvalidPassword(schedule, inputPassword))
                throw new InvalidPasswordException();

            scheduleRepository.deleteById(scheduleId);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException();
        } catch (InvalidPasswordException e) {
            throw new InvalidPasswordException();
        }
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

    // 비밀번호 불일치 여부
    public boolean isInvalidPassword(Schedule schedule, String inputPassword) {
        String storedPassword = schedule.getPassword();
        return !inputPassword.equals(storedPassword);
    }
}