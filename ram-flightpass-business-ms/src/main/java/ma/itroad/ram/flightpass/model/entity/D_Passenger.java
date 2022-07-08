package ma.itroad.ram.flightpass.model.entity;

import lombok.*;
import ma.itroad.core.model.entity.AuditEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@GenericGenerator(name = "eth_seq_generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = { @org.hibernate.annotations.Parameter(name = "sequence_name", value = "flightpass.passenger_seq") })


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "d_passenger",schema = "flightpass")
public class D_Passenger extends AuditEntity {

    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String mobileNumber;
    private String address;
    private String postalCode;
    private String city;
    private String country;
    private String userAccountId;
    private boolean principalUserAccount;

}
