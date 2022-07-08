
package ma.itroad.ram.flightpass.model.entity;

import lombok.*;
import ma.itroad.core.model.entity.AuditEntity;
import ma.itroad.ram.flightpass.model.enums.PageName;
import ma.itroad.ram.flightpass.model.enums.PortletPosition;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@GenericGenerator(name = "eth_seq_generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {@org.hibernate.annotations.Parameter(name = "sequence_name", value = "flightpass.portlet_seq")})


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "portlet", schema = "flightpass")

public class Portlet extends AuditEntity {
    @Enumerated(EnumType.STRING)
    private PortletPosition position;

    @Column(nullable = false)
    private String imagePath;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private PageName pageName;

    private Long imageDisplayingOrder;

    @ManyToOne
    private P_SegmentRef segmentRef;


}
