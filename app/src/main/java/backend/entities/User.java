package backend.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users", schema = "sandalsDB")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    private String first_name;
    private String last_name;

    private String email;
    private String password;


    private String phone_number;

}
