package com.aukcje.repository;

import com.aukcje.entity.User;
import com.aukcje.model.UserSearchModel;
import org.springframework.data.domain.Page;

public interface CustomUserRepository {

    Page<User> findByUserSearchModel(UserSearchModel usm);
}
