package ma.oneshoot.oneshootmain.candidate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class Experience {
    String entreprise ;
    String title ;
    Date startDate;
    Date endDate ;
    String description;
    String location ;                 

}
