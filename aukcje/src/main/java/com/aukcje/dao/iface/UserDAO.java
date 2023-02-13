package com.aukcje.dao.iface;

import com.aukcje.entity.User;

import java.util.List;

public interface UserDAO {

    void save(User user);

    List<User> get();

    User getById(int id);

    User getByUsername(String username);
}
