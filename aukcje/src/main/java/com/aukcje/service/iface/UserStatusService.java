package com.aukcje.service.iface;

import com.aukcje.dto.UserStatusDTO;

import java.util.List;
import java.util.Objects;

public interface UserStatusService {

    List<UserStatusDTO> findAll();

    UserStatusDTO findById(Integer id);

    boolean isUserStatusCorrect(Integer userStatus);

}
