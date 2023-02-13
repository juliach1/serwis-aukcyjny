package com.aukcje.service.iface;

import com.aukcje.dto.UserStatusDTO;
import com.aukcje.entity.UserStatus;

import java.util.List;

public interface UserStatusService {

    List<UserStatusDTO> findAll();

    UserStatusDTO findById(Integer id);



}
