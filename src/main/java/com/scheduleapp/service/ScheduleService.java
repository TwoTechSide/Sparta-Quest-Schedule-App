package com.scheduleapp.service;

import com.scheduleapp.entity.Schedule;
import com.scheduleapp.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public void save(Schedule schedule) {
        scheduleRepository.save(schedule);
    }
}
