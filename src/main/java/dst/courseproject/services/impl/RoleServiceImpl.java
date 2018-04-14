package dst.courseproject.services.impl;

import dst.courseproject.entities.Role;
import dst.courseproject.repositories.RoleRepository;
import dst.courseproject.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleByAuthority(String authority) {
        return this.roleRepository.findByAuthority(authority);
    }
}
