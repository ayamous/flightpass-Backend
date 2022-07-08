package ma.itroad.ram.flightpass.controller;

import lombok.AllArgsConstructor;
import ma.itroad.ram.flightpass.model.bean.UserBean;
import ma.itroad.ram.flightpass.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/business/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/business/user/user-infos")
    public ResponseEntity<UserBean> getUserInfos(){
        return new ResponseEntity<>(userService.getUserInfos(), HttpStatus.OK);
    }

    @PostMapping("/public/user/logout")
    public void logout(){
           userService.logout();
        }

}
