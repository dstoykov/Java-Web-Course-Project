package dst.courseproject.services;

import dst.courseproject.entities.Role;

public interface RoleService {

    Role getRoleByAuthority(String authority);

    void save(Role role);
}
