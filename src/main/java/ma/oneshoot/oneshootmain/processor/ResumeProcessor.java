package ma.oneshoot.oneshootmain.processor;


import lombok.extern.slf4j.Slf4j;
import ma.oneshoot.oneshootmain.analyse.ResumeContentAnalyserImpl;
import ma.oneshoot.oneshootmain.extract.IPdfTextExtractorService;
import ma.oneshoot.oneshootmain.upload.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
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
    public ResumeProcessor(IStorageService storageService, IPdfTextExtractorService extractorService, ResumeContentAnalyserImpl resumeContentAnalyser) {
        this.storageService = storageService;
        this.extractorService = extractorService;
        this.resumeContentAnalyser = resumeContentAnalyser;
    }


    public UploadReport processResumes(MultipartFile[] files) throws FileNotFoundException {
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
        log.info("Try to extract text from pdf file");
        Map<String, String> extractedObjectFromPdf = extractorService.extractTextFromPdf(storageService.getUploadDirectory().toString());
        log.info("Text extracted successfully");
        resumeContentAnalyser.analyse(extractedObjectFromPdf);
        return UploadReport.builder()
                .uploadedFiles(uploadFileStates)
                .uploadDate(java.time.LocalDateTime.now())
                .uploadDirectory(uploadDirectory)
                .build();
    }
}
