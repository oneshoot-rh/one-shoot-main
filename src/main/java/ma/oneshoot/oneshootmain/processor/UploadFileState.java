package ma.oneshoot.oneshootmain.processor;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UploadFileState(
        String fileName,
        String state,
        String message
) {
}