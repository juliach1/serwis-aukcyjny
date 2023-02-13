package com.aukcje.service;

import com.aukcje.entity.Role;
import com.aukcje.repository.RoleRepository;
import com.aukcje.service.iface.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getByName(String name) {
        return roleRepository.findByName(name);
    }
}
