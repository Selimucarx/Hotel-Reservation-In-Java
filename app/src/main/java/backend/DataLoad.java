package backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import backend.entities.Admin;
import backend.entities.Role;
import backend.repositories.AdminRepository;
import backend.repositories.RoleRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
public class DataLoad implements ApplicationRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;  // PasswordEncoder bean'ı enjekte edildi

    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;

    public DataLoad(AdminRepository adminRepository, RoleRepository roleRepository) {
        this.adminRepository = adminRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Roles insertion
        Role roleAdmin = Role.builder().roleName("ROLE_ADMIN").build();
        Role roleUser = Role.builder().roleName("ROLE_USER").build();

        roleRepository.save(roleAdmin);
        roleRepository.save(roleUser);

        // Admin creation and insertion
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(roleAdmin);

        String hashedPassword = passwordEncoder.encode("1234");  // Şifre BCrypt ile şifrelendi

        Admin admin = new AdminBuilder()
                .userName("admin")
                .password(hashedPassword)  // Şifrelenmiş şifre
                .roles(adminRoles)
                .build();

        admin.setPasswordSalt(UUID.randomUUID().toString());
        adminRepository.save(admin);
    }
}
