package ma.oneshoot.oneshootmain.upload;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/upload")
public class UploadController {
    IStorageService storageService;

    @Autowired
    public UploadController(IStorageService storageService) {
        this.storageService = storageService;
    }



    @PostMapping
    public ResponseEntity<?> uploadFiles(@RequestParam("files") MultipartFile[] files) throws IOException {
        Map<String , String> fileUploadReport = new HashMap<>();
        for (MultipartFile file : files) {
            System.out.println(file.getOriginalFilename());
            try {
                storageService.store(file);
                fileUploadReport.put(file.getOriginalFilename(), "uploaded");
            } catch (Exception e) {
                fileUploadReport.put(file.getOriginalFilename(), e.getMessage());
            }
        }
        return ResponseEntity.ok(fileUploadReport);
    }
}
