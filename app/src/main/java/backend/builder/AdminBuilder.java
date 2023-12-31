package backend.builder;

import java.util.Set;

import backend.entity.Admin;
import backend.entity.Role;

public class AdminBuilder {

    private String userName;
    private String password;
    private Set<Role> roles;

    public AdminBuilder userName(String userName) {
        this.userName = userName;
        return this;
    }



    public AdminBuilder password(String password) {
        this.password = password;
        return this;
    }

    public AdminBuilder roles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Admin build() {
        Admin admin = new Admin(null, this.userName, this.password, "", this.roles);
        return admin;
    }


}
