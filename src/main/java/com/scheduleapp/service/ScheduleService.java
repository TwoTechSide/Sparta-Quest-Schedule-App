package com.scheduleapp.service;

import com.scheduleapp.dto.ScheduleDto;
import com.scheduleapp.entity.Schedule;
import com.scheduleapp.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<ScheduleDto> findAllSchedule(String userName) {
        return scheduleRepository.findAll().stream()
                .filter(schedule -> userName == null || schedule.getUserName().equals(userName))
                .sorted(Comparator.comparing(Schedule::getUpdatedAt).reversed())
                .map(this::entityToDto)
                .toList();
    }

    public ScheduleDto entityToDto(Schedule schedule) {
        return ScheduleDto.builder()
                .id(schedule.getId())
                .title(schedule.getTitle())
                .content(schedule.getContent())
                .userName(schedule.getUserName())
                .createdAt(schedule.getCreatedAt())
                .updatedAt(schedule.getUpdatedAt()).build();
    }
}