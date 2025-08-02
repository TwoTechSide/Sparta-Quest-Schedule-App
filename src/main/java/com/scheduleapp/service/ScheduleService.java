package com.scheduleapp.service;

import com.scheduleapp.dto.ScheduleDto;
import com.scheduleapp.entity.Schedule;
import com.scheduleapp.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    // userName == null 이면 전체 일정, userName != null 이면 필터 조건으로 일부 일정을 반환
    public List<ScheduleDto> findAllSchedule(String userName) {
        return scheduleRepository.findAll().stream()
                .filter(schedule -> userName == null || schedule.getUserName().equals(userName))
                .sorted(Comparator.comparing(Schedule::getModifiedAt).reversed())
                .map(this::entityToDto)
                .toList();
    }

    public ScheduleDto findScheduleById(Long id) {
        try {
            Schedule schedule = scheduleRepository.findById(id).get();
            return entityToDto(schedule);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Schedule with id " + id + " not found");
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

    public ScheduleDto updateScheduleTitleAndUsername(Long scheduleId, ScheduleDto scheduleDto) {
        try {
            Schedule schedule = scheduleRepository.findById(scheduleId).get();
            schedule.updateTitleAndUsername(scheduleDto.getTitle(), scheduleDto.getUserName());
            return entityToDto(scheduleRepository.save(schedule));
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Schedule with id " + scheduleId + " not found");
        }
    }
}