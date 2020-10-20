package com.pk.ms.mappers.day;

import com.pk.ms.dto.day.DayBasicInfoDTO;
import com.pk.ms.entities.day.Day;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class DayMapService {

    ModelMapper modelMapper = new ModelMapper();

    public DayBasicInfoDTO mapToDTO(Day day) {
        return modelMapper.map(day, DayBasicInfoDTO.class);
    }

}
