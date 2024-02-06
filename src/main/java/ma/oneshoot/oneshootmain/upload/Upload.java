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
    @Column(name = "upload_directory")
    String uploadDirectory ;
    @Column(name = "upload_date_time")
    LocalDateTime uploadDateTime;
    @Column(name = "uploaded_files")
    int uploadedFiles ;
    @Column(name = "uploaded_by")
    Long uploadedBy;
}
