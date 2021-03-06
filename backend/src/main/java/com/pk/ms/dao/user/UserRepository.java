package com.pk.ms.dao.user;

import com.pk.ms.entities.user.MyScheduleUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<MyScheduleUser, Long> {

    Optional<MyScheduleUser> findById(long id);

    boolean existsByNick(String nick);

    boolean existsByEmail(String email);

    MyScheduleUser findByNick(String nick);

}
