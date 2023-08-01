package com.aukcje.service.iface;

import com.aukcje.dto.UserDTO;
import com.aukcje.model.UserEditModel;
import com.aukcje.model.UserRegisterModel;
import com.aukcje.model.UserSearchModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<UserDTO> findAll();

    List<UserDTO> search(String searchPhrase, Integer searchLimit);

    List<UserDTO> search(String searchPhrase, Integer searchLimit, Boolean isActive, Boolean isBlocked, Boolean isDeleted);

    List<UserDTO> searchBySearchModel(UserSearchModel userSearchModel);


    void setUserBlocked(Long userId);

    void setUserActive(Long userId);


    UserDTO findById(Long id);

    UserDTO findByUsername(String username);

    void save(UserEditModel userModel);

    void save(UserRegisterModel userRegisterModel);

     void updateEditUser(UserEditModel userEditModel);

     void setUserDeleted(Long userId);


    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;
}
