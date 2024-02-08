package ma.oneshoot.oneshootmain.upload;


import ma.oneshoot.oneshootmain.extract.LoadingResumeException;
import ma.oneshoot.oneshootmain.processor.ResumeProcessor;
import ma.oneshoot.oneshootmain.processor.UploadReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class UploadController {
    ResumeProcessor resumeProcessor;

    @Autowired
    public UploadController(ResumeProcessor resumeProcessor) {
        this.resumeProcessor = resumeProcessor;
    }



    @PostMapping
    public ResponseEntity<UploadReport> uploadFiles(@RequestParam("files") MultipartFile[] files) throws  LoadingResumeException {
        return ResponseEntity.ok(resumeProcessor.processResumes(files));
    }
}
