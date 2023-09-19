package backend.services;

import backend.repositories.AdminRepository;
import backend.entities.Admin;
import org.springframework.beans.factory.annotation.Autowired;
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
        // Fetch admin entity by adminId
        Admin admin = adminRepository.findById(adminId).orElse(null);

        if (admin != null) {
            // Access the Admin.roles collection here
            // Hibernate session is active within this method
            // Add your code here
        }
    }

    @Transactional
    public Admin findAdminWithRoles(Long adminId) {
        Admin admin = adminRepository.findById(adminId).orElse(null);
        if(admin != null) {
            admin.getRoles().size();  // roles'u erişmek için, yüklemeyi tetikler.
        }
        return admin;
    }
}
