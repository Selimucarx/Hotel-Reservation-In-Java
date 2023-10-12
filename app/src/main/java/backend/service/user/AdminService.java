package backend.service.user;

import backend.repository.user.AdminRepository;
import backend.entity.Admin;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Transactional
    public void someMethod(Long adminId) {
        Admin admin = adminRepository.findById(adminId).orElse(null);

        if (admin != null) {

        }
    }

    @Transactional
    public Admin findAdminWithRoles(Long adminId) {
        Admin admin = adminRepository.findById(adminId).orElse(null);
        if(admin != null) {
            admin.getRoles().size();
        }
        return admin;
    }
}
