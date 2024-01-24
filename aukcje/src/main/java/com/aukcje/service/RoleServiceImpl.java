package com.aukcje.service;

import com.aukcje.entity.Role;
import com.aukcje.repository.RoleRepository;
import com.aukcje.service.iface.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getByName(String name) {
        return roleRepository.findByName(name);
    }
}
