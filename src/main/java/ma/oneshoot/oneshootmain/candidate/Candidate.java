package ma.oneshoot.oneshootmain.candidate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Candidate {
    String name ;
    String bio;
    Contact contact;
    HashMap<String,String> languages;
    List<String> certifications ;
    List<Experience> experiences ;
    List<Education> education;


}
