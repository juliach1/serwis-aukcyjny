package com.aukcje.service;

import com.aukcje.dto.UserDTO;
import com.aukcje.dto.mapper.UserDTOMapper;
import com.aukcje.entity.Role;
import com.aukcje.entity.User;
import com.aukcje.enums.UserStatusEnum;
import com.aukcje.model.UserEditModel;
import com.aukcje.model.UserRegisterModel;
import com.aukcje.model.UserSearchModel;
import com.aukcje.model.mapper.UserEditModelMapper;
import com.aukcje.repository.CustomUserRepository;
import com.aukcje.repository.RoleRepository;
import com.aukcje.repository.UserRepository;
import com.aukcje.repository.UserStatusRepository;
import com.aukcje.service.iface.UserService;
import com.aukcje.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomUserRepository customUserRepository;
    @Autowired
    private UserStatusRepository userStatusRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public List<UserDTO> findAll() {
        return createUserDTO(userRepository.findAll());
    }

    @Override
    public List<UserDTO> search(String searchPhrase, Integer pageSize) {
        Long numericPhrase;

        if(pageSize == null || pageSize<=0) pageSize = 10;

        if(searchPhrase == null || searchPhrase.isEmpty() || searchPhrase.isBlank()) {
            return createUserDTO(userRepository.findAll(PageRequest.of(0, pageSize)).toList());
        }else if(Utils.isLong(searchPhrase)) {
            numericPhrase = Long.parseLong(searchPhrase);
            return createUserDTO(userRepository.search(numericPhrase, searchPhrase, PageRequest.of(0, pageSize)).toList());
        }else
            return createUserDTO(userRepository.search(searchPhrase, PageRequest.of(0,pageSize)).toList());

    }

    @Override
    public List<UserDTO> search(String searchPhrase, Integer pageSize, Boolean isActive, Boolean isBlocked, Boolean isDeleted) {
        Long numericPhrase;

        if(pageSize == null || pageSize<=0) pageSize = 10;

        if( searchPhrase == null || searchPhrase.isEmpty() || searchPhrase.isBlank()) {
            return createUserDTO(userRepository.findAll(PageRequest.of(0, pageSize)).toList());
        }else if(Utils.isLong(searchPhrase)) {
            numericPhrase = Long.parseLong(searchPhrase);
            return createUserDTO(userRepository.search(numericPhrase, searchPhrase, PageRequest.of(0, pageSize)).toList());
        }else
            return createUserDTO(userRepository.search(searchPhrase, PageRequest.of(0,pageSize)).toList());

    }

    @Override
    public List<UserDTO> searchBySearchModel(UserSearchModel userSearchModel) {
        return createUserDTO(customUserRepository.findByUserSearchModel(userSearchModel).toList());
    }

    @Override
    @Transactional
    public void setUserBlocked(Long userId) {
        userRepository.setUserStatus(2, userId);
    }

    @Override
    @Transactional
    public void setUserActive(Long userId) { userRepository.setUserStatus(1, userId); }

    @Override
    public UserDTO findById(Long id) {
        return UserDTOMapper.instance.userDTO(userRepository.findById(id).orElseThrow());
    }

    @Override
    public void save(UserEditModel userModel) {
       userRepository.save(UserEditModelMapper.instance.user(userModel));
    }

    @Override
    public void save(UserRegisterModel userRegisterModel) {
        User user = new User();
        user.setUsername(userRegisterModel.getUsername());
        user.setPassword(passwordEncoder.encode(userRegisterModel.getPassword()));
        user.setEmail(userRegisterModel.getEmail());
        user.setFirstName(userRegisterModel.getFirstName());
        user.setLastName(userRegisterModel.getLastName());
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USERNAME")));;
        userRepository.save(user);
    }


    @Override
    public UserDTO findByUsername(String username){ return createUserDTO(userRepository.findByUsername(username)); }


    public List<UserDTO> createUserDTO(List<User> users){
        List<UserDTO> usersDTO = new ArrayList<>();
        for (User tempUser : users){
            usersDTO.add(UserDTOMapper.instance.userDTO(tempUser));
        }
        return usersDTO;
    }

    @Override
    public void updateEditUser(UserEditModel userEditModel){
        User user = UserEditModelMapper.instance.user(userEditModel);
        System.out.printf("EDYCJA");

        if(userEditModel.getUserStatus() != null){
            Integer userStatusId = userEditModel.getUserStatus();
            System.out.printf("USER STATUS: "+userStatusId);
            user.setUserStatus(userStatusRepository.findById(userStatusId).orElseThrow());
        }

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void setUserDeleted(Long userId) {
        userRepository.setUserStatus(3, userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("Nie znaleziono użytkownika o tej nazwie");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    @Override
    public boolean isUserActive(User user) {
        return Objects.equals(user.getUserStatus().getId(), UserStatusEnum.ACTIVE.getId());
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public UserDTO createUserDTO(User user){
        return UserDTOMapper.instance.userDTO(user);
    }
}
