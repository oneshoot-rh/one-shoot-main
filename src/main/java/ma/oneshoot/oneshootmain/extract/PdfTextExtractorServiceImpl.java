package ma.oneshoot.oneshootmain.extract;


import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PdfTextExtractorServiceImpl {

    List<String> headers = new ArrayList<>();

    {
        headers.add("Contact");
        headers.add("Top Skills");
        headers.add("Languages");
        headers.add("Certifications");
        headers.add("Summary");
        headers.add("Experience");
        headers.add("Education");
    }

    public  String extractTextFromPdf(String pdfPath) throws FileNotFoundException {
        File file = new File(pdfPath);
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper textStripper = new PDFTextStripper() {
                private boolean isHeader = false;

                @Override
                protected void startPage(PDPage pageNo) throws IOException {
                    super.startPage(pageNo);
                    isHeader = false;
                }

                @Override
                protected void writeString(String string, List<TextPosition> textPositions) throws IOException {
                    if (isHeader(textPositions)) {
                        writeLineSeparator();
                        isHeader = true;
                    }
                    super.writeString(string, textPositions);
                }

                private boolean isHeader(List<TextPosition> textPositions) {
                    return textPositions.stream().anyMatch(position -> position.getFontSize() > 16);
                }
            };

            textStripper.setStartPage(1);
            textStripper.setEndPage(document.getNumberOfPages());
            textStripper.setAddMoreFormatting(true);

            String text = textStripper.getText(document);
            Map<String, String> categorizeText = categorizeText(text);

            log.info("Text extracted from pdf file");
            for (var entry : categorizeText.entrySet()) {
                log.info("Header: {}", entry.getKey());
                log.info("Body: {}", entry.getValue());
            }
            return text;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Map<String,String> categorizeText(String text){
        // TODO : splitting text only which between headers
        Map<String,String> categorizedText = new HashMap<>();
        for (int i = headers.size()-1 ; i>= 0; i--){
            String header = headers.get(i);
            String[] splittedText = text.split("(?<=\\n)"+header+"(?=\\r)");
            if (splittedText.length > 1){
                String bodyText = splittedText[1];
                // remove content extracted
                text = text.replace(header, "");
                text = text.replace(bodyText, "");
                categorizedText.put(header, bodyText);
            }
        }
        return categorizedText;
    }


}
