package com.aukcje.service;

import com.aukcje.dto.UserDTO;
import com.aukcje.dto.mapper.UserDTOMapper;
import com.aukcje.entity.Role;
import com.aukcje.entity.User;
import com.aukcje.enums.UserStatusEnum;
import com.aukcje.exception.customException.UserNotFoundException;
import com.aukcje.model.UserEditModel;
import com.aukcje.model.UserRegisterModel;
import com.aukcje.model.UserSearchModel;
import com.aukcje.model.mapper.UserEditModelMapper;
import com.aukcje.model.mapper.UserRegisterModelMapper;
import com.aukcje.repository.CustomUserRepository;
import com.aukcje.repository.RoleRepository;
import com.aukcje.repository.UserRepository;
import com.aukcje.repository.UserStatusRepository;
import com.aukcje.service.iface.UserService;
import com.aukcje.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.nio.file.Files.exists;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CustomUserRepository customUserRepository;
    private final UserStatusRepository userStatusRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ServletContext servletContext;

    @Value("${files.path}")
    private String filesPath;

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
    public UserDTO findById(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        System.out.println("avatarpath: "+user.getAvatarPath());

        UserDTO userDTO = UserDTOMapper.instance.userDTO(user);
        System.out.println("avatarpath: "+userDTO.avatarPath);

        return userDTO;
    }

    @Override
    public void save(UserEditModel userModel) {
        userRepository.save(UserEditModelMapper.instance.user(userModel));
    }

    @Override
    public void save(UserRegisterModel userRegisterModel) {
        User user = UserRegisterModelMapper.instance.user(userRegisterModel);
        user.setPassword(passwordEncoder.encode(userRegisterModel.getPassword()));
        user.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_USERNAME")));;
        user.setRegistrationDate(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public UserDTO findByUsername(String username){ return createUserDTO(userRepository.findByUsername(username)); }

    private List<UserDTO> createUserDTO(List<User> users) {
        List<UserDTO> usersDTO = new ArrayList<>();
        for (User tempUser : users){
            usersDTO.add(UserDTOMapper.instance.userDTO(tempUser));
        }
        return usersDTO;
    }

    @Override
    public void updateEditUser(UserEditModel userEditModel) {
        User user = UserEditModelMapper.instance.user(userEditModel);

        if(userEditModel.getUserStatus() != null){
            Integer userStatusId = userEditModel.getUserStatus();
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

    @Override
    public boolean isUserActive(UserDTO userDTO) {
        return userDTO.getUserStatus().getId().equals(UserStatusEnum.ACTIVE.getId());
    }

    @Override
    @Transactional
    public void setUserRating(Long userId, Double userRating) {
        User user = userRepository.getOne(userId);
        user.setAverageRate(userRating);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateProfilePhoto(long id, MultipartFile file) throws UserNotFoundException {
        addPhoto(id, file);
    }



    private void addPhoto(long userId, MultipartFile multipartFile) throws UserNotFoundException {

        // exit if filesize < 1
        if (!(multipartFile.getSize() > 1)) {
            return;
        }

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Long photoName = userId;


        String path;
        String pathTarget;


        Path directory = Paths.get(userPathTarget() + "\\" + userId);

        if (!exists(directory)) {
            path = createFolderForUser(userPath(), userId) + "\\" + photoName + ".png";
            pathTarget = createFolderForUser(userPathTarget(), userId) + "\\" + photoName + ".png";
        } else {
            path = userPath() + "\\" + userId + "\\" + photoName + ".png";
            pathTarget = userPathTarget() + "\\" + userId + "\\" + photoName + ".png";
        }

        File file = new File(path);
        File fileTarget = new File(pathTarget);

        saveOfferPhoto(file, multipartFile);
        saveOfferPhoto(fileTarget, multipartFile);

        User changedUser = userRepository.getOne(userId);
        changedUser.setAvatarPath(photoName.toString());
        userRepository.save(changedUser);
    }

    private void saveOfferPhoto(File file, MultipartFile multipartFile){
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(file);
            byte[] bytes = multipartFile.getBytes();
            fos.write(bytes);
            fos.close();
        } catch (FileNotFoundException e) {
            System.out.println("OfferServiceImpl.saveOfferPhoto FileNotFoundException: Błąd podczas tworzenia pliku");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("OfferServiceImpl.saveOfferPhoto IOException");
            e.printStackTrace();
        }
    }

    private String userPath(){
        return filesPath+"\\img\\users";
    }

    private String userPathTarget(){
        return servletContext.getRealPath("/WEB-INF/files/img/users");
    }

    private String createFolderForUser(String path, long userId){
        String createdPath = path + "\\"+ userId;
        File folder = new File(createdPath);
        folder.mkdirs();

        return createdPath;
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public UserDTO createUserDTO(User user){
        return UserDTOMapper.instance.userDTO(user);
    }
}
