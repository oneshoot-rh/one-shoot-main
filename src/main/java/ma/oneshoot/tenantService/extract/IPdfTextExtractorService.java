package ma.oneshoot.tenantService.extract;

import java.io.FileNotFoundException;
import java.util.Map;

public interface IPdfTextExtractorService {
    Map<String, String> extractTextFromPdf(String pdfPath) throws FileNotFoundException;

    Map<String, String> categorizeText(String text);
}
