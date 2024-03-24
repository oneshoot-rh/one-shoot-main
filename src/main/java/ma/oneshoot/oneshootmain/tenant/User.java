package ma.oneshoot.oneshootmain.tenant;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringExclude;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String username;
    String name;
    @JsonIgnore
    @ToStringExclude
    String password;
    String role;
    @OneToMany
    @JoinTable(name = "user_permissions", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    List<Permission> permissions;
    @ManyToOne
    @JoinColumn(name = "tenant_id")
    Tenant tenant;

}
