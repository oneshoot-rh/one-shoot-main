package ma.oneshoot.oneshootmain.tenant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table("users")
@Entity
public class User {
    @Id
    @GeneratedValue
    Long id;
    String username;
    String name;
    String password;
}
