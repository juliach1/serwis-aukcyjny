package com.aukcje.repository;

import com.aukcje.entity.User;
import com.aukcje.model.UserSearchModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomUserRepository {

    Page<User> findByUserSearchModel(UserSearchModel usm);
}
