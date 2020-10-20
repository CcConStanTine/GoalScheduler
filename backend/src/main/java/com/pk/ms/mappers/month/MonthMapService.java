package com.pk.ms.mappers.month;

import com.pk.ms.dto.month.MonthBasicInfoDTO;
import com.pk.ms.entities.month.Month;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class MonthMapService {


    ModelMapper modelMapper = new ModelMapper();

    public MonthBasicInfoDTO mapToDTO(Month month) {
        return modelMapper.map(month, MonthBasicInfoDTO.class);
    }
}
