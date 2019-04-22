package dst.courseproject.services;

import dst.courseproject.entities.Role;

public interface RoleService {

    Role getRoleByAuthority(String authority);

    Role save(Role role);
}
