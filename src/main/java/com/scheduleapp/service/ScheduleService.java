package com.scheduleapp.service;

import com.scheduleapp.entity.ScheduleEntity;
import com.scheduleapp.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleEntity save(ScheduleEntity scheduleEntity) {
        return scheduleRepository.save(scheduleEntity);
    }
}
