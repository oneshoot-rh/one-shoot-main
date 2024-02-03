package ma.oneshoot.oneshootmain.upload;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table("uploads")
@Entity
public class Upload {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ;
    Long userId ;
    String uploadDirectory ;
    LocalDateTime uploadDateTime;
    int uploadedFiles ;
    Long uploadedBy;
}
