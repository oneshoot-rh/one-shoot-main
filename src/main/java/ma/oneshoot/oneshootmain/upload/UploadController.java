package ma.oneshoot.oneshootmain.upload;


import com.fasterxml.jackson.databind.ObjectMapper;
import ma.oneshoot.oneshootmain.processor.ResumeProcessor;
import ma.oneshoot.oneshootmain.processor.UploadReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/upload")
public class UploadController {
    ResumeProcessor resumeProcessor;

    @Autowired
    public UploadController(ResumeProcessor resumeProcessor) {
        this.resumeProcessor = resumeProcessor;
    }



    @PostMapping
    public ResponseEntity<UploadReport> uploadFiles(@RequestParam("files") MultipartFile[] files) throws IOException {
        return ResponseEntity.ok(resumeProcessor.processResumes(files));
    }
}
