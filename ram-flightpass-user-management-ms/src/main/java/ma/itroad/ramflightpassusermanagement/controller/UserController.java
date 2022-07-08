package ma.itroad.ramflightpassusermanagement.controller;

import lombok.AllArgsConstructor;
import ma.itroad.ramflightpassusermanagement.model.bean.UserDto;
import ma.itroad.ramflightpassusermanagement.service.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/business/user")
@AllArgsConstructor
public class UserController {

    private IUserService userService;


//    @Value("${keycloak.credentials.secret}")
//    private String secretClient;


    @PostMapping("/create")
    public ResponseEntity addUser(@RequestBody UserDto userDto) {

        return userService.createUser(userDto);
    }


    @PostMapping("/reset-password/{email}")
    public void test(@PathVariable("email") String email) {
        userService.resetPassword(email);
    }
}

