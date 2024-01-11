package ma.oneshoot.oneshootmain.analyse;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AnalysisReport {
    String header;
    String body;
    List<String> remarks = new ArrayList<>();


    public void addRemark(String remark){
        remarks.add(remark);
    }
}
