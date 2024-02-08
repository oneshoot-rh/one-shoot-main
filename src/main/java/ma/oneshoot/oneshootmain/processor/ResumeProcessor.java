package ma.oneshoot.oneshootmain.processor;


import lombok.extern.slf4j.Slf4j;
import ma.oneshoot.oneshootmain.analyse.ResumeContentAnalyserImpl;
import ma.oneshoot.oneshootmain.extract.IPdfTextExtractorService;
import ma.oneshoot.oneshootmain.extract.LoadingResumeException;
import ma.oneshoot.oneshootmain.upload.IStorageService;
import ma.oneshoot.oneshootmain.upload.Upload;
import ma.oneshoot.oneshootmain.upload.UploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ResumeProcessor {
    private final IStorageService storageService;
    private IPdfTextExtractorService extractorService;

    private ResumeContentAnalyserImpl resumeContentAnalyser;
    @Autowired
    private UploadRepository uploadRepository;

    @Autowired
    public ResumeProcessor(IStorageService storageService, IPdfTextExtractorService extractorService, ResumeContentAnalyserImpl resumeContentAnalyser) {
        this.storageService = storageService;
        this.extractorService = extractorService;
        this.resumeContentAnalyser = resumeContentAnalyser;
    }


    public UploadReport processResumes(MultipartFile[] files) {
        List<UploadFileState> uploadFileStates = new ArrayList<>();
        int uploadedFiles = 0;
        // store and extract functionalities
        for (MultipartFile file : files) {
            UploadFileState uploadFileState = null ;
            boolean isProcessed;
            String message = null;
            try {
                String fileDistination = storageService.store(file);
                uploadedFiles++;
                try {
                    Map<String, String> extractedObjectFromPdf = extractorService.extractTextFromPdf(fileDistination);
                    log.info("Text extracted successfully");
                    isProcessed = true;
                    log.info("Processing extracted text");
                    resumeContentAnalyser.analyse(extractedObjectFromPdf);
                } catch (FileNotFoundException e) {
                    log.debug("Failed to load resumes {}", e.getMessage());
                    message = "Failed to load "+e.getMessage();
                    throw new LoadingResumeException(message);
                }
                uploadFileState = UploadFileState.builder()
                        .fileName(file.getOriginalFilename())
                        .isUploaded(true)
                        .isProcessed(isProcessed)
                        .build();
            } catch (Exception e) {
                uploadFileState = UploadFileState.builder()
                        .fileName(file.getOriginalFilename())
                        .isUploaded(false)
                        .message(message == null ? e.getMessage() : message)
                        .isProcessed(false)
                        .build();
            }finally {
                uploadFileStates.add(uploadFileState);
            }
        }

        String uploadDistination = uploadFileStates.stream()
                .filter(uploadFileState -> uploadFileState.isUploaded())
                .count() > 0 ? storageService.getUploadDistination().toString() : null;
        Upload newUpload = Upload.builder()
                .uploadDateTime(LocalDateTime.now())
                .uploadDirectory(storageService.getUploadDistination().toString())
                .uploadedFiles(uploadedFiles)
                .build();
        uploadRepository.save(newUpload);
        uploadRepository.findAll()
                .forEach(System.out::println);
        //resumeContentAnalyser.analyse(extractedObjectFromPdf);
        return UploadReport.builder()
                .uploadedFiles(uploadFileStates)
                .uploadDate(java.time.LocalDateTime.now())
                .uploadDirectory(uploadDistination)
                .build();
    }
}
