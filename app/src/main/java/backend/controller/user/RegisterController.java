package backend.controller.user;

import backend.entity.Role;
import backend.entity.User;
import backend.repository.user.RoleRepository;
import backend.repository.user.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/api/users")
public class RegisterController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public RegisterController(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        Role userRole = roleRepository.findByRoleName("ROLE_USER");
        if (userRole == null) {
            throw new RuntimeException("User role not found");
        }
        user.setRoles(Arrays.asList(userRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
