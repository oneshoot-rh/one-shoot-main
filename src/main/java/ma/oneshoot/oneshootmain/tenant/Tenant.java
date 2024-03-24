package ma.oneshoot.oneshootmain.tenant;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "organization_name")
    String organizationName;
    @Column(name = "domain_name")
    String domainName;
    @Column(name = "requestor_role")
    String requestorRole;
    @Column(name = "requestor_professional_email")
    String requestorProfessionalEmail;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "tenant")
    List<User> users = new ArrayList<>();


    public void addUser(User user) {
        if(users==null) users=new ArrayList<>();
        users.add(user);
    }
}
