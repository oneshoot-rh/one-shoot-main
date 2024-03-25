package ma.oneshoot.tenantService.candidate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Education {
    String instituteName ;
    String degreeName ;
    Date startDate ;
    Date endDate ;
    String description ;
}
