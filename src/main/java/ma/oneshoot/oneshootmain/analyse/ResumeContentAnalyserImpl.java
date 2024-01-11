package ma.oneshoot.oneshootmain.analyse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ResumeContentAnalyserImpl {


    public List<AnalysisReport> analyse(Map<String, String> extractedObjectFromPdf) {
        List<AnalysisReport> analysisReports = new ArrayList<>();
        log.info("Analyse extracted text");
        for (Map.Entry<String, String> entry : extractedObjectFromPdf.entrySet()) {
            AnalysisReport analysisReport = new AnalysisReport();
            analysisReport.setHeader(entry.getValue());
            analysisReport.setBody(entry.getValue());
            if(entry.getKey().isEmpty()){
                analysisReport.addRemark("Empty body");
            }
            if(entry.getKey().length() < 50){
                            analysisReport.addRemark("Body is too short");
            }
            if(entry.getKey().length() > 1000){
                            analysisReport.addRemark("Body is too long");
            }
            analysisReports.add(analysisReport);
        }
        log.info("Text analysed successfully");
        return analysisReports;
    }
}
