package com.pk.ms.mappers.week;

import com.pk.ms.dto.week.WeekBasicInfoDTO;
import com.pk.ms.entities.week.Week;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class WeekMapService {

    ModelMapper modelMapper = new ModelMapper();

    public WeekBasicInfoDTO mapToDTO(Week week) {
        return modelMapper.map(week, WeekBasicInfoDTO.class);
    }

}
