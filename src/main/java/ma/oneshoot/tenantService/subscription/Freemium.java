package ma.oneshoot.tenantService.subscription;


import jakarta.persistence.*;
import lombok.Data;
import ma.oneshoot.tenantService.tenant.Tenant;

@Data
@Entity
@Table(name = "freemium_subscriptions")
public class Freemium {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Tenant tenant;
}
