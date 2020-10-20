package com.pk.ms.dao.user;

import com.pk.ms.entities.user.MyScheduleUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends CrudRepository<MyScheduleUser, Long> {

    public MyScheduleUser findById(long id);

}
