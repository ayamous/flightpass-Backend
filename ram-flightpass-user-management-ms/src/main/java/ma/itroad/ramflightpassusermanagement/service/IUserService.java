package ma.itroad.ramflightpassusermanagement.service;

import ma.itroad.ramflightpassusermanagement.model.bean.UserDto;
import org.springframework.http.ResponseEntity;

public interface IUserService {

    ResponseEntity<?> createUser(UserDto userDto);

    ResponseEntity<?> resetPassword(String email);
}
