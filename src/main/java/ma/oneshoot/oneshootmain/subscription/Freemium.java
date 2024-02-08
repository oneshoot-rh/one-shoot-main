package ma.oneshoot.oneshootmain.subscription;


import jakarta.persistence.*;
import lombok.Data;
import ma.oneshoot.oneshootmain.tenant.Tenant;

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
