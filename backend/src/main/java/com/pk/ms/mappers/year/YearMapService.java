package com.pk.ms.mappers.year;

import com.pk.ms.dto.year.YearBasicInfoDTO;
import com.pk.ms.entities.year.Year;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class YearMapService {

    ModelMapper modelMapper = new ModelMapper();

    public YearBasicInfoDTO mapToDTO(Year year) {
        return modelMapper.map(year, YearBasicInfoDTO.class);
    }

}
