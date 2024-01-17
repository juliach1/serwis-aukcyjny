package com.aukcje.service;

import com.aukcje.dto.UserStatusDTO;
import com.aukcje.dto.mapper.UserStatusDTOMapper;
import com.aukcje.entity.UserStatus;
import com.aukcje.repository.UserStatusRepository;
import com.aukcje.service.iface.UserStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
//@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class UserStatusServiceImpl implements UserStatusService {

    @Autowired
    private UserStatusRepository userStatusRepository;

    @Override
    public List<UserStatusDTO> findAll() {
        return createUserStatusDTO(userStatusRepository.findAll());
    }

    @Override
    public UserStatusDTO findById(Integer id){
        return createUserStatusDTO(userStatusRepository.findById(id).orElseThrow());
    }

    @Override
    public boolean isUserStatusCorrect(Integer userStatus) {
        List<UserStatusDTO> userStatusDTOS = findAll();
        for(UserStatusDTO userStatusDTO : userStatusDTOS){
            if(Objects.equals( userStatusDTO.getId(), userStatus ))
                return true;
        }
        return false;
    }

    public List<UserStatusDTO> createUserStatusDTO(List<UserStatus> userStatuses) {
        List<UserStatusDTO> userStatusesDTO = new ArrayList<>();

        for(UserStatus userStatus : userStatuses) {
            userStatusesDTO.add(UserStatusDTOMapper.instance.userStatusDTO(userStatus));
        }
        return userStatusesDTO;
    }

    public UserStatusDTO createUserStatusDTO(UserStatus userStatus) {
        return UserStatusDTOMapper.instance.userStatusDTO(userStatus);
    }

}
