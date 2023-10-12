package backend.controller.user;

import backend.entity.User;
import backend.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/api/users")
public class LoginController {

    private UserRepository userRepository;

    @Autowired
    public LoginController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User userRequest){
        User user = userRepository.findByEmail(userRequest.getEmail());

        if(user == null || !user.getPassword().equals(userRequest.getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email or password false kanks");
        }

        return ResponseEntity.ok("Basarili giris");
    }
}

