package ma.oneshoot.oneshootmain.processor;


import ma.oneshoot.oneshootmain.upload.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResumeProcessor {
    private final IStorageService storageService;

    @Autowired
    public ResumeProcessor(IStorageService storageService) {
        this.storageService = storageService;
    }

    public UploadReport processResumes(MultipartFile[] files) {
        List<UploadFileState> uploadFileStates = new ArrayList<>();
        String uploadDirectory = storageService.getUploadDirectory().toString();
        for (MultipartFile file : files) {
            UploadFileState uploadFileState = null ;
            try {
                storageService.store(file);
                uploadFileState = UploadFileState.builder()
                        .fileName(file.getOriginalFilename())
                        .state("uploaded")
                        .build();
            } catch (Exception e) {
                uploadFileState = UploadFileState.builder()
                        .fileName(file.getOriginalFilename())
                        .state("failed")
                        .message(e.getMessage())
                        .build();
            }finally {
                uploadFileStates.add(uploadFileState);
            }
        }
        return UploadReport.builder()
                .uploadedFiles(uploadFileStates)
                .uploadDate(java.time.LocalDateTime.now())
                .uploadDirectory(uploadDirectory)
                .build();
    }
}
