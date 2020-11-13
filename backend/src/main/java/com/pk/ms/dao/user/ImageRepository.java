package com.pk.ms.dao.user;

import com.pk.ms.entities.user.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {

    Image findById(long id);

}
