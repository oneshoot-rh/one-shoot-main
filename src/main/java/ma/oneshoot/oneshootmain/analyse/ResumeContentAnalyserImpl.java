package ma.oneshoot.oneshootmain.analyse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class ResumeContentAnalyserImpl {


    public void analyse(Map<String, String> extractedObjectFromPdf) {
        log.info("Analyse extracted text");
    }
}
