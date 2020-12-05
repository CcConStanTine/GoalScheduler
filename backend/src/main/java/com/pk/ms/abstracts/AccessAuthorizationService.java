package com.pk.ms.abstracts;

import org.springframework.security.access.AccessDeniedException;

public interface AccessAuthorizationService {

    default void authorize(boolean b) {
        if(!b)
            throw new AccessDeniedException("Access denied. ");
    }

}
