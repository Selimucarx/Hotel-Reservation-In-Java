package backend.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "admins", schema = "sandalsDB")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long id;

    private String userName;
    private String password;

    @Column(nullable = false)
    private String passwordSalt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "admin_roles",
            joinColumns = @JoinColumn(name = "admin_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public Admin(String userName, String password, String passwordSalt, Set<Role> roles) {
        this.userName = userName;
        this.password = password;
        this.passwordSalt = passwordSalt;
        this.roles = roles;
    }
}
