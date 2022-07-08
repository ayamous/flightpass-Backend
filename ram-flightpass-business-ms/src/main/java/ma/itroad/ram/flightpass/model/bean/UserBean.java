package ma.itroad.ram.flightpass.model.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserBean {

    private String firstName;
    private String lastName;
    private String email;
    private String userId;
    private String mobile;
    private String address;
    private String postalCode;
    private String city;
    private String country;


}
