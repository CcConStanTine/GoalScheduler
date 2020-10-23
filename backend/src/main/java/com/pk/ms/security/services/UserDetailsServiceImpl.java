package com.pk.ms.security.services;

import com.pk.ms.dao.user.IUserRepository;
import com.pk.ms.entities.user.MyScheduleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    IUserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        MyScheduleUser user = userRepository.findByNick(username);
        if(user == null){
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        } else{
            return UserDetailsImpl.build(user);
        }


    }

}
