package ma.oneshoot.oneshootmain.tenant;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "tenants")
@Entity
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String organizationName;
    String requestorRole;
    String requestorProfessionalEmail;



}
