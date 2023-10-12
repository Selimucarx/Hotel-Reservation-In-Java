package backend.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:8000")
@RequestMapping("/api")
public class MyRestController {



    @GetMapping("/admin-page")
    public String getAdminPage() {
        // your code here
        return "sadece admin kipss";
    }


    @GetMapping("/users/reset-password")
    public String getResetPasswordPage() {
        // your code here
        return "sadece user kipss";
    }


    @GetMapping("/index")
    public String getIndex() {
        // your code here
        return "herkes takilsin burada";
    }

}
