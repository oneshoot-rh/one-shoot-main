package ma.oneshoot.oneshootmain.candidate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    String city  ;
    String email;
    String website ;

}
