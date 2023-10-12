package backend.util;

import backend.builder.AdminBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import backend.entity.Admin;
import backend.entity.Role;
import backend.repository.user.AdminRepository;
import backend.repository.user.RoleRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
public class DataLoad implements ApplicationRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;

    public DataLoad(AdminRepository adminRepository, RoleRepository roleRepository) {
        this.adminRepository = adminRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Role roleAdmin = Role.builder().roleName("ROLE_ADMIN").build();
        Role roleUser = Role.builder().roleName("ROLE_USER").build();

        roleRepository.save(roleAdmin);
        roleRepository.save(roleUser);

        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(roleAdmin);

        String hashedPassword = passwordEncoder.encode("1234");

        Admin admin = new AdminBuilder()
                .userName("admin")
                .password(hashedPassword)
                .roles(adminRoles)
                .build();

        admin.setPasswordSalt(UUID.randomUUID().toString());
        adminRepository.save(admin);
    }
}
