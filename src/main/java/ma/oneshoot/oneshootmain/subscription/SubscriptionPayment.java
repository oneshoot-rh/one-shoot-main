package ma.oneshoot.oneshootmain.subscription;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ma.oneshoot.oneshootmain.tenant.Tenant;
import ma.oneshoot.oneshootmain.utils.UUIDToStringConverter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "permissions")
@Entity
public class SubscriptionPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    @JsonIgnore
    @ToString.Exclude
    private Tenant tenant;

    private String organizationName;
    
    @Convert(converter = UUIDToStringConverter.class)
    private UUID reference;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "payment_delay")
    private LocalDateTime paymentDelay;

    private Double amount;

    @Column(name = "is_paid",columnDefinition = "boolean default false")
    private Boolean isPaid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at",columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;
}
