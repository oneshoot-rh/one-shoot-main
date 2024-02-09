package ma.oneshoot.oneshootmain.tenant;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "tenants")
@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "subscription_id")
    UUID subscriptionId;
    @Column(name = "organization_name")
    String organizationName;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription_type")
    SubscriptionType subscriptionType;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    Tenant tenant;

    @Temporal(TemporalType.TIMESTAMP)
            @Column(name = "start_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    LocalDateTime startDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    LocalDateTime endDate;
}
