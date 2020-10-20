package com.pk.ms.mappers.user;

import com.pk.ms.dto.user.UserInfoDTO;
import com.pk.ms.entities.user.MyScheduleUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserInfoMapService {

    ModelMapper modelMapper = new ModelMapper();

    public UserInfoDTO mapToDTO(MyScheduleUser user) {
        return modelMapper.map(user, UserInfoDTO.class);
    }
}
