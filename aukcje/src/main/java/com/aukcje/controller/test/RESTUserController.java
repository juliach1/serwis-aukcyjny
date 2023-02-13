//package com.aukcje.controller.test;
//
//import com.aukcje.entity.User;
//import com.aukcje.service.test.UserServiceTest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/user")
//public class RESTUserController {
//
//    @Autowired
//    UserServiceTest userService;
//
//    @GetMapping("/")
//    public List<User> getUsers(){
//        return userService.get();
//    }
//
//    @PostMapping("/")
//    public ResponseEntity<User> save(@RequestBody User user){
//        user.setId(0);
//
//        // sprawdzenie, czy podano nazwę
//        if(user.getUsername().isEmpty()){
//            return new ResponseEntity<>(user,HttpStatus.I_AM_A_TEAPOT);
//        }
//
//        // sprawdzenie, czy podana nazwa istnieje juz w bazie
//        if(userService.checkIfUsernameExists(user.getUsername())){
//            return new ResponseEntity<>(user,HttpStatus.CONFLICT);
//        }
//
//        userService.save(user);
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }
//
//    @PutMapping("/")
//    public ResponseEntity<User> update(User user){
//
//        userService.save(user);
//
//        return new ResponseEntity<>(user,HttpStatus.OK);
//    }
//
//}
