package backend.services;

import backend.entities.Admin;
import backend.repositories.AdminRepository;
import backend.services.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import backend.entities.User; // User sınıfının olduğu paketi ekleyin
import backend.repositories.UserRepository; // UserRepository sınıfının olduğu paketi ekleyin

@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomUserDetailsService(AdminRepository adminRepository, UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUserName(username);
        if (admin != null) {
            return new CustomUserDetail(admin);
        }

        User user = userRepository.findByEmail(username);
        if (user != null) {
            return new CustomUserDetail(user);  // You'll have to implement this constructor in CustomUserDetail
        }

        throw new UsernameNotFoundException("User not found: " + username);
    }
}
