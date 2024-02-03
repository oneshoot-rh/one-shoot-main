package ma.oneshoot.oneshootmain.upload;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "uploads")
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
