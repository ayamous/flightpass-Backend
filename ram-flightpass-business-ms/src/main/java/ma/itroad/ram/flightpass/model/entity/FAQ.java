
package ma.itroad.ram.flightpass.model.entity;

import lombok.*;
import ma.itroad.core.model.entity.AuditEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@GenericGenerator(name = "eth_seq_generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = { @org.hibernate.annotations.Parameter(name = "sequence_name", value = "flightpass.faq_seq") })


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "faq",schema = "flightpass")
public class FAQ extends AuditEntity {

    @Column(nullable = false )
    private String question;

    @Column(nullable = false)
    private String answer;
}

