package ma.oneshoot.oneshootmain.processor;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record UploadReport(
        List<UploadFileState> uploadedFiles,
        LocalDateTime uploadDate,
        String uploadDirectory
) {
}
